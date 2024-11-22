import org.apache.spark.sql.SparkSession

object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Day 14 Assignment")
      .master("local[*]")
      .getOrCreate()

//    Task 1: Given a collection of strings, write a Spark program to count the total number of words in the collection using RDD transformations and actions.
    val sentance = Seq("Hi What's up","I am doing nothing","Do I know you")
    val rdd1 = spark.sparkContext.parallelize(sentance)
    val wordsRdd1 = rdd1.flatMap(line => line.split(" "))
    val wordsMapRdd1 = wordsRdd1.map(word => (word,1))
    val wordsPairRdd1 = wordsMapRdd1.reduceByKey(_+_)

    val wordsCount = wordsPairRdd1.map(_._2).sum()
    println(s"Taks1: no. of words: ${wordsCount}")

    /*
    * Task 2. Create two RDDs containing numbers and write a Spark program to compute their Cartesian product using RDD transformations.
    * */

    val seq1 = spark.sparkContext.parallelize((Seq(1,2,3)))
    val seq2 = spark.sparkContext.parallelize(Seq(2,4,6))

    val cartesianRdd = seq1.cartesian(seq2)
    print("Task2: Cartesian Product : ")
    cartesianRdd.collect().foreach{
      case (a,b) => println(s"( ${a}, ${b} )")
    }

    /*
    * Task 3. Create an RDD from a list of integers and filter out all even numbers using a Spark program.
    * */

    val numbers = Seq(1,2,3,4,6,7,3,6,2,7,1,9,11,22,53,53,86,345)
    val rdd2 = spark.sparkContext.parallelize(numbers)
    val oddNumberRdd = rdd2.filter(num => num%2 !=0)

    print("Task 3: ")
    oddNumberRdd.collect().foreach(println)

    /*
    * Task 4. Write a Spark program to count the frequency of each character in a given collection of strings using RDD transformations.
    *
    * */

    val words = Seq("my","name","is","what","is","your","name","you","are")
    val rdd4 = spark.sparkContext.parallelize((words))

    val wordsCountRdd4 = rdd4.map(word => (word,1))
    val wordsCount1 = wordsCountRdd4.reduceByKey(_+_)
    println("Task 4: ")
    wordsCount1.collect().foreach{
      case (a,b) => println(s"count of '${a}' is ${b}")
    }


    /*
    * Task 5. Create an RDD from a list of tuples `(id, score)` and write a Spark program to calculate the average score for all records.
    *
    * */
    val records = Seq(
      (1, 90),
      (2, 80),
      (3, 85),
      (4, 70),
      (5, 95)
    )

    val recordsRdd = spark.sparkContext.parallelize(records)
    val totalScore = recordsRdd.map(_._2).reduce(_+_)
    val totalRecords = recordsRdd.count()

    println(s"Task 5: Average of all records: ${totalScore.toDouble /totalRecords}")

    /*
    * Task 6. Create two RDDs containing key-value pairs `(id, name)` and `(id, score)`. Write a Spark program to join these RDDs on `id` and produce `(id, name, score)`.
    * */
    val names = Seq(
      (5, "Harsh"),
      (3, "Adarsh"),
      (2, "Yash"),
      (1, "Raj"),
      (4, "Deep")
    )

    val namesRdd = spark.sparkContext.parallelize(names)
    val joinedRecord = namesRdd.join(recordsRdd)

    val namesRecord = joinedRecord.map{
      case (id,(name,score)) => (id,name,score)
    }

    println("Task 6: ")
    namesRecord.collect().foreach{
      case (id,name,score) => println(s"( ${id}, ${name}, ${score} )")
    }

   /*
   * Task 7. Write a Spark program to perform a union operation on two RDDs of integers and remove duplicate elements from the resulting RDD.
   * */
  val num1 = Seq(1,2,3,4)
  val num2 = Seq(2,1,4,2,6,4,7,8,2)

    val num1Rdd7 = spark.sparkContext.parallelize(num1)
    val num2Rdd7 = spark.sparkContext.parallelize(num2)
    val num12UnionRdd = num1Rdd7.union(num2Rdd7)
    val distinctRdd7 = num12UnionRdd.distinct()

    println("Task 7: ")
    distinctRdd7.collect().foreach(println)


  /*
  * TODO: Create an RDD from a list of strings where each string represents a CSV row. Write a Spark program to parse the rows and
  * filter out records where the age is less than 18.
  * */


    /*
    * Task 9. Create an RDD of integers from 1 to 100 and write a Spark program to compute their sum using an RDD action.
    * */
    val rdd9 = spark.sparkContext.parallelize(1 to 100)
    val sumRdd9 = rdd9.reduce(_+_)
    println(s"Task 9: Total sum of 1 to 100 RDD is : ${sumRdd9}")

    /*
    * 10. Write a Spark program to group an RDD of key-value pairs `(key, value)` by key and compute the sum of values for each key.
    * */

    val data = Seq(
      ("a", 1),
      ("b", 2),
      ("a", 3),
      ("b", 4),
      ("c", 5)
    )

    val rdd10 = spark.sparkContext.parallelize(data)
    val groupByKeyRdd = rdd10.groupByKey()   // e.g. ("a", Iterable(1, 3)), ("b", Iterable(2, 4)) etc

    // mapValues works on each pair of (key, value)
    val sumForGroupByKeyRdd = groupByKeyRdd.mapValues(values => values.sum)

    println("Task 10: ")

    sumForGroupByKeyRdd.collect().foreach{
      case (key,value) => println(s"'${key}' : ${value}")
    }

    spark.stop()
  }
}