import akka.actor.Actor
import model.Visitor

class ITSupportProcessor extends Actor {
  override def receive: Receive = {
    case visitor: Visitor =>
      val name = visitor.name
      val email = visitor.email

      visitor.status match {
        case "check-in" =>
          // Define Wi-Fi access email content
          val subject = s"Wi-Fi Access Details for $name"
          val body =
            s"""
               |Dear $name,
               |
               |Welcome! Below are your WiFi credentials:
               |
               |WIFI : Apple7.2-SEZ
               |Password: Apple12345
               |
               |Thank you for visiting us.
               |
               |Best regards,
               |IT Support Team
               |""".stripMargin

          // Send Wi-Fi details email
//          EmailUtils.sendEmail(email, subject, body)
          println("="*80)
          println(s"Subject: ${subject}")
          println(body)
          println(s"Wi-Fi details email sent to $email successfully.")
          println("="*80)

        case "check-out" =>
          // Define exit notification email content
          val subject = s"Exit Confirmation for $name"
          val body =
            s"""
               |Dear $name,
               |
               |We hope you had a pleasant visit. This email confirms your check-out.
               |
               |Thank you for visiting us.
               |
               |Best regards,
               |IT Support Team
               |""".stripMargin

          // Send exit confirmation email
//          EmailUtils.sendEmail(email, subject, body)
          println("="*80)
          println(s"Subject: ${subject}")
          println(body)
          println(s"Exit confirmation email sent to $email successfully.")
          println("="*80)



        case _ =>
          println(s"Unknown visitor status: ${visitor.status}")
      }
  }
}

class HostProcessor extends Actor {
  override def receive: Receive = {
    case visitor: Visitor =>
      val name = visitor.name
      val hostName = visitor.hostName
      val contactNumber = visitor.contactNumber
      val hostMail = visitor.hostMail
      val building = visitor.building

      visitor.status match {
        case "check-in" =>
          val subject = "Visitor Check-in Confirmation"
          val body =
            s"""
               |Dear $hostName,
               |
               |This is to inform you that your visitor, $name, has successfully checked in.
               |
               |Best regards,
               |Visitor Management System
               |""".stripMargin

          // Send check-in confirmation to host
          println("="*80)
          println(s"Subject: ${subject}")
          println(body)
          println(s"Visitor check-in confirmation sent to host at $hostMail for visitor $name.")
          println("="*80)



        case "check-out" =>
          val subject = "Visitor Check-out Notification"
          val body =
            s"""
               |Dear $hostName,
               |
               |This is to inform you that your visitor, $name, has checked out.
               |
               |Thank you for using our service.
               |
               |Best regards,
               |Visitor Management System
               |""".stripMargin

          // Send check-out notification to host
          println("="*80)
          println(s"Subject: ${subject}")
          println(body)
          println(s"Visitor check-out notification sent to host at $hostMail for visitor $name.")
          println("="*80)


        case _ =>
          println(s"Unknown visitor status: ${visitor.status}")
      }
  }
}

class SecurityProcessor extends Actor {
  override def receive: Receive = {
    case visitor: Visitor =>
      visitor.status match {
        case "check-in" =>
          println(s"Security Team notified: Visitor ${visitor.name} has checked in.")

        case "check-out" =>
          println(s"Security Team notified: Visitor ${visitor.name} has checked out.")

        case _ =>
          println(s"Unknown visitor status: ${visitor.status}")
      }
  }
}
