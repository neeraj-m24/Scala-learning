import scala.collection.mutable.ListBuffer
import scala.io.StdIn.readLine

case class Employee(eid: Int, ename: String, city: String) {
  override def toString: String = s"  |__($eid, $ename, $city)"
}

case class Department(departmentName: String, employees: ListBuffer[Employee] = ListBuffer(), subDepartments: ListBuffer[Department] = ListBuffer()) {
  override def toString: String = s"  |__$departmentName"
}

class Organization {
  // To ensure the root department is only added once, we'll create a flag
  var rootDepartment: Option[Department] = None

  // Method to add a department, including employees
  def addDepartment(root: Department, parentDept: String, deptName: String, employees: ListBuffer[Employee]): String = {
    // Handle the root department creation if it's not yet created
    if (rootDepartment.isEmpty) {
      rootDepartment = Some(Department("Organization"))
    }

    // Find or create the parent department
    val parent = findOrCreateDepartment(root, parentDept)
    // Find or create the current department
    val currentDept = findOrCreateDepartment(parent, deptName)

    // Add employees to the current department
    currentDept.employees ++= employees
  }

  // Method to find an existing department or create it if it doesn't exist
  private def findOrCreateDepartment(parentDept: Department, deptName: String): Department = {

    parentDept.subDepartments.find(_.departmentName == deptName) match {
      case Some(dept) => dept
      case None =>
        val newDept = Department(deptName)
        parentDept.subDepartments += newDept
        newDept
    }
  }

  // Method to display the organization structure
  def displayStructure(root: Department): Unit = {
    println(s"\nOrganization Structure:")
    printDepartment(root, 1)
  }

  // Helper method to recursively print departments and employees
  private def printDepartment(dept: Department, indent: Int): Unit = {
    println(" " * indent + s"|--- ${dept.departmentName}")
    dept.employees.foreach(emp => println(" " * (indent + 2) + s"|--- $emp"))
    dept.subDepartments.foreach(subDept => printDepartment(subDept, indent + 2))
  }
}

object OrganizationApp {
  def main(args: Array[String]): Unit = {
    val org = new Organization()
    val root = Department("Organization")
    var continue = true

    println("Welcome to the Organization Management System!")
    while (continue) {
      println("\nChoose an option:")
      println("1. View Current Organization Structure")
      println("2. Add into Organization")
      println("3. Exit")
      val choice = readLine().trim

      choice match {
        case "1" => org.displayStructure(root)
        case "2" =>
          val (parentDept, deptName, employees) = getUserInputs()
          println(org.addDepartment(root, parentDept, deptName, employees))
        case "3" => continue = false
        case _ => println("Invalid choice, please try again.")
      }
    }
  }

  // Method to get user inputs for department and employee details
  def getUserInputs(): (String, String, ListBuffer[Employee]) = {
    // Ensuring the parent department cannot be empty
    var parentDept = ""
    while (parentDept.trim.isEmpty) {
      parentDept = readLine("Enter the parent department name: ").trim
      if (parentDept.isEmpty) {
        println("Parent department cannot be empty. Please enter a valid department name.")
      }
    }

    // Ensuring the department name cannot be empty
    var deptName = ""
    while (deptName.trim.isEmpty) {
      deptName = readLine("Enter the department name: ").trim
      if (deptName.isEmpty) {
        println("Department name cannot be empty. Please enter a valid department name.")
      }
    }

    var employees = ListBuffer[Employee]()

    var addMoreEmployees = true
    while (addMoreEmployees) {
      println("1. Add employee details")
      println("2. Done adding employees")
      val empChoice = readLine().trim

      empChoice match {
        case "1" =>
          // Ensuring the employee ID is valid
          var eid = -1
          while (eid <= 0) {
            val eidStr = readLine("Enter Employee ID: ").trim
            if (eidStr.isEmpty || !eidStr.forall(_.isDigit)) {
              println("Employee ID must be a positive integer. Please enter a valid Employee ID.")
            } else {
              eid = eidStr.toInt
            }
          }

          // Ensuring the employee name is not empty
          var ename = ""
          while (ename.trim.isEmpty) {
            ename = readLine("Enter Employee name: ").trim
            if (ename.isEmpty) {
              println("Employee name cannot be empty. Please enter a valid name.")
            }
          }

          // Ensuring the employee city is not empty
          var city = ""
          while (city.trim.isEmpty) {
            city = readLine("Enter Employee city: ").trim
            if (city.isEmpty) {
              println("Employee city cannot be empty. Please enter a valid city.")
            }
          }

          employees += Employee(eid, ename, city)

        case "2" => addMoreEmployees = false
        case _ => println("Invalid option, try again.")
      }
    }

    (parentDept, deptName, employees)
  }
}
