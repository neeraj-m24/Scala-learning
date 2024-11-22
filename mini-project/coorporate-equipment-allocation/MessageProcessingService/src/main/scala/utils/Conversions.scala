//package com.training.app
//package utils
//
//import schemas.MailSchemas.{SimpleMailSchema, simpleMailSchemaFormat}
//import schemas.MessageSchema
//import utils.Constants.MAINTENANCE
//
//import com.typesafe.config.ConfigFactory
//import spray.json._
//
//
//
//object Conversions {
//
//  val config = ConfigFactory.load()
//
//  implicit def MessageSchemaToReminderMessage(message: MessageSchema): String = {
//
//    val toEmail = message.employeeEmail
//    val subject = "Your allocated Equipment is Overdue"
//    val body =
//      s"""
//         |Dear ${message.employeeName},
//         |
//         |This is a reminder to return the equipment you have borrowed.
//         |Equipment Name: ${message.name}
//         |Device Id: ${message.deviceId}
//         |Expected Return Date: ${message.expectedReturnDate}
//         |
//         |Regards,
//         |Equipment AllocationTeam
//         |""".stripMargin
////    ReturnReminderSchema(subject, toEmail, body)
//    SimpleMailSchema(subject, toEmail, body).toJson.toString()
//
//
//  }
//  implicit def MessageSchemaToEmployeeAllocationAlertMessage(messageSchema: MessageSchema): String = {
//    val toEmail = messageSchema.employeeEmail
//    val subject = "Your Equipment Has Been Allocated"
//    val body =
//      s"""
//         |Dear ${messageSchema.employeeName},
//         |
//         |The equipment with the following details has been allocated to you:
//         |Equipment Name: ${messageSchema.name}
//         |Device Id: ${messageSchema.deviceId}
//         |Allocated Date: ${messageSchema.allocatedDate}
//         |
//         |Regards,
//         |Equipment Allocation Team
//         |""".stripMargin
//
//    SimpleMailSchema(subject, toEmail, body).toJson.toString()
//  }
//
//  implicit def MessageSchemaToMaintenanceAlertMessage(messageSchema: MessageSchema): String = {
//    val toEmail = config.getString("mails.maintenance.email")
//    val subject = "Maintenance Alert"
//    val body =
//      s"""
//         |Dear Maintenance Team,
//         |
//         |The equipment with the following details has been returned and is sent for maintenance:
//         |Employee Name: ${messageSchema.employeeName}
//         |Device Id: ${messageSchema.deviceId}
//         |Equipment Name: ${messageSchema.name}
//         |Return Date: ${messageSchema.returnDate}
//         |
//         |Regards,
//         |Equipment Allocation Team
//         |""".stripMargin
////    MaintenanceAlertSchema(subject, toEmail, body)
//       SimpleMailSchema(subject, toEmail, body).toJson.toString()
//  }
//  implicit def MessageSchemaToInventoryAlertMessage(messageSchema: MessageSchema): String= {
//    val toEmail = config.getString("mails.inventory.email")
//    val subject = "Equipment Allocation Alert"
//    val body =
//      s"""
//         |Dear Inventory  Team,
//         |
//         |The equipment with the following details has been allocated:
//         |Employee Name: ${messageSchema.employeeName}
//         |Device Id: ${messageSchema.deviceId}
//         |Equipment Name: ${messageSchema.name}
//         |Allocated Date: ${messageSchema.allocatedDate}
//         |
//         |Regards,
//         |Equipment Allocation Team
//         |""".stripMargin
//
//    SimpleMailSchema(subject, toEmail, body).toJson.toString()
//
//  }
//
//  implicit def MessageSchemaToInventoryReturnMessage(messageSchema: MessageSchema): String= {
//    val toEmail = config.getString("mails.inventory.email")
//    val subject = "Equipment Return Alert"
//    val body =
//      s"""
//         |Dear Inventory Team,
//         |
//         |The equipment with the following details has been returned:
//         |Employee Name: ${messageSchema.employeeName}
//         |Device Id: ${messageSchema.deviceId}
//         |Equipment Name: ${messageSchema.name}
//         |Return Date: ${messageSchema.returnDate}
//         |${if(messageSchema.status.equals(MAINTENANCE)) "and is sent for maintenance" else ""}
//         |
//         |Regards,
//         |Equipment Allocation Team
//         |""".stripMargin
//
//    SimpleMailSchema(subject, toEmail, body).toJson.toString()
//
//  }
//  implicit def MessageSchemaToEquipmentReturnEmployeeAcknowledgementMessage(messageSchema: MessageSchema): String= {
//    val toEmail = messageSchema.employeeEmail
//    val subject = "Equipment Return Acknowledgement"
//    val body =
//      s"""
//         |Dear ${messageSchema.employeeName},
//         |
//         |The equipment with the following details has been returned:
//         |Device Id: ${messageSchema.deviceId}
//         |Equipment Name: ${messageSchema.name}
//         |Return Date: ${messageSchema.returnDate}
//         |${if(messageSchema.status.equals(MAINTENANCE)) "and is sent for maintenance" else ""}
//         |
//         |Regards,
//         |Equipment Allocation Team
//         |""".stripMargin
//
//    SimpleMailSchema(subject, toEmail, body).toJson.toString()
//
//  }
//
//}
//


package com.training.app
package utils

import schemas.MailSchemas.{SimpleMailSchema, simpleMailSchemaFormat}
import schemas.MessageSchema
import utils.Constants.MAINTENANCE

import com.typesafe.config.ConfigFactory
import spray.json._

