package Q1

import org.apache.spark.sql.SparkSession
import scala.util.Random

object Question1 {

  def createData(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Broadcast Join Example")
      .config("spark.hadoop.fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
      .config("spark.hadoop.fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
      .config("spark.hadoop.google.cloud.auth.service.account.enable", "true")
      .config("spark.hadoop.google.cloud.auth.service.account.json.keyfile", "/Users/neerajkumarmilan/spark-gcs-key.json") // Use your path
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // Generate fake user data
    def makeUserData(totalUsers: Int): Seq[(Int, String)] = {
      val rand = new Random()
      (1 to totalUsers).map { id =>
        (id, s"User_${rand.alphanumeric.take(5).mkString}")
      }
    }

    // Generate fake transaction logs
    def makeTxnLogs(totalTxns: Int, totalUsers: Int): Seq[(Int, String, Double)] = {
      val rand = new Random()
      (1 to totalTxns).map { _ =>
        val uid = rand.nextInt(totalUsers) + 1
        val txnId = s"txn_${rand.alphanumeric.take(6).mkString}"
        val amount = rand.nextDouble() * 1000
        (uid, txnId, amount)
      }
    }

    // Dataset sizes
    val users = 100
    val transactions = 10000

    val usersDF = makeUserData(users).toDF("id", "username")
    val txnsDF = makeTxnLogs(transactions, users).toDF("id", "txn_id", "txn_amount")

    // Save paths
    val usersPath = "gs://spark_learning_1/day_18_and_19/question_1/users.csv"
    val txnsPath = "gs://spark_learning_1/day_18_and_19/question_1/txns.csv"

    usersDF.write
      .option("header", "true")
      .csv(usersPath)

    txnsDF.write
      .option("header", "true")
      .csv(txnsPath)

    println(s"Data saved at $usersPath and $txnsPath")

    spark.stop()
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Broadcast Join Example")
      .config("spark.hadoop.fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
      .config("spark.hadoop.fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
      .config("spark.hadoop.google.cloud.auth.service.account.enable", "true")
      .config("spark.hadoop.google.cloud.auth.service.account.json.keyfile", "/Users/neerajkumarmilan/spark-gcs-key.json") // Update this
      .master("local[*]")
      .getOrCreate()

    // Paths to datasets
    val usersPath = "gs://spark_learning_1/day_18_and_19/question_1/users.csv"
    val txnsPath = "gs://spark_learning_1/day_18_and_19/question_1/txns.csv"

    // Read users and transactions
    val users = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(usersPath)
      .toDF("user_id", "name")

    val transactions = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(txnsPath)
      .toDF("user_id", "txn_type", "amount")

    // Perform broadcast join
    val joinedDF = transactions
      .join(org.apache.spark.sql.functions.broadcast(users), Seq("user_id"))

    // Show results
    println("Joined dataset:")
    joinedDF.show(10)

    spark.stop()
  }
}


