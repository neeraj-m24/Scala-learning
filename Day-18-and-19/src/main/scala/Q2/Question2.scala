package Q2

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{avg, sum}

import scala.util.Random

object Question2 {
  def main(args: Array[String]): Unit = {
    // Create Spark session
    val spark = SparkSession.builder()
      .appName("Spark DataFrame Performance Test")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // Generate a sample dataset for sales data
    val numRecords = 1000000
    val salesData = (1 to numRecords).map { id =>
      val region = List("North", "South", "East", "West")(Random.nextInt(4))
      val amount = Random.nextDouble() * 1000
      val category = List("Burgers", "Pizzas", "Beverages")(Random.nextInt(3))
      (id, region, amount, category)
    }.toDF("saleId", "region", "amount", "category")

    // Function to perform transformations
    def performTransformations(df: org.apache.spark.sql.DataFrame): Unit = {
      val byRegion = df.groupBy("region").agg(avg("amount").as("Average Amount"))
      val byCategory = df.groupBy("category").agg(sum("amount").as("Total Sales"))
      byRegion.show(5)
      byCategory.show(5)
    }

    // Function to measure execution time
    def time[T](block: => T): Double = {
      val start = System.nanoTime()
      block
      (System.nanoTime() - start) / 1e6
    }

    // Measure time without caching
    val timeWithoutCache = time {
      performTransformations(salesData)
      performTransformations(salesData)
    }
    println(s"Execution time without caching: $timeWithoutCache ms")

    // Cache the DataFrame and measure time with caching
    salesData.cache()
    val timeWithCache = time {
      performTransformations(salesData)
      performTransformations(salesData)
    }
    println(s"Execution time with caching: $timeWithCache ms")

    // Stop the Spark session
    spark.stop()
  }
}