object Conversions {

  val config = ConfigFactory.load()

  // Email Template: Reminder for Overdue Equipment
  implicit def MessageSchemaToReminderMessage(message: MessageSchema): String = {
    val toEmail = message.employeeEmail
    val subject = "Reminder: Your Equipment Is Overdue"
    val body =
      s"""
         |Hi ${message.employeeName},
         |
         |Just a friendly reminder that the equipment you borrowed is now overdue.
         |Here are the details:
         |
         |• Equipment Name: ${message.name}
         |• Device ID: ${message.deviceId}
         |• Expected Return Date: ${message.expectedReturnDate}
         |
         |We kindly request that you return the equipment at your earliest convenience.
         |
         |If you've already returned it, please disregard this message.
         |
         |Thanks so much!
         |
         |Best regards,
         |The Equipment Allocation Team
         |""".stripMargin
    SimpleMailSchema(subject, toEmail, body).toJson.toString()
  }

  // Email Template: Equipment Allocation Alert
  implicit def MessageSchemaToEmployeeAllocationAlertMessage(messageSchema: MessageSchema): String = {
    val toEmail = messageSchema.employeeEmail
    val subject = "Your Equipment Has Been Allocated"
    val body =
      s"""
         |Hi ${messageSchema.employeeName},
         |
         |Good news! The equipment you requested has been allocated to you. Here are the details:
         |
         |• Equipment Name: ${messageSchema.name}
         |• Device ID: ${messageSchema.deviceId}
         |• Allocation Date: ${messageSchema.allocatedDate}
         |
         |Please take care of the equipment and make sure it’s used according to our guidelines.
         |
         |If you have any questions, feel free to reach out!
         |
         |Cheers,
         |The Equipment Allocation Team
         |""".stripMargin
    SimpleMailSchema(subject, toEmail, body).toJson.toString()
  }

  // Email Template: Maintenance Alert
  implicit def MessageSchemaToMaintenanceAlertMessage(messageSchema: MessageSchema): String = {
    val toEmail = config.getString("mails.maintenance.email")
    val subject = "Maintenance Alert: Equipment Sent for Repair"
    val body =
      s"""
         |Hi Maintenance Team,
         |
         |Just letting you know that the following equipment has been returned and is now being sent for maintenance:
         |
         |• Employee Name: ${messageSchema.employeeName}
         |• Device ID: ${messageSchema.deviceId}
         |• Equipment Name: ${messageSchema.name}
         |• Return Date: ${messageSchema.returnDate}
         |
         |Please take the necessary steps to get it repaired as soon as possible.
         |
         |Thanks,
         |The Equipment Allocation Team
         |""".stripMargin
    SimpleMailSchema(subject, toEmail, body).toJson.toString()
  }

  // Email Template: Inventory Allocation Alert
  implicit def MessageSchemaToInventoryAlertMessage(messageSchema: MessageSchema): String = {
    val toEmail = config.getString("mails.inventory.email")
    val subject = "Inventory Alert: Equipment Allocation"
    val body =
      s"""
         |Hi Inventory Team,
         |
         |We’ve just allocated the following equipment to an employee:
         |
         |• Employee Name: ${messageSchema.employeeName}
         |• Device ID: ${messageSchema.deviceId}
         |• Equipment Name: ${messageSchema.name}
         |• Allocation Date: ${messageSchema.allocatedDate}
         |
         |Please make sure the equipment is properly logged in your records.
         |
         |Thanks a lot!
         |
         |Best,
         |The Equipment Allocation Team
         |""".stripMargin
    SimpleMailSchema(subject, toEmail, body).toJson.toString()
  }

  // Email Template: Equipment Return Alert (for Inventory)
  implicit def MessageSchemaToInventoryReturnMessage(messageSchema: MessageSchema): String = {
    val toEmail = config.getString("mails.inventory.email")
    val subject = "Inventory Alert: Equipment Return"
    val body =
      s"""
         |Hi Inventory Team,
         |
         |The following equipment has been returned by an employee:
         |
         |• Employee Name: ${messageSchema.employeeName}
         |• Device ID: ${messageSchema.deviceId}
         |• Equipment Name: ${messageSchema.name}
         |• Return Date: ${messageSchema.returnDate}
         |
         |${if(messageSchema.status.equals(MAINTENANCE))
        "It’s been sent for maintenance and will be back once it’s fixed."
      else
        "Please update your records accordingly."}
         |
         |Thank you!
         |
         |Best,
         |The Equipment Allocation Team
         |""".stripMargin
    SimpleMailSchema(subject, toEmail, body).toJson.toString()
  }

  // Email Template: Equipment Return Acknowledgement (for Employee)
  implicit def MessageSchemaToEquipmentReturnEmployeeAcknowledgementMessage(messageSchema: MessageSchema): String = {
    val toEmail = messageSchema.employeeEmail
    val subject = "Equipment Return Acknowledgement"
    val body =
      s"""
         |Hi ${messageSchema.employeeName},
         |
         |Thank you for returning the equipment. Here are the details of the return:
         |
         |• Device ID: ${messageSchema.deviceId}
         |• Equipment Name: ${messageSchema.name}
         |• Return Date: ${messageSchema.returnDate}
         |${if(messageSchema.status.equals(MAINTENANCE)) "It’s been sent for maintenance and will be back soon." else ""}
         |
         |We appreciate your cooperation!
         |
         |Best regards,
         |The Equipment Allocation Team
         |""".stripMargin
    SimpleMailSchema(subject, toEmail, body).toJson.toString()
  }
}
