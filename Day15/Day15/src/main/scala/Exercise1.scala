import org.apache.spark.{SparkConf, SparkContext}
import scala.util.Random

object Exercise1 {

  def performSparkOperations(sc: SparkContext): Unit = {
    // Create an RDD with 10 million random numbers
    val data = Seq.fill(10000000)(Random.nextInt(100))
    val rdd = sc.parallelize(data)

    println(s"Initial partitions: ${rdd.getNumPartitions}")

    // Repartition
    val rdd4 = rdd.repartition(4)
    println(s"Partitions after repartition: ${rdd4.getNumPartitions}")

    // Coalesce
    val rdd2 = rdd4.coalesce(2)
    println(s"Partitions after coalesce: ${rdd2.getNumPartitions}")

    println("Data in 4 partitions:")
    printPartitionData(rdd4)

    println("Data in 2 partitions:")
    printPartitionData(rdd2)
  }

  def printPartitionData(rdd: org.apache.spark.rdd.RDD[Int]): Unit = {
    val partitionedData = rdd.glom().collect()
    partitionedData.zipWithIndex.foreach { case (partition, index) =>
      println(s"Partition $index: ${partition.take(5).mkString(", ")}")
    }
  }

}
