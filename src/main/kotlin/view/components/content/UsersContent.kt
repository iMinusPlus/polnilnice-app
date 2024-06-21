package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dto.user.UserDTO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import util.UserUtil

@Composable
@Preview
fun UsersContent() {
    val users = remember { mutableStateListOf<UserDTO>() }

    LaunchedEffect(Unit) {
        runBlocking {
            val fetchedUsers = UserUtil.getAllUsers()
            users.clear()
            users.addAll(fetchedUsers)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stations") },
                actions = {
                    IconButton(onClick = {
                        // Clear old data
                        users.clear()

                        GlobalScope.launch {
                            val fetchedUsers = UserUtil.getAllUsers()
                            users.clear()
                            users.addAll(fetchedUsers)
                        }
                    }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(200.dp),
                    content = {
                        items(users.size) { i ->
                            UserCard(users[i])
                        }
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun UserCard(user: UserDTO) {
    var isEnabeled by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .height(300.dp)
            .width(300.dp)
            .padding(10.dp)
            .border(width = 1.dp, color = Color(0xFFd1cdcd), shape = RoundedCornerShape(5.dp))
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.padding(20.dp).size(50.dp),
                tint = Color(0xFF5c6cfa)
            )
            Text(
                text = user.username,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )
            Text(text = "${user.username}, ${user.email}")

            Button(onClick = {
                isEnabeled = false
                GlobalScope.launch {
                    UserUtil.postRemoveUser(user)
                }
            }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red), enabled = isEnabeled) {
                Text("Delete")
            }
        }
    }
}
