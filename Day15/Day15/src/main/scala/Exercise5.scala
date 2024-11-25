import org.apache.spark.{SparkConf, SparkContext}

object Exercise5 {

  def performSparkOperations(sc: SparkContext): Unit = {

    // Loading file
    val filePath = "./large_dataset.csv" // Change this to your dataset path
    val rdd = sc.textFile(filePath)

    // Task: Count the number of rows in the RDD (this is a narrow transformation)
    val rowCount = rdd.count()
    println(s"Total number of rows: $rowCount")

    // Function to perform operations on the RDD with different partition sizes
    def performPartitioningAndSort(partitionCount: Int): Unit = {
      // Repartition the RDD into `partitionCount` partitions
      val partitionedRDD = rdd.repartition(partitionCount)
      println(s"RDD partitioned into $partitionCount partitions")

      // Perform a wide transformation: Sort the data (which triggers a shuffle)
      val sortedRDD = partitionedRDD.sortBy(identity)

      // Generate a unique output directory name based on the number of partitions
      val outputDir = s"output_files/exercise_5/partitioned_$partitionCount"

      // Write the sorted data to disk (save to a directory)
      sortedRDD.saveAsTextFile(outputDir)

      println(s"Output written to: $outputDir")
    }

    // Partition the RDD into 2, 4, and 8 partitions, and perform the operations
    for (partitions <- List(2, 4, 8)) {
      // Measure execution time for partitioning and sorting
      val startTime = System.currentTimeMillis()
      performPartitioningAndSort(partitions)
      val endTime = System.currentTimeMillis()

      val executionTime = endTime - startTime
      println(s"Execution time for $partitions partitions: $executionTime ms")
    }


  }
}
