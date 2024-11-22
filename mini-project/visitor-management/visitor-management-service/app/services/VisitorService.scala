package services

import models.{Visitor, VisitorIdentityProof}
import repositories.VisitorRepository

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class VisitorService @Inject()(VisitorRepository: VisitorRepository,
                               KafkaProducerService: KafkaProducerService
) {

  private var visitors: List[Visitor] = List()

  def checkIn(visitorData: Visitor): Future[Long] = {
    val persistedVisitorFuture: Future[Long] = VisitorRepository.create(visitorData)

    // Map over the Future result to send visitor data along with visitorId to Kafka
    persistedVisitorFuture.map { visitorId =>
      // Create a composite object or map containing both the visitorId and visitorData
      val kafkaVisitorData = visitorData.copy(visitorId = Some(visitorId))

      // Send the enriched visitor data to Kafka
      KafkaProducerService.sendMessage(kafkaVisitorData)

      // Return the ID from the persistence operation
      visitorId
    }
  }



  def checkOut(visitorId: Long): Future[Boolean] = {
    VisitorRepository.updateCheckOut(visitorId).flatMap {
      case Some(visitor) =>
        // Push the updated visitor to Kafka
        KafkaProducerService.sendMessage(visitor)
        Future.successful(true)
      case None =>
        Future.successful(false)
    }
  }


  def list(): Future[Seq[Visitor]] = VisitorRepository.list()

  def get(id: Long): Future[Option[Visitor]] = VisitorRepository.getById(id)

}
