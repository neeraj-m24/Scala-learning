package utils

import java.io.{BufferedWriter, FileWriter}
import scala.util.Random

object GenerateCSV {

  def main(args: Array[String]): Unit = {
    // Define possible names, genders, and age range
    val names = List("John", "Alice", "Bob", "Diana", "Eve", "Mike", "Sophia", "Liam", "Emma", "Noah")
    val genders = List("Male", "Female")
    val minAge = 15
    val maxAge = 40

    // Number of records to generate
    val recordCount = 200

    // Generate random records
    val data = (1 to recordCount).map { _ =>
      val name = names(Random.nextInt(names.length))
      val age = Random.nextInt(maxAge - minAge + 1) + minAge
      val gender = genders(Random.nextInt(genders.length))
      s"$name,$age,$gender"
    }

    // Specify the file name
    val fileName = "large_people_data.csv"

    // Write the data to the CSV file
    val writer = new BufferedWriter(new FileWriter(fileName))
    try {
      data.foreach(row => {
        writer.write(row)
        writer.newLine()
      })
      println(s"CSV file '$fileName' with $recordCount records has been created successfully.")
    } finally {
      writer.close()
    }
  }
}

