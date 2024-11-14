package services

import javax.inject._
import models.Visitor
import repositories.VisitorRepository
import services.KafkaProducerService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class VisitorService @Inject()(VisitorRepository: VisitorRepository, KafkaProducerService: KafkaProducerService) {

  private var visitors: List[Visitor] = List()

  def checkIn(visitorData: Visitor): Future[Long] = {
    val persistedVisitorFuture: Future[Long] = VisitorRepository.create(visitorData)

    // Then, asynchronously send the visitor data to Kafka after persistence
    persistedVisitorFuture.map { visitorId =>
      // Send the visitor data to Kafka
      KafkaProducerService.sendMessageToKafka(visitorData)

      // Return the ID from the persistence operation (you can use it as needed)
      visitorId
    }
  }

  def checkOut(visitorId: Long): Future[Boolean] = VisitorRepository.updateCheckOut(visitorId)

  def list(): Future[Seq[Visitor]] = VisitorRepository.list()

  def get(id: Long): Future[Option[Visitor]] = VisitorRepository.getById(id)


  //  def update(id: Long, Visitor: Visitor): Future[Option[Visitor]] =
  //    VisitorRepository.update(id, Visitor)
  //
  //  def delete(id: Long): Future[Boolean] = VisitorRepository.delete(id)
}
