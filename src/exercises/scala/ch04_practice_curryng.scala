// 1
def largerThan(thresh: Int)(number: Int): Boolean = {
    number > thresh
}

List(5, 1, 2, 4, 0).filter(largerThan(4))
List(5, 1, 2, 4, 0).filter(largerThan(1))

//2
def divisibleBy(divisor: Int)(number: Int): Boolean = {
    number % divisor == 0
}

List(5, 1, 2, 4, 15).filter(divisibleBy(5))
List(5, 1, 2, 4, 15).filter(divisibleBy(2))

// 3 
def shorterThan(thresh: Int)(word: String): Boolean = {
    word.length < thresh
}

List("scala", "ada").filter(shorterThan(4))
List("scala", "ada").filter(shorterThan(7))

// 4
def sCountGreaterThan(thresh: Int)(word: String): Boolean = {
    val sCount: String => Int = word => word.length - word.replace("s", "").length
    sCount(word) > thresh
}

List("rust", "ada").filter(sCountGreaterThan(2))
List("rust", "ada").filter(sCountGreaterThan(0))
