package utils

import java.io._
import scala.util.Random

object CSVGenerator {
  def generateCSV(fileName: String, numRows: Int): Unit = {
    val writer = new BufferedWriter(new FileWriter(fileName))

    // Define the number of columns
    val numColumns = 5  // You can change the number of columns here

    // Write header (optional)
    writer.write("id,name,age,city,salary\n")

    // Write random data rows
    for (i <- 1 to numRows) {
      val id = i
      val name = "Name_" + Random.nextInt(1000)
      val age = Random.nextInt(60) + 20 // Random age between 20 and 80
      val city = "City_" + Random.nextInt(100)
      val salary = Random.nextInt(100000) + 30000 // Random salary between 30k and 130k
      writer.write(s"$id,$name,$age,$city,$salary\n")
    }

    writer.close()
    println(s"File '$fileName' has been generated with $numRows rows.")
  }
}

