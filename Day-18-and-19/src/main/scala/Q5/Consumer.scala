package Q5

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{broadcast, col, from_json}
import org.apache.spark.sql.types.{DoubleType, StringType, StructType}

object Consumer {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Kafka Order Enrichment")
      .config("spark.hadoop.fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
      .config("spark.hadoop.fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
      .config("spark.hadoop.google.cloud.auth.service.account.enable", "true")
      .config("spark.hadoop.google.cloud.auth.service.account.json.keyfile", "/Users/neerajkumarmilan/spark-gcs-key.json")
      .master("local[*]")
      .getOrCreate()

    val userDetailsPath = "gs://spark_learning_1/day_18_and_19/question_5/user_details"
    val outputPath = "gs://spark_learning_1/day_18_and_19/question_5/enriched_orders"

    spark.sparkContext.setLogLevel("WARN")

    val kafkaSchema = new StructType()
      .add("orderId", StringType)
      .add("userId", StringType)
      .add("amount", DoubleType)

    val userDetailsDF = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(userDetailsPath)
      .cache()

    val kafkaStreamDF = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "orders")
      .option("startingOffsets", "latest")
      .load()

    val parsedStreamDF = kafkaStreamDF
      .selectExpr("CAST(value AS STRING) as jsonString")
      .select(from_json(col("jsonString"), kafkaSchema).as("data"))
      .select("data.*")

    val enrichedStreamDF = parsedStreamDF
      .join(broadcast(userDetailsDF), Seq("userId"), "left_outer")

    val query = enrichedStreamDF.writeStream
      .outputMode("append")
      .format("json")
      .option("path", outputPath)
      .option("checkpointLocation", "/tmp/spark-kafka-enrichment-checkpoints")
      .start()

    query.awaitTermination()
  }
}