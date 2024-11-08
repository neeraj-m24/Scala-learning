
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import scala.concurrent.Future

def provideMeData():Future[String] = {
    Future{
        println("Making the resource ready")
        Thread.sleep(500)
        "Waking up!"
}
        
}

@main def run() = {
    provideMeData().onComplete{
        case Success(value) => println(s"Thread ran properly with $value")
        case Failure(exception) => println(s"Failed due to $exception")
    }
    Thread.sleep(1000)
}