# Sql-Delight-Example01
## Developed by Mahdi Razzaghi Ghaleh
**first example of sql-delight**

 
### What is SqlDelight?
Kotlin Multiplatform is one of the most interesting trends in mobile development this year. 
It's dedicated to sharing the code between many different platforms, including mobile ones — Android and iOS.
SQLDelight generates typesafe kotlin APIs from your SQL statements. It verifies your schema, statements, 
and migrations at compile-time and provides IDE features like autocomplete and refactoring which make writing and maintaining SQL simple.
  

### SqlDelight and Coroutine
One of the main reasons behind the success of the jetpack Room database library is that
it’s easy to use and compatible with popular frameworks like coroutines, and paging.
SQL Delight also has that benefit;
we only need to add the following line under the dependencies node inside the app level Gradle file to make it work.

**look at these examples:**

```kotlin


/**
 * we need to use AndroidSqliteDriver to write the data into the android database which persists across app launches.
 * */
val context = LocalContext.current
val androidSqlDriver = AndroidSqliteDriver(
    schema = Database.Schema,
    context = context,
    name = "user.db"
)

/**Then we need to get hold of the queries that we’ve created inside database.sql file.*/
val queries = Database(androidSqlDriver).userDbQueries
val users = queries.selectAll().executeAsList().map { it.toUser() }

```

  

#### Some Ideas:
**https://cashapp.github.io/sqldelight/**
**https://github.com/cashapp/sqldelight**

**https://betterprogramming.pub/getting-started-with-sqldelight-in-android-development-eecd0ae9bbdd**
**https://medium.com/xorum-io/migration-from-room-to-sqldelight-28d6f4aaf31e**
