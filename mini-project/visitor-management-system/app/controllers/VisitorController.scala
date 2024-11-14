package controllers

import models.Visitor
import play.api.mvc._
import play.api.libs.json._
import services.{KafkaProducerService, VisitorService}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class VisitorController @Inject()(
                                   val cc: ControllerComponents,
                                   visitorService: VisitorService,
                                   kafkaProducerService: KafkaProducerService
                                 )(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok("Welcome to Visitor Management System!")  // Render the homepage
  }

  // Endpoint to check-in a visitor
  def checkInVisitor(): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Visitor] match {
      case JsSuccess(visitor, _) =>
        visitorService.checkIn(visitor).map { created =>
          // Returning a success message when the visitor is checked in
          Created(Json.obj(
            "message" -> "Visitor successfully checked in.",
            "visitor" -> Json.toJson(created)
          ))
        }
      case JsError(errors) =>
        // If validation fails, return a bad request with detailed error messages
        Future.successful(BadRequest(Json.obj(
          "message" -> "Failed to check in visitor due to invalid data.",
          "errors" -> JsError.toJson(errors)
        )))
    }
  }

  // Endpoint to check-out a visitor
  def checkOutVisitor(visitorId: Long): Action[AnyContent] = Action.async {
    visitorService.checkOut(visitorId).map {
      case true =>
        // Success message when the visitor is checked out
        Ok(Json.obj("message" -> "Visitor checked out successfully."))
      case false =>
        // Failure message when the check-out operation fails
        InternalServerError(Json.obj("message" -> "Failed to check out the visitor. Please try again later."))
    }
  }

  // Endpoint to list all visitors
  def list(): Action[AnyContent] = Action.async {
    visitorService.list().map { visitors =>
      // Returning all visitors as JSON
      Ok(Json.obj("message" -> "List of all visitors", "data" -> Json.toJson(visitors)))
    }
  }

  // Endpoint to get details of a specific visitor by ID
  def getVisitorDetails(visitorId: Long): Action[AnyContent] = Action.async {
    visitorService.get(visitorId).map {
      case Some(visitor) =>
        // Success message with visitor details
        Ok(Json.obj("message" -> s"Visitor with id $visitorId found.", "data" -> Json.toJson(visitor)))
      case None =>
        // Message when the visitor is not found
        NotFound(Json.obj("message" -> s"Visitor with id $visitorId not found."))
    }
  }

}
