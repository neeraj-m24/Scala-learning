package Q5

import org.apache.spark.sql.SparkSession

object ReadEnrichedData {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Read Enriched Orders from GCS")
      .config("spark.hadoop.fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
      .config("spark.hadoop.fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
      .config("spark.hadoop.google.cloud.auth.service.account.enable", "true")
      .config("spark.hadoop.google.cloud.auth.service.account.json.keyfile", "/Users/neerajkumarmilan/spark-gcs-key.json")
      .master("local[*]")
      .getOrCreate()

    val enrichedOrdersPath = "gs://spark_learning_1/day_18_and_19/question_5/enriched_orders/"

    val enrichedOrdersDF = spark.read
      .json(enrichedOrdersPath)

    enrichedOrdersDF.show(10)

    println("Enriched orders successfully fetched.")

    spark.stop()
  }
}