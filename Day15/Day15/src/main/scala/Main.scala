import org.apache.spark.{SparkConf, SparkContext}
import Exercise1.performSparkOperations
import Exercise2.performSparkOperations
import utils.CSVGenerator

object Main {
  def main(args: Array[String]): Unit = {
    println("Assignment Started!")

    // Spark setup
    val conf = new SparkConf().setAppName("Day 15 Tasks").setMaster("local[*]")

//      conf.set("spark.executor.instances", "2") // Simulate 2 executors for Exercise_3
    val sc = new SparkContext(conf)

//    // Call the method in Exercise1 object to perform Spark operations
//    Exercise1.performSparkOperations(sc)
//    Exercise2.performSparkOperations(sc)

//    Exercise3.performSparkOperations(sc)
//    Exercise4.performSparkOperations(sc)

//    Exercise5:

//    // Define file name and number of rows for the large dataset
//    val fileName = "large_dataset.csv"
//    val numRows = 1000000  // 1 million rows
//
//    // Call the generateCSV method from LargeCSVGenerator
//    CSVGenerator.generateCSV(fileName, numRows)

    Exercise5.performSparkOperations(sc)


    //    TODO: Read Part files, only folder name is required to do it.

    println("Application completed. Keeping Spark UI active...")
    Thread.sleep(6000000) // Keeps the application running for 10 minutes

    sc.stop()

  }
}
