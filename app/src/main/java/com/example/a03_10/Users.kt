package com.example.a03_10


class Users(
    val name: String,
    val phoneNumber: String? = null,
    val email: String? = null,  //либо строка, либо null
    val password: String
) {
    init {
        //проверяем, что не оба поля phoneNumber, email = null
        if (phoneNumber == null && email == null) {
            throw IllegalArgumentException("Необходимо заполнить номер телефона или почту")
        }
    }
}

fun getUsers(): List<Users>{
    return listOf(
        Users("user1", "89996661234", "user1@gmail.com", "pas1"),
        Users(name = "user2", phoneNumber = "1234", password = "pas2"),
        Users(name = "user3", email = "user3@yandex.ru", password = "pas3")
    )
}

