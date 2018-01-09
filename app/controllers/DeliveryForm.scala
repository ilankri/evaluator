package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

import models._

sealed abstract trait DeliveryForm[Data] {
  def form: Form[Data]

  def commitDeliverable(
    data: Data,
    request: TaskWorkerRequest[AnyContent]): Unit

  def deliver(onFailure: Result, onSuccess: Result)(
    implicit
    request: TaskWorkerRequest[AnyContent]) =
    form.bindFromRequest.fold(
      formWithErrors => onFailure,
      deliverableData => {
        commitDeliverable(deliverableData, request)
        onSuccess
      }
    )
}

case class ChoicesData(choices: Seq[Boolean])

case class McqData(answers: Seq[ChoicesData], comment: String) {
  def buildDeliverable(request: TaskWorkerRequest[AnyContent]) =
    request.worker submitDeliverable (
      comment,
      toMcqSolution,
      request.task
    )

  private def toMcqSolution = McqSolution(
    answers map { case ChoicesData(choices) => choices }
  )
}

case object McqDeliveryForm extends DeliveryForm[McqData] {
  type Data = McqData

  override val form = Form(
    mapping(
      "questions" ->
        seq(
          mapping(
            "choices" -> seq(boolean)
          )(ChoicesData.apply)(ChoicesData.unapply)
        ),
      "comment" -> text
    )(McqData.apply)(McqData.unapply)
  )

  override def commitDeliverable(
    data: Data,
    request: TaskWorkerRequest[AnyContent]) =
    data buildDeliverable request
}
