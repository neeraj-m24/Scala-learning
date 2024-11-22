package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Case class to represent a Visitor entity
case class Visitor(
                    visitorId: Option[Long] = None,  // Optional visitor ID, auto-generated
                    name: String,  // Visitor's name
                    hostName: String,  // Name of the host the visitor is meeting
                    hostMail: String,  // Host's email address
                    building: String,  // Building where the visitor is meeting the host
                    email: String,  // Visitor's email address
                    contactNumber: String,  // Visitor's contact number
                    checkInTime: String = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),  // Default to the current time
                    checkOutTime: Option[String] = None,  // Optional check-out time (if applicable)
                    status: String = "check-in",  // Default status is "Waiting"
                    identityProof: Array[Byte],  // Binary data representing the identity proof (e.g., image, document)
                  )

// Companion object for JSON serialization and deserialization for Visitor
object Visitor {

  // Reads: Defines how to convert JSON data into a Visitor object
  // Maps JSON fields to the corresponding fields in the Visitor case class
  implicit val visitorReads: Reads[Visitor] = (
    (JsPath \ "visitorId").readNullable[Long] and  // Optional visitor ID
      (JsPath \ "name").read[String] and  // Visitor's name
      (JsPath \ "hostName").read[String] and  // Host's name
      (JsPath \ "hostMail").read[String] and  // Host's email
      (JsPath \ "building").read[String] and  // Building name
      (JsPath \ "email").read[String] and  // Visitor's email
      (JsPath \ "contactNumber").read[String] and  // Visitor's contact number
      (JsPath \ "checkInTime").readWithDefault[String](LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)) and  // Default to current time
      (JsPath \ "checkOutTime").readNullable[String] and  // Optional check-out time
      (JsPath \ "status").readWithDefault[String]("check-in") and  // Default status is "Checked In"
      (JsPath \ "identityProof").read[Array[Byte]]  // Identity proof field (binary data)
    )(Visitor.apply _)  // Map the data to the case class

  // Writes: Defines how to convert a Visitor object into JSON
  implicit val visitorWrites: Writes[Visitor] = Json.writes[Visitor]  // Automatically generate Writes for Visitor case class

  // Format: Combines both Reads and Writes for easy serialization and deserialization
  // This allows both converting from JSON to Visitor and from Visitor to JSON
  implicit val visitorFormat: Format[Visitor] = Format(visitorReads, visitorWrites)
}
