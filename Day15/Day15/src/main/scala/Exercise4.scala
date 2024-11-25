import org.apache.spark.{SparkConf, SparkContext}
import scala.util.Random

object Exercise4 {

  def performSparkOperations(sc: SparkContext): Unit = {

    // Step 1: Create an RDD of integers from 1 to 10,000
    val numbersRDD = sc.parallelize(1 to 10000)

    // Step 2: Apply transformations
    val evenNumbersRDD = numbersRDD.filter(_ % 2 == 0) // Filter even numbers
    val multipliedRDD = evenNumbersRDD.map(_ * 10) // Multiply each number by 10
    val keyValueRDD = multipliedRDD.map(num => (num % 100, num)) // Create key-value pairs
    val reducedRDD = keyValueRDD.reduceByKey(_ + _) // Reduce by key (sum values)

    // Step 3: Perform an action to trigger computation and collect results
    val results = reducedRDD.collect()

    // Display results
    results.foreach(println)


  }

}
