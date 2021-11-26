package com.axample.sqldelightexample01.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axample.sqldelightexample01.Database
import com.axample.sqldelightexample01.domain.User
import com.axample.sqldelightexample01.domain.toUser
import com.squareup.sqldelight.android.AndroidSqliteDriver
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.res.painterResource
import com.axample.sqldelightexample01.R

@ExperimentalAnimationApi
@Composable
fun UserListScreen(
    navigateToAddScreen: (Int) -> Unit,
    androidSqlDriver: AndroidSqliteDriver
) {

    val queries = Database(androidSqlDriver).userDbQueries
    val users = queries.selectAll().executeAsList().map { it.toUser() }

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToAddScreen(-1) },
                backgroundColor = Color.Red,
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            CheckEmptyList(users.isEmpty(), navigateToAddScreen = navigateToAddScreen)

            ShowUserList(users = users, navigateToAddScreen = navigateToAddScreen)


        }
    }

}

@ExperimentalAnimationApi
@Composable
fun ShowUserList(users: List<User>, navigateToAddScreen: (Int) -> Unit) {

    AnimatedVisibility(visible = users.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 8.dp)
        ) {
            items(users) { user ->
                UserListItem(
                    user = user,
                    onSelectUser = navigateToAddScreen,
                )
            }
        }
    }
}

@Composable
fun UserListItem(user: User, onSelectUser: (Int) -> Unit) {


    Card(
        modifier = Modifier
            .padding(bottom = 4.dp, top = 4.dp)
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth()
            .clickable {
                onSelectUser(user.id.toInt())
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp
    ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "FirstName:",
                        style = TextStyle(
                            fontWeight = FontWeight.W500,
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    )
                    Text(
                        text = user.userFirstName,
                        style = TextStyle(fontWeight = FontWeight.W200, color = Color(0xFF414141))
                    )
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "LastName:",
                        style = TextStyle(
                            fontWeight = FontWeight.W500,
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    )
                    Text(
                        text = user.userLastName,
                        style = TextStyle(fontWeight = FontWeight.W200, color = Color(0xFF414141))
                    )
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Email:",
                        style = TextStyle(
                            fontWeight = FontWeight.W500,
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    )
                    Text(
                        text = user.userEmail,
                        style = TextStyle(fontWeight = FontWeight.W200, color = Color(0xFF414141))
                    )
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Gender:",
                        style = TextStyle(
                            fontWeight = FontWeight.W500,
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    )
                    Text(
                        text = user.userGendar,
                        style = TextStyle(fontWeight = FontWeight.W200, color = Color(0xFF414141))
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                    Text(
                        text = "Age:",
                        style = TextStyle(
                            fontWeight = FontWeight.W500,
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    )
                    Text(
                        text = "${user.userAge}",
                        style = TextStyle(fontWeight = FontWeight.W200, color = Color(0xFF414141))
                    )
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))

            }


    }

}


@ExperimentalAnimationApi
@Composable
fun CheckEmptyList(isEmpty: Boolean, navigateToAddScreen: (Int) -> Unit) {
    AnimatedVisibility(visible = isEmpty) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No User Available...",
                style = TextStyle(fontWeight = FontWeight.W700, fontSize = 22.sp)
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            OutlinedButton(
                modifier = Modifier
                    .padding(8.dp)
                //  .fillMaxWidth()
                , onClick = { navigateToAddScreen(-1) }
            ) {
                Text(
                    text = "Add First User!",
                    style = TextStyle(fontWeight = FontWeight.W300, color = Color.Blue)
                )
            }
        }
    }
}

