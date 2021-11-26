package com.axample.sqldelightexample01.screen

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.axample.sqldelightexample01.*
import com.axample.sqldelightexample01.LAST_NAME_TEXT_FIELD_TAG
import com.axample.sqldelightexample01.domain.User
import com.axample.sqldelightexample01.domain.toUser
import com.example.sqldelightexample01.UserDbQueries
import com.squareup.sqldelight.android.AndroidSqliteDriver

@ExperimentalComposeUiApi
@Composable
fun AddUserScreen(androidSqlDriver: AndroidSqliteDriver, userIdFromNav: Int) {

    val queries = Database(androidSqlDriver).userDbQueries
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current


    val scaffoldState = rememberScaffoldState()
    var id by remember { mutableStateOf(-1) }
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }


    if (userIdFromNav > -1) {
       val user= queries.getUser(userIdFromNav.toLong()).executeAsOne().toUser()
        id= user.id.toInt()
        firstname=user.userFirstName
        lastname=user.userLastName
        age=user.userAge
        email=user.userEmail
    }


    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.SpaceBetween

        ) {

            Column {

                TextField(
                    modifier = Modifier
                        .padding(8.dp)
                        .testTag(FIRST_NAME_TEXT_FIELD_TAG)
                        .fillMaxWidth(),
                    value = firstname,
                    onValueChange = { firstname = it },
                    label = { Text(text = "First Name:") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        },
                    ),
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Person Icon") },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
                )



                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .testTag(LAST_NAME_TEXT_FIELD_TAG),
                    value = lastname,
                    onValueChange = { lastname = it },
                    label = { Text(text = "Last Name:") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        },
                    ),
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Person Icon") },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .testTag(EMAIL_NAME_TEXT_FIELD_TAG),
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Email:") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        },
                    ),
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon") },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
                )



                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .testTag(AGE_NAME_TEXT_FIELD_TAG),
                    value = age,
                    onValueChange = { age = it },
                    label = { Text(text = "Age:") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        },
                    ),
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Person Icon") },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
                )


            }


            Row(modifier = Modifier.fillMaxWidth()) {

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(.7f)
                        .height(65.dp)
                        .padding(bottom = 8.dp, start = 8.dp, end = 4.dp),
                    onClick = {
                        if (firstname.isNotEmpty() && lastname.isNotEmpty() &&
                            email.isNotEmpty() && age.isNotEmpty()
                        ) {
                            queries.insertUser(
                                id =  id.toLong(),
                                userFirstName = firstname,
                                userLastName = lastname,
                                userGendar = "Male",
                                userEmail = email,
                                userAge = age.toLong()
                            )

                            context.findActivity()?.onBackPressed()

                        } else {
                            Toast.makeText(
                                context,
                                "Please Enter All Fields...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    border = BorderStroke(1.dp, Color.Red),
                    shape = RoundedCornerShape(16), // = 50% percent
                    //or shape = CircleShape
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                ) {
                    Text(text = "SAVE")
                }

                if (userIdFromNav != -1) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(65.dp)
                            .padding(bottom = 8.dp, start = 4.dp, end = 8.dp),
                        onClick = {
                            queries.removeUser( id.toLong())
                            context.findActivity()?.onBackPressed()
                        },
                        // border = BorderStroke(1.dp, Color.Red),
                        elevation = null,
                        shape = RoundedCornerShape(16), // = 50% percent
                        //or shape = CircleShape
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            backgroundColor = Color.Red
                        )
                    ) {
                        Text(text = "DELETE")
                    }

                }


            }


        }
    }
}

@Composable
fun InsertUser(
    queries: UserDbQueries,
    firstname: String,
    lastname: String,
    email: String,
    age: String,
    context: Context
) {
    if (firstname.isNotEmpty() && lastname.isNotEmpty() && email.isNotEmpty() && age.isNotEmpty()) {
        queries.insertUser(
            0,
            firstname,
            lastname,
            "Male",
            email,
            age.toLong()
        )
    } else {
        Toast.makeText(
            context,
            "Please Enter All Fields...",
            Toast.LENGTH_SHORT
        ).show()
    }
}


fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}