import org.apache.spark.{SparkConf, SparkContext}
import scala.util.Random

object Exercise2 {

  def performSparkOperations(sc: SparkContext): Unit = {

    // Create an RDD with numbers from 1 to 1000
    val rdd = sc.parallelize(1 to 1000)

    // Apply narrow transformations
    val mappedRDD = rdd.map(x => x * 2) // Double each number
    val filteredRDD = mappedRDD.filter(x => x % 4 == 0) // Keep only numbers divisible by 4

    // Map numbers into key-value pairs (number % 10, number)
    val pairedRDD = filteredRDD.map(x => (x % 10, x))

    // Apply a wide transformation: groupByKey
    val groupedRDD = pairedRDD.groupByKey()

    // Reduce by key (sum the values in each group)
    val reducedRDD = pairedRDD.reduceByKey(_ + _)

    //    many files are generated when using saveAsTextFile is due to the way Spark processes and partitions data.
    //    Each partition in the RDD generates a separate output file during the save operation.
    // Save the grouped results to a file
    groupedRDD.saveAsTextFile("./exercise_2_grouped_result")

    // Save the reduced results to a file (if using reduceByKey)
    reducedRDD.saveAsTextFile("./exercise_2_reduced_result")


  }

}
