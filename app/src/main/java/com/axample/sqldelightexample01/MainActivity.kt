package com.axample.sqldelightexample01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.axample.sqldelightexample01.screen.AddUserScreen
import com.axample.sqldelightexample01.screen.UserListScreen
import com.axample.sqldelightexample01.ui.theme.SqlDelightExample01Theme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.squareup.sqldelight.android.AndroidSqliteDriver

class MainActivity : ComponentActivity() {


    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current

            val androidSqlDriver = AndroidSqliteDriver(
                schema = Database.Schema,
                context = context,
                name = "user.db"
            )


            SqlDelightExample01Theme {

                BoxWithConstraints {
                    val navController = rememberAnimatedNavController()
                     AnimatedNavHost(
                        navController = navController,
                        startDestination = Screen.UserList.route,
                        builder = {
                            addUserList(
                                navController = navController,
                                width = constraints.maxWidth / 2,
                                androidSqlDriver = androidSqlDriver
                            )
                            addAddUser(
                                width = constraints.maxWidth / 2,
                                androidSqlDriver = androidSqlDriver
                            )
                        }
                    )
                }

            }
        }
    }
}


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addUserList(
    navController: NavController,
    width: Int,
    androidSqlDriver: AndroidSqliteDriver,
) {
    composable(
        route = Screen.UserList.route,
        exitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = { initial, _ ->
            slideInHorizontally(
                initialOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
    ) {

        UserListScreen(
            navigateToAddScreen = { userId ->
                navController.navigate("${Screen.AddUser.route}/$userId")
            },
            androidSqlDriver = androidSqlDriver
        )
    }
}


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addAddUser(
    width: Int,
    androidSqlDriver: AndroidSqliteDriver,
) {
    composable(
        route = Screen.AddUser.route + "/{userId}",
        arguments = Screen.AddUser.arguments,
        enterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = { _, target ->
            slideOutHorizontally(
                targetOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        val userId = it.arguments?.getInt("userId")
        AddUserScreen(androidSqlDriver = androidSqlDriver,userIdFromNav= userId?:-1 )
    }
}

 