package controllers

import concurrent._

import play.api.mvc._

class TaskWorkerRequest[A](
    request: WorkerRequest[A],
    val task: models.Task[models.AnyTaskFormat])
  extends MessagesRequest[A](request, request.messagesApi) {
  def worker = request.user
}

class TaskEvaluatorRequest[A](
    request: EvaluatorRequest[A],
    val task: models.Task[models.AnyTaskFormat])
  extends MessagesRequest[A](request, request.messagesApi) {
  def evaluator = request.user
}

abstract class TaskAbstractController(cc: AppControllerComponents)
  extends UserAbstractController(cc) {
  private def taskWorkerRefiner(taskId: Long, ec: ExecutionContext) =
    new ActionRefiner[WorkerRequest, TaskWorkerRequest] {
      def executionContext = ec

      def refine[A](request: WorkerRequest[A]) = Future.successful {
        cc.db.readTask(taskId).fold[Either[Result, TaskWorkerRequest[A]]](
          Left(AppResults.notFound))(task =>
            Right(new TaskWorkerRequest(request, task))
          )
      }
    }

  private def taskEvaluatorRefiner(taskId: Long, ec: ExecutionContext) =
    new ActionRefiner[EvaluatorRequest, TaskEvaluatorRequest] {
      def executionContext = ec

      def refine[A](request: EvaluatorRequest[A]) = Future.successful {
        cc.db.readTask(taskId).fold[Either[Result, TaskEvaluatorRequest[A]]](
          Left(AppResults.notFound))(task =>
            Right(new TaskEvaluatorRequest(request, task))
          )
      }
    }

  private def membershipCheckAction(ec: ExecutionContext) =
    new ActionFilter[TaskWorkerRequest] {
      override def executionContext = ec

      override def filter[A](request: TaskWorkerRequest[A]) =
        Future.successful {
          if (!request.task.member(request.worker))
            Some(AppResults.unauthorized)
          else
            None
        }
    }

  private def ownershipCheckAction(ec: ExecutionContext) =
    new ActionFilter[TaskEvaluatorRequest] {
      override def executionContext = ec

      override def filter[A](request: TaskEvaluatorRequest[A]) =
        Future.successful {
          if (request.task.author.id != request.evaluator.id)
            Some(AppResults.unauthorized)
          else
            None
        }
    }

  def taskMemberAction(taskId: Long) =
    workerAction andThen taskWorkerRefiner(taskId, cc.executionContext) andThen
      membershipCheckAction(cc.executionContext)

  def taskOwnerAction(taskId: Long) =
    evaluatorAction andThen
      taskEvaluatorRefiner(taskId, cc.executionContext) andThen
      ownershipCheckAction(cc.executionContext)

  def authTaskWorkerAction(taskId: Long, userId: Long) =
    authWorkerAction(userId) andThen
      taskWorkerRefiner(taskId, cc.executionContext)
}
