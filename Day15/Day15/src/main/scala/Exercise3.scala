import org.apache.spark.{SparkConf, SparkContext}
import scala.util.Random

object Exercise3 {

  def performSparkOperations(sc: SparkContext): Unit = {

    val lines = sc.parallelize(List.fill(1000000)("Lorem ipsum dolor sit amet"))

    val wordsRDD = lines.flatMap(line => line.split(" "))
    val wordPairsRDD = wordsRDD.map(word => (word, 1))
    val wordCountsRDD = wordPairsRDD.reduceByKey(_ + _)

    wordCountsRDD.saveAsTextFile("./exercise_3_word_counts")


  }

}
