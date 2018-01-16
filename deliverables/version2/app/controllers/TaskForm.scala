package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

import db._
import models._

/**
  * For each task format , we must implement this class to define a form
  * for creating tasks of this format.  `Data` is the type of data
  * needed to create a task.
  */
sealed abstract class TaskForm[Data] {
  /** Returns the form for creating a task with data of type `Data`.  */
  def form: Form[Data]

  /**
    * Add the task created from the `data` and the `request` to the
    * database.
    */
  def commitTask(data: Data, request: EvaluatorRequest[AnyContent],
    db: Db): Unit

  /** Validates the task creation form.  */
  def validate(onFailure: Result, onSuccess: Result, db: Db)(
    implicit
    request: EvaluatorRequest[AnyContent]) =
    form.bindFromRequest.fold(
      formWithErrors => onFailure,
      taskData => {
        commitTask(taskData, request, db)
        onSuccess
      }
    )
}

object TaskForm {
  sealed abstract class Kind
  case object Mcq extends Kind

  def validator(kind: Kind)(
    implicit
    request: EvaluatorRequest[AnyContent]): (Result, Result, Db) => Result =
    kind match {
      case Mcq => McqCreationForm.validate
    }

  def creationPage(kind: Kind)(
    implicit
    request: EvaluatorRequest[AnyContent]): play.twirl.api.Html =
    kind match {
      case Mcq => views.html.mcqCreationForm(McqCreationForm.form)
    }
}

case class McqCreationData(description: String, questions: Seq[McqQuestion]) {
  def buildTask(request: EvaluatorRequest[AnyContent]) =
    request.user.submitTask(description, Mcq(questions))
}

case object McqCreationForm extends TaskForm[McqCreationData] {
  override val form = Form(mapping(
    "description" -> text,
    "questions" -> seq(mapping(
      "label" -> text,
      "choices" -> seq(mapping(
        "label" -> text,
        "right" -> boolean
      )(McqChoice.apply)(McqChoice.unapply))
    )(McqQuestion.apply)(McqQuestion.unapply))
  )(McqCreationData.apply)(McqCreationData.unapply))

  override def commitTask(
    data: McqCreationData,
    request: EvaluatorRequest[AnyContent],
    db: Db) =
    db.create(data buildTask request)
}
