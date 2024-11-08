import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet, SQLException, Statement};

case class candidateName(sno: Int, name: String, city: String);

class dbWork {
  val url = "jdbc:mysql://scaladb.mysql.database.azure.com:3306/neeraj_milan";
  val username = "mysqladmin";
  val password = "Password@12345";
  var connection: Connection = null;
  var preparedStatement: PreparedStatement = null;
  var statement: Statement = null;
  var resultSet: ResultSet = null;

  def createTable(): Unit = {
    try {
      connection = DriverManager.getConnection(url, username, password);
      val query = """
        CREATE TABLE IF NOT EXISTS candidateName (
          sno INT PRIMARY KEY,
          name VARCHAR(50),
          city VARCHAR(50)
        );
      """
      preparedStatement = connection.prepareStatement(query);
      preparedStatement.executeUpdate();
      println("Candidate table is created..... thanks");
    } catch {
      case e: SQLException => e.printStackTrace();
      case e: Exception => e.printStackTrace();
    } finally {
      try {
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
      } catch {
        case se: SQLException => se.printStackTrace();
      }
    }
  }

  def insertData(data: candidateName): Unit = {
    try {
      connection = DriverManager.getConnection(url, username, password);

      val query = "INSERT INTO candidateName (sno, name, city) VALUES (?, ?, ?)";

      preparedStatement = connection.prepareStatement(query);
      
      preparedStatement.setInt(1, data.sno);
      preparedStatement.setString(2, data.name);
      preparedStatement.setString(3, data.city);
      
      preparedStatement.executeUpdate();

      println(s"Data for candidate ${data.name} inserted successfully.");
    } catch {
      case e: SQLException => e.printStackTrace();
      case e: Exception => e.printStackTrace();
    } finally {
      try {
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
      } catch {
        case se: SQLException => se.printStackTrace();
      }
    }
  }

  def viewData(): Unit = {
    try {
      connection = DriverManager.getConnection(url, username, password);
      statement = connection.createStatement();

      val query = "SELECT * FROM candidateName";
      
      resultSet = statement.executeQuery(query);

      println("Records in 'candidateName' table:");
      
      while (resultSet.next()) {
        val sno = resultSet.getInt("sno");
        val name = resultSet.getString("name");
        val city = resultSet.getString("city");

        println(s"Sno: $sno, Name: $name, City: $city");
      }

    } catch {
      case e: SQLException => e.printStackTrace();
      case e: Exception => e.printStackTrace();
    } finally {
      try {
        if (resultSet != null) resultSet.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
      } catch {
        case se: SQLException => se.printStackTrace();
      }
    }
  }
}

object Main extends App {
  val db = new dbWork();
//   db.createTable();
  val candidateData: Array[(Int, String, String)] = Array(
      (1, "Alice", "New York"),
      (2, "Bob", "Los Angeles"),
      (3, "Charlie", "Chicago"),
      (4, "Diana", "Houston"),
      (5, "Eve", "Phoenix"),
      (6, "Frank", "Philadelphia"),
      (7, "Grace", "San Antonio"),
      (8, "Hank", "San Diego"),
      (9, "Ivy", "Dallas"),
      (10, "Jack", "San Jose"),
      (11, "Kathy", "Austin"),
      (12, "Leo", "Jacksonville"),
      (13, "Mona", "Fort Worth"),
      (14, "Nina", "Columbus"),
      (15, "Oscar", "Charlotte"),
      (16, "Paul", "San Francisco"),
      (17, "Quinn", "Indianapolis"),
      (18, "Rita", "Seattle"),
      (19, "Steve", "Denver"),
      (20, "Tina", "Washington"),
      (21, "Uma", "Boston"),
      (22, "Vince", "El Paso"),
      (23, "Wendy", "Detroit"),
      (24, "Xander", "Nashville"),
      (25, "Yara", "Portland"),
      (26, "Zane", "Oklahoma City"),
      (27, "Aiden", "Las Vegas"),
      (28, "Bella", "Louisville"),
      (29, "Caleb", "Baltimore"),
      (30, "Daisy", "Milwaukee"),
      (31, "Ethan", "Albuquerque"),
      (32, "Fiona", "Tucson"),
      (33, "George", "Fresno"),
      (34, "Hazel", "Mesa"),
      (35, "Ian", "Sacramento"),
      (36, "Jill", "Atlanta"),
      (37, "Kyle", "Kansas City"),
      (38, "Luna", "Colorado Springs"),
      (39, "Mason", "Miami"),
      (40, "Nora", "Raleigh"),
      (41, "Owen", "Omaha"),
      (42, "Piper", "Long Beach"),
      (43, "Quincy", "Virginia Beach"),
      (44, "Ruby", "Oakland"),
      (45, "Sam", "Minneapolis"),
      (46, "Tara", "Tulsa"),
      (47, "Ursula", "Arlington"),
      (48, "Victor", "New Orleans"),
      (49, "Wade", "Wichita"),
      (50, "Xena", "Cleveland")
    );
  
  val candidate: Array[candidateName] = candidateData.map {
    case (sno, name, city) => candidateName(sno, name, city)
  }
  candidate.foreach(candi => db.insertData(candi));

//   db.viewData();
}

// command to run: scala -classpath mysql-connector-j-8.0.33.jar task3.scala