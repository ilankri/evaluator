package controllers

import play.api._

import db._

class AppControllerComponents(
    messagesActionBuilder: mvc.MessagesActionBuilder,
    actionBuilder: mvc.DefaultActionBuilder,
    parsers: mvc.PlayBodyParsers,
    messagesApi: i18n.MessagesApi,
    langs: i18n.Langs,
    fileMimeTypes: http.FileMimeTypes,
    executionContext: concurrent.ExecutionContext,
    val db: Db,
    val userIdKey: String)
  extends mvc.DefaultMessagesControllerComponents(
    messagesActionBuilder,
    actionBuilder, parsers, messagesApi, langs, fileMimeTypes, executionContext)
