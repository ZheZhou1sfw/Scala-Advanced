package lectures.part1as.part2afp

object LazyEvaluation extends App {

  lazy val x: Int = throw new RuntimeException
}
