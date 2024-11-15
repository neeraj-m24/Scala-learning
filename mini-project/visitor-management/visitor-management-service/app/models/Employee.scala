package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Employee(employeeId: Option[Long],
                    employeeName: String,
                    organisation: String,
                    building: String,
                    email: String,
                    employeeType: String,
                    contactNo: String)

object Employee {
  // Reads: Defines how to deserialize (convert JSON to Employee)
  // Maps JSON fields to Employee class fields using JsPath
  implicit val employeeReads: Reads[Employee] = (
    (JsPath \ "employeeId").readNullable[Long] and
      (JsPath \ "employeeName").read[String] and
      (JsPath \ "organisation").read[String] and
      (JsPath \ "building").read[String] and
      (JsPath \ "email").read[String] and
      (JsPath \ "employeeType").read[String] and
      (JsPath \ "contactNo").read[String]
    )(Employee.apply _)

  // Writes: Defines how to serialize (convert Employee to JSON)
  // Converts the Employee object to a JSON object using implicit Json.writes
  implicit val employeeWrites: Writes[Employee] = Json.writes[Employee]

  // Format: Combines both Reads and Writes for easy serialization and deserialization
  // This is a shorthand for both reading and writing operations
  implicit val employeeFormat: Format[Employee] = Format(employeeReads, employeeWrites)
}
