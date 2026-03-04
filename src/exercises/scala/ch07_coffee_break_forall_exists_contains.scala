case class User(name: String, city: Option[String], 
                favoriteArtists: List[String])

	
val users = List(
  User("Alice", Some("Melbourne"), List("Bee Gees")),
  User("Bob", Some("Lagos"), List("Bee Gees")),
  User("Eve", Some("Tokyo"), List.empty),
  User("Mallory", None, List("Metallica", "Bee Gees")),
  User("Trent", Some("Buenos Aires"), List("Led Zeppelin"))
)

// users that haven´t specified theur city or live in melbourne
def f1(users: List[User]): List[User] = {
    users.filter(user => user.city.forall(_ == "Melbourne"))
}

// users that live in Lagos
def f2(users: List[User]): List[User] = {
    users.filter(user => user.city.contains(_ == "Lagos"))
}

// users that like Bee Gees
def f3(users: List[User]): List[User] = {
    users.filter(user => user.favoriteArtists.contains("Bee Gees"))
}

// users that live in cities that start with the letter T
def f4(users: List[User]): List[User] = {
    users.filter(user => user.city.exists(_.substring(0,1) == "T"))
}

// users that only like artists that have a name longer than eight characters or no favorite artists at all
def f5(users: List[User]): List[User] = {
    users.filter(user => user.favoriteArtists.forall(_.length > 8))
}

// users that like some artists whose name start with an M
def f6(users: List[User]): List[User] = {
    users.filter(user => user.favoriteArtists.exists(_.substring(0,1) == "M"))
}