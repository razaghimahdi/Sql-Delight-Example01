package com.axample.sqldelightexample01.domain

import com.example.sqldelightexample01.User_entity

data class User(
    val id:Long,
    var userFirstName: String,
    var userLastName: String,
    val userGendar: String,
    var userEmail: String,
    var userAge: String
)

fun User.toEntity():User_entity{
    return User_entity(
        id = id,
        userFirstName=userFirstName,
        userLastName=userLastName,
        userGendar=userGendar,
        userEmail=userEmail,
        userAge=userAge.toLong(),
    )
}

fun User_entity.toUser():User{
    return User(
        id = id,
        userFirstName=userFirstName,
        userLastName=userLastName,
        userGendar=userGendar,
        userAge=userAge.toString(),
        userEmail=userEmail,
    )
}
