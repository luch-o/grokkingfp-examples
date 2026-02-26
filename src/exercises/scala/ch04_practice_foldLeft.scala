// 1. sum of integers
List(5, 1, 2, 4, 100).foldLeft(0)((total, num) => total + num)

// 2. total length of words
List("scala", "rust", "ada").foldLeft(0)((total, word) => total +  word.length)

// 3. number of s occurences in a given list
def sCount(word: String): Int = word.length - word.replace("s", "").length
List("scala", "haskell", "rust", "ada").foldLeft(0)((total, word) => total + sCount(word))

// 4. maximum integer
List(5, 1, 2, 4, 15).foldLeft(Int.MinValue)((max, num) => if (num > max) num else max)
