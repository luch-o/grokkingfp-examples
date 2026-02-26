def abbreviate(name: String): String = {
    val initial = name.substring(0, 1)
    val separator = name.indexOf(" ")
    val lastName = name.substring(separator, name.length)
    initial.concat(".").concat(lastName)
}

abbreviate("Alfonzo Church")
abbreviate("A. Church")
abbreviate("A Church")