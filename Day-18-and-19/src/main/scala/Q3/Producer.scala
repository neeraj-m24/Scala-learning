package Q3

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, expr, struct, to_json}
import org.apache.spark.sql.streaming.Trigger

object Producer {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Streaming kafka Producer")
      .master("local[*]")
      .getOrCreate()

    val transactionLogsPath = "gs://spark_learning_1/day_18_and_19/question_3/transaction_logs"

    val transactionDF = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(transactionLogsPath)
      .toDF("userId", "transactionId", "amount")

    val rateStream = spark.readStream
      .format("rate")
      .option("rowsPerSecond", 1)
      .load()

    val indexedCsvDF = transactionDF.withColumn("index", expr("row_number() over (order by userId) - 1"))

    val streamingDF = rateStream
      .withColumn("index", col("value"))
      .join(indexedCsvDF, "index")
      .select(col("userId").cast("string").as("key"), to_json(struct("userId", "transactionId", "amount")).as("value"))

    val kafkaSink = streamingDF.writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("topic", "transactions")
      .option("checkpointLocation", "/tmp/spark-kafka-checkpoints")
      .trigger(Trigger.ProcessingTime("1 second"))
      .start()

    kafkaSink.awaitTermination()
  }
}
