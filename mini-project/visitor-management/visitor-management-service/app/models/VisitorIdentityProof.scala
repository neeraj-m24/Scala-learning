package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

// Case class to represent a Visitor's identity proof information
case class VisitorIdentityProof(
                                 id: Option[Long] = None,  // Optional unique ID for the identity proof record
                                 visitorId: Long,  // ID of the visitor associated with this identity proof
                                 identityProof: Array[Byte]  // Binary data representing the identity proof (e.g., image, document)
                               )

// Companion object for JSON serialization and deserialization for VisitorIdentityProof
object VisitorIdentityProof {

  // Reads: Defines how to deserialize JSON data into a VisitorIdentityProof object
  // This maps the JSON fields to the corresponding fields in the VisitorIdentityProof case class
  implicit val visitorReads: Reads[VisitorIdentityProof] = (
    (JsPath \ "id").readNullable[Long] and  // Optional ID field
      (JsPath \ "visitorId").read[Long] and  // Visitor's ID (required)
      (JsPath \ "identityProof").read[Array[Byte]]  // Identity proof field (binary data)
    )(VisitorIdentityProof.apply _)  // Maps the parsed data into the case class constructor

  // Writes: Defines how to serialize a VisitorIdentityProof object into JSON
  implicit val visitorWrites: Writes[VisitorIdentityProof] = Json.writes[VisitorIdentityProof]  // Automatically generates Writes for VisitorIdentityProof

  // Format: Combines both Reads and Writes for easy serialization and deserialization
  // This allows both converting from JSON to VisitorIdentityProof and from VisitorIdentityProof to JSON
  implicit val visitorFormat: Format[VisitorIdentityProof] = Format(visitorReads, visitorWrites)
}
