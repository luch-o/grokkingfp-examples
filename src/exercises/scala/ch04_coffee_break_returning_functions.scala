// 1
def largerThan(thresh: Int): Int => Boolean = {
    x => x > thresh
}

List(5, 1, 2, 4, 0).filter(largerThan(4))
List(5, 1, 2, 4, 0).filter(largerThan(1))

//2
def divisibleBy(divisor: Int): Int => Boolean = {
    x => (x % divisor) == 0
}

List(5, 1, 2, 4, 15).filter(divisibleBy(5))
List(5, 1, 2, 4, 15).filter(divisibleBy(2))

// 3 
def shorterThan(thresh: Int): String => Boolean = {
    word => word.length < thresh
}

List("scala", "ada").filter(shorterThan(4))
List("scala", "ada").filter(shorterThan(7))

// 4
def sCountGreaterThan(thresh: Int): String => Boolean = {
    val sCount: String => Int = word => word.length - word.replace("s", "").length
    word => sCount(word) > thresh
}

List("rust", "ada").filter(sCountGreaterThan(2))
List("rust", "ada").filter(sCountGreaterThan(0))
