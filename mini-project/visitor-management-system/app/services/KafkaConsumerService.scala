package services

import javax.inject._
import org.apache.kafka.clients.consumer.{KafkaConsumer, ConsumerConfig}
import java.util.{Properties, Collections}
import org.apache.kafka.common.serialization.StringDeserializer
import play.api.libs.json._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.scaladsl.Source
//import akka.stream.actor.ActorSubscriberMessage.OnNext

@Singleton
class KafkaConsumerService @Inject()(implicit ec: ExecutionContext, mat: Materializer, actorSystem: ActorSystem) {

  // Kafka consumer configuration
  private val props = new Properties()
  props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ConsumerConfig.GROUP_ID_CONFIG, "visitor-notification-group")
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)

  // Create the Kafka consumer instance
  private val consumer = new KafkaConsumer[String, String](props)

  // Function to start consuming messages
  def startConsuming(): Future[Unit] = Future {
    // Subscribe to the "visitor-checkin-topic"
    consumer.subscribe(Collections.singletonList("visitor-checkin-topic"))

    // Poll for new records
    while (true) {
      val records = consumer.poll(java.time.Duration.ofMillis(1000))

      records.forEach(record => {
        // Deserialize the message
        val visitorJson = Json.parse(record.value())

        // Process the visitor data (e.g., send notifications)
        processMessage(visitorJson)
      })
    }
  }

  // Process the message received from Kafka
  private def processMessage(visitorJson: JsValue): Unit = {
    val visitorName = (visitorJson \ "name").as[String]
    val hostName = (visitorJson \ "hostName").as[String]

    // Simulate sending notifications (e.g., send email, notify security, etc.)
    println(s"$visitorName has checked in to meet $hostName.")

    // Here you can implement email notifications, security alerts, etc.
    // For example, you can call your email service, IT service, or security service here.
  }

  // Gracefully stop the consumer
  def stopConsuming(): Unit = {
    consumer.close()
  }
}
