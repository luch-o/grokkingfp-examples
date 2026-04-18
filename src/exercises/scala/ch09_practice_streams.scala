import cats.effect.IO
import cats.implicits.*
import cats.effect.unsafe.implicits.global
import fs2.Stream

import ch08_CastingDie.castTheDieImpure


def castTheDie(): IO[Int] = IO.delay(castTheDieImpure())

val infiniteDieCast: Stream[IO, Int] = Stream.eval(castTheDie()).repeat

// first trhee odd numbers
val v1 = infiniteDieCast.filter(_ % 2 != 0).take(3).compile.toList

// first five cast doubling the six
val v2 = infiniteDieCast.map(die => if (die == 6) die*2 else die).take(5).compile.toList

// sum of the first 3 cast
val v3 = infiniteDieCast.take(3).compile.toList.map(_.sum)

// cast until 5 and then two mor casts
val v4 = infiniteDieCast.filter(_ == 5).take(1).append(infiniteDieCast.take(2)).compile.toList

// die is cast 100 times and values discarded
val v5 = infiniteDieCast.take(100).compile.drain

// return first 3 casts unchanged and next 3 tripled (six in total)
val v6 = infiniteDieCast.take(3).append(infiniteDieCast.map(_*3).take(3)).compile.toList

// cast the die until there are two 6 in a row
val v7 = infiniteDieCast
            .scan(0)((sixesInRow, die) => if (die == 6) sixesInRow + 1 else 0)
            .filter(_ == 2)
            .take(1)
            .compile
            .toList