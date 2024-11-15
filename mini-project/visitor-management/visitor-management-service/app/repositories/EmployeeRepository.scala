package repositories

import models.Employee
import models.db.EmployeeTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}



class EmployeeRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._


  private val employees = TableQuery[EmployeeTable]

  //  def create(Employee: Employee): Future[Int] = db.run(Employees += Employee)
  def create(employee: Employee): Future[Long] = {
    val insertQueryThenReturnId = employees
      .map(v => (v.employeeName, v.organisation, v.building, v.email, v.employeeType, v.contactNo))
      .returning(employees.map(_.employeeId))  // Ensure this returns a Long value

    // Execute the query and return the inserted visitor's ID
    db.run(insertQueryThenReturnId += (
      employee.employeeName,
      employee.organisation,
      employee.building,
      employee.email,
      employee.employeeType,
      employee.contactNo
    )).map(_.head)  // Extract the first element from the result (the ID)
  }
  def isEmployeeEmailValid(email: String): Future[Boolean] = {
    db.run(employees.filter(_.email === email).exists.result)
  }

  def list(): Future[Seq[Employee]] = db.run(employees.result)
  def getById(id: Long): Future[Option[Employee]] = db.run(employees.filter(_.employeeId === id).result.headOption)

}
