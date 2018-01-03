package controllers

import play.api.mvc._
import db._

class Tasks(cc: MessagesControllerComponents, db: Db)
  extends AuthMessagesAbstractController(cc, db) {
  def createPage = Auth { TODO }

  def create = Auth { TODO }

  def read(id: Long) = Auth { TODO }

  def readAll = Auth { TODO }

  def readAllDeliverables(id: Long) = Auth { TODO }

  def register(id: Long) = Auth { TODO }

  def unregister(id: Long) = Auth { TODO }

  def deliver(id: Long) = Auth { TODO }

  def delete(id: Long) = Auth { TODO }
}
