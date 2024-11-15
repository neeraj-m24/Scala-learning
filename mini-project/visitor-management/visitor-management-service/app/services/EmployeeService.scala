package services

import models.Employee
import repositories.EmployeeRepository

import javax.inject._
import scala.concurrent.Future

@Singleton
class EmployeeService @Inject()(employeeRepository: EmployeeRepository) {


  def create(employeeData: Employee): Future[Long] = employeeRepository.create(employeeData)

  def list(): Future[Seq[Employee]] = employeeRepository.list()

  def isEmployeeEmailValid(email: String): Future[Boolean] = employeeRepository.isEmployeeEmailValid(email)

  def get(id: Long): Future[Option[Employee]] = employeeRepository.getById(id)
}
