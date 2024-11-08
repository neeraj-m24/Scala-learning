import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import scala.util.Random

def generateRandomNumberInThreads(): Future[String] = {
  val resultPromise = Promise[String]()
  var stopFlag = false
  def createThread(threadName:String):Thread = {
        new Thread(new Runnable {
        def run(): Unit = {
        while (!stopFlag) {
            val randomNum = Random.nextInt(10000)
            println(s"${threadName} generated: $randomNum")
            if (randomNum == 1567) {
            stopFlag = true
            resultPromise.success(s"${threadName} has generated $randomNum")
            println(s"${threadName} finished!")
            }
        }
        }
    })
  }
  def initiateThreads():Unit = {

  val firstThread = createThread("FirstThread")
  val secondThread = createThread("SecondThread")
  val thirdThread = createThread("ThirdThread")

  firstThread.start()
  secondThread.start()
  thirdThread.start()

  }
  initiateThreads()
  resultPromise.future
  
}

@main def runApplication(): Unit = {
  val futureResult = generateRandomNumberInThreads()
  futureResult.onComplete {
    case Success(message) => println(s"Future completed with: $message")
    case Failure(error) => println(s"Future failed with error: $error")
  }
  println("Main thread is waiting for the thread result...")
}

