import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

def setTimeout(delay: Int, task: => Unit): Unit = {
  Future {
    Thread.sleep(delay)
    task
  }
}

class JobRuner(jobId:String,time:Int,logic: => Unit){
    def this(){
        setTimeout(time,logic)
    }
}

@main def execute(): Unit = {
    JobRuner("hvhsvdhs-dxs-jbjdbsj-jbjb",20,{() => println("Executed")}).onComplete
}