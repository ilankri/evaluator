package controllers

import concurrent._

import play.api.mvc._

/**
  * A "task worker request" is a request made on a certain task by
  * a worker.
  */
class TaskWorkerRequest[A](
    request: WorkerRequest[A],
    val task: models.Task[models.AnyTaskFormat])
  extends MessagesRequest[A](request, request.messagesApi) {
  def worker = request.user
}

/**
  * A "task evaluator request" is a request made on a certain task by an
  * evaluator.
  */
class TaskEvaluatorRequest[A](
    request: EvaluatorRequest[A],
    val task: models.Task[models.AnyTaskFormat])
  extends MessagesRequest[A](request, request.messagesApi) {
  def evaluator = request.user
}

/**
  * Abstract controller for the handling of tasks
  *
  * It provides several types of action according to the role and the
  * permissions of the user.
  */
abstract class TaskAbstractController(cc: AppControllerComponents)
  extends UserAbstractController(cc) {
  private def asTaskWorkerAction(taskId: Long) =
    new ActionRefiner[WorkerRequest, TaskWorkerRequest] {
      def executionContext = cc.executionContext

      def refine[A](request: WorkerRequest[A]) = Future.successful {
        cc.db.readTask(taskId).fold[Either[Result, TaskWorkerRequest[A]]](
          Left(AppResults.notFound))(task =>
            Right(new TaskWorkerRequest(request, task))
          )
      }
    }

  private def asTaskEvaluatorAction(taskId: Long) =
    new ActionRefiner[EvaluatorRequest, TaskEvaluatorRequest] {
      def executionContext = cc.executionContext

      def refine[A](request: EvaluatorRequest[A]) = Future.successful {
        cc.db.readTask(taskId).fold[Either[Result, TaskEvaluatorRequest[A]]](
          Left(AppResults.notFound))(task =>
            Right(new TaskEvaluatorRequest(request, task))
          )
      }
    }

  private def membershipCheckAction =
    new ActionFilter[TaskWorkerRequest] {
      override def executionContext = cc.executionContext

      override def filter[A](request: TaskWorkerRequest[A]) =
        Future.successful {
          if (!request.task.member(request.worker))
            Some(AppResults.unauthorized)
          else
            None
        }
    }

  private def ownershipCheckAction =
    new ActionFilter[TaskEvaluatorRequest] {
      override def executionContext = cc.executionContext

      override def filter[A](request: TaskEvaluatorRequest[A]) =
        Future.successful {
          if (request.task.author.id != request.evaluator.id)
            Some(AppResults.unauthorized)
          else
            None
        }
    }

  /**
    * Returns an action builder for requests made by a worker who is
    * registered for the task with the given ID.
    */
  def taskMemberAction(taskId: Long) =
    taskWorkerAction(taskId) andThen membershipCheckAction

  /**
    * Returns an action builder for requests made by an evaluator who
    * owns the task with the given ID.
    */
  def taskOwnerAction(taskId: Long) =
    evaluatorAction andThen asTaskEvaluatorAction(taskId) andThen
      ownershipCheckAction

  /**
    * Returns an action builder for requests on the task with the given
    * ID made by any worker (regardless of his registration status).
    */
  def taskWorkerAction(taskId: Long) =
    workerAction andThen asTaskWorkerAction(taskId)
}
