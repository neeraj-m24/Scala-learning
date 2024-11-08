trait GetStarted {
    def prepare(): Unit;
}
trait KeepIngredients extends GetStarted{
       override def prepare():Unit={
        println("Keeping the ingredients!");
    }
}
trait Cook extends KeepIngredients {
   override def prepare(): Unit = {
    println("GetStarted prepare!");
    super.prepare();
    println("Cook prepare!");
  }
}

trait Seasoning {
    def applySeasoning(): Unit={
        println("Seasoning applySeasoning!");
    };
}

class Food extends Cook with Seasoning{
      def prepareFood(): Unit = {
        prepare();
        applySeasoning();
    }
}




object TraitsExmp{
  def main(args: Array[String]): Unit = {
    val food: Food = new Food()
    food.prepareFood();
  }
}