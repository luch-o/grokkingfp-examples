case class Book(title: String, authors: List[String])

def recommendedBooks(friend: String): List[Book] = {
    val scala = List(
        Book("FP in Scala", List("Chiusano", "Bjarnason")),
        Book("Get programming with Scala", List("Sfregola"))
    )

    val fiction = List(
        Book("Harry Potter", List("Rowling")),
        Book("The Lord of the Rings", List("Tolkien"))
    )

    if (friend == "Alice") scala
    else if (friend == "Bob") fiction
    else List.empty
}

val friends = List("Alice", "Bob", "Charlie")
val recommendations = friends.flatMap(recommendedBooks)
val authors = recommendations.flatMap(_.authors)
