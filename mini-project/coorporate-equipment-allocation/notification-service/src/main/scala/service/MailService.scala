package org.trainingapp.notificationservice
package service

import com.typesafe.config.ConfigFactory
import schemas.NotificationSchemas.SimpleMailSchema

import java.util.Properties
import javax.mail.internet.{InternetAddress, MimeMessage}
import javax.mail.{Authenticator, Message, MessagingException, PasswordAuthentication, Session, Transport}
import scala.concurrent.{ExecutionContext, Future}
object MailService {
  implicit val ec:ExecutionContext = ExecutionContext.global


    def sendMail(mail:SimpleMailSchema):  Future[Unit] = {

      Future {
        println(s"Sending mail to ${mail.toEmail} with subject ${mail.subject} and body ${mail.body}")
      }
    }

}
