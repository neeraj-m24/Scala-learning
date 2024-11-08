trait Task {
    def doTask(): Unit = {
      println("Executing the base Task trait, in doTask method");
    }
}

trait Cook extends Task {
  override def doTask(): Unit = {
    println("Executing the Cook trait, in overridden doTask method");
  }
}

trait Garnish extends Cook {
  override def doTask(): Unit = {
    println("Executing the Garnish trait, in overridden doTask method");
  }
}

trait Pack extends Garnish {
  override def doTask(): Unit = {
    println("Executing the Pack trait, in overridden doTask method");
  }
}

class Activity extends Task {
    def doActivity(): Unit = {
        doTask()
    }
}

object TraitsExmp {
  def main(args: Array[String]): Unit = {
    // val temp = new Activity with Cook with Garnish with Pack
    // temp.doActivity()    // output: Executing the Pack trait, in overridden doTask method

    // val temp = new Activity with Cook with Garnish
    // temp.doActivity()    // output: Executing the Garnish trait, in overridden doTask method

    // val temp = new Activity with Cook
    // temp.doActivity()    // output: Executing the Cook trait, in overridden doTask method

    // val temp = new Activity
    // temp.doActivity()    // output: Executing the base Task trait, in doTask method

    val temp = new Activity with Pack with Cook with Garnish
    temp.doActivity()    // output: Executing the Pack trait, in overridden doTask method
  }
}
