package repository

import model.{Equipment, EquipmentAllocation, EquipmentAllocationTable, EquipmentTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted
import utils.{AllocationStatus, EquipmentStatus}
import utils.AllocationStatus.AllocationStatus
import utils.EquipmentStatus.EquipmentStatus

import java.time.LocalDateTime
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class EquipmentAllocationRepository @Inject() (dbConfigProvider:DatabaseConfigProvider)(implicit ec:ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val equipmentAllocations = lifted.TableQuery[EquipmentAllocationTable]
  private val equipments = lifted.TableQuery[EquipmentTable]


  def list(): Future[Seq[EquipmentAllocation]] = db.run(equipmentAllocations.result)

  def getById(id: Long): Future[Option[EquipmentAllocation]] = {
    db.run(equipmentAllocations.filter(_.id === id).result.headOption)
  }

  def add(equipmentAllocation: EquipmentAllocation): Future[EquipmentAllocation] = {
    db.run(equipmentAllocations returning equipmentAllocations.map(_.id) += equipmentAllocation).map(id => equipmentAllocation.copy(id = id))
  }

  def update(equipmentAllocation: EquipmentAllocation): Future[Int] = {
    db.run(equipmentAllocations.filter(_.id === equipmentAllocation.id).update(equipmentAllocation))
  }

  def delete(id: Long): Future[Int] = {
    db.run(equipmentAllocations.filter(_.id === id).delete)
  }

  //for updating status in equipment allocation
  def updateStatus(id: Long, status: AllocationStatus): Future[EquipmentAllocation] = {
    val updateQuery = equipmentAllocations.filter(_.id === id).map(_.status).update(status).flatMap {
      case 0 => DBIO.failed(new Exception("Equipment Allocation not found"))
      case _ => equipmentAllocations.filter(_.id === id).result.head
    }
    db.run(updateQuery)


  }

  def allocate(equipmentAllocation: EquipmentAllocation): Future[(EquipmentAllocation,Equipment)] = {
    val transaction = for {
      _ <- equipmentAllocations += equipmentAllocation
      _ <- equipments.filter(_.id === equipmentAllocation.equipmentId).map(_.status).update(EquipmentStatus.ALLOCATED)
      equipment<- equipments.filter(_.id === equipmentAllocation.equipmentId).result.head
    } yield (equipmentAllocation,equipment)
    db.run(transaction.transactionally)
  }
  def returnEquipment(id: Long, status: EquipmentStatus): Future[(EquipmentAllocation,Equipment)] = {


    val transaction = for {
      equipmentAllocation <- equipmentAllocations.filter(e1=>e1.id===id && e1.status===AllocationStatus.ACTIVE).result.head
      _ <- equipmentAllocations.filter(_.id === id).map(_.status).update(AllocationStatus.INACTIVE)
      equipment <- equipments.filter(e2=>e2.id===equipmentAllocation.equipmentId && e2.status===EquipmentStatus.ALLOCATED).map(_.status).update(status).flatMap {
        case 0 => {
          println("Equipment not found")
          DBIO.failed(new Exception("Equipment not found"))
        }
        case _ => equipments.filter(_.id === equipmentAllocation.equipmentId).result.head
      }
    } yield (equipmentAllocation, equipment)

    db.run(transaction.transactionally)


  }
  //using joins to get the overdue equipment and equipment allocation details

  def findOverdueAllocations() =   {
    val now: LocalDateTime = LocalDateTime.now()

    println("Finding overdue allocations...")

    //create a join query to join equipment and equipment allocation tables where expected return date is before now
    val query = for {
      (allocation, equipment) <- equipmentAllocations join equipments on (_.equipmentId === _.id) if allocation.expectedReturnDate < now && allocation.status === AllocationStatus.ACTIVE
    } yield (allocation, equipment)

    db.run(query.result)

  }

}


