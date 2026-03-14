import cats.effect.IO
import cats.implicits.*
import cats.effect.unsafe.implicits.global

import ch08_CardGame.castTheDie
import ch08_CardGame.drawAPointCard

def f1(): IO[Int] = {
    IO.delay(castTheDie())
      .orElse(IO.pure(0))
}

def f2(): IO[Int] = {
    IO.delay(drawAPointCard())
      .orElse(IO.delay(castTheDie()))
}

def f3(): IO[Int] = {
    IO.delay(castTheDie())
      .orElse(IO.delay(castTheDie()))
      .orElse(IO.pure(0))
}

def f4(): IO[Int] = {
    for {
        card <- IO.delay(castTheDie()).orElse(IO.pure(0))
        dieCast <- IO.delay(drawAPointCard()).orElse(IO.pure(0))
    } yield card + dieCast
}

def f5(): IO[Int] = {
    (
        for {
            card <- IO.delay(castTheDie()).orElse(IO.pure(0))
            dieCast1 <- IO.delay(drawAPointCard()).orElse(IO.pure(0))
            dieCast2 <- IO.delay(drawAPointCard()).orElse(IO.pure(0))
        } yield card + dieCast1 + dieCast2
    ).orElse(IO.pure(0))
}
