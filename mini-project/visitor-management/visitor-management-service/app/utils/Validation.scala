package utils

import play.api.data.validation.ValidationError

import scala.util.matching.Regex

object Validation {

  // Email validation regex
  private val emailRegex: Regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$".r

  // Contact number validation regex (for example, basic US phone format)
  private val contactNumberRegex: Regex = "^[0-9]{10}$".r

  // Validate Email format
  def validateEmail(email: String): Option[ValidationError] = {
    if (emailRegex.matches(email)) None  // If it matches the regex, it's valid
    else Some(ValidationError("Invalid email format"))
  }

  // Validate Contact Number format
  def validateContactNumber(contactNumber: String): Option[ValidationError] = {
    if (contactNumberRegex.matches(contactNumber)) None  // If it matches the regex, it's valid
    else Some(ValidationError("Invalid contact number format. It should be a 10-digit number"))
  }
}
