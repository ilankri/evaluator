package controllers

import concurrent._

import play.api.mvc._

class TaskWorkerRequest[A](
    request: WorkerRequest[A],
    val task: models.Task[Any])
  extends WrappedRequest[A](request) {
  def worker = request.user
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

  def authTaskWorkerAction(taskId: Long, userId: Long) =
    authWorkerAction(userId) andThen
      taskWorkerRefiner(taskId, cc.executionContext)
}
