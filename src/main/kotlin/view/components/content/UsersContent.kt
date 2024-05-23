package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dto.user.UserDTO

@Composable
@Preview
fun UsersContent() {
    var users = remember { mutableStateListOf<UserDTO>() }

    LaunchedEffect(Unit) {
        val user1 = UserDTO(
            id = 1,
            name = "Gandalf",
            username = "Saruman's pet",
            password = "test",
            email = "forgondor@gmail.com"
        )
        val user2 = UserDTO(
            id = 1,
            name = "Golum",
            username = "Ring's pet",
            password = "test",
            email = "precisios1231@gmail.com"
        )

        users.clear()
        users.addAll(listOf(user1, user2, user2, user2, user1, user2))
    }

    //TODO
    val groupedCards = users.chunked(2)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            LazyColumn {
                items(groupedCards) { rowItems ->
                    Row {
                        rowItems.forEach { item ->
                            UserCard(item)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun UserCard(user: UserDTO) {
    //todo
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
            Text(text = "${user.name}, ${user.email}")
        }
    }
}
