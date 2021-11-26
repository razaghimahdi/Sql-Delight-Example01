package com.axample.sqldelightexample01

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class Screen(val route: String, val arguments: List<NamedNavArgument>){

    object UserList: Screen(
        route = "userList",
        arguments = emptyList() /*listOf(navArgument("id") {
            type = NavType.IntType
        })*/
    )

    object AddUser: Screen(
        route = "addUser",
        arguments = listOf(navArgument("userId") {
            type = NavType.IntType
        })
    )
}