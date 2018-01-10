package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

import db._
import models._

sealed abstract trait TaskForm[Data] {
  def form: Form[Data]

  def commitTask(data: Data, request: EvaluatorRequest[AnyContent], db: Db): Unit

  def submit(onFailure: Result, onSuccess: Result, db: Db)(
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

case class McqCreationData(description: String, questions: Seq[McqQuestion]) {
  def buildTask(request: EvaluatorRequest[AnyContent]) =
    request.user submitTask (description, Mcq(questions))
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
