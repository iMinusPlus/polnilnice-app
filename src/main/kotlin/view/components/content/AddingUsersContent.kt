package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dto.user.UserDTO
import kotlinx.coroutines.launch
import util.UserUtil
import view.components.CustomTextField

@Composable
@Preview
fun AddingUsersContent() {
    var username = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }

    var showDialog = remember { mutableStateOf(false) }
    var isSuccess = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    suspend fun postUser() {
        isSuccess.value = UserUtil.postUser(
            UserDTO(
                id = null,
                username = username.value,
                password = password.value,
                email = email.value
            )
        )
        showDialog.value = true
    }

    //TODO
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.Center)
        ) {
            Text("ADD USER")
            CustomTextField(username, "Enter username")
            Text("ADD PASSWORD")
            CustomTextField(password, "Enter password")
            Text("ADD EMAIL")
            CustomTextField(email, "Enter email")
            Button(onClick = {
                coroutineScope.launch {
                    postUser()
                }
            }) {
                Text("Submit")
            }
        }
    }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(text = "Result")
            },
            text = {
                if (isSuccess.value) {
                    Text("Success!")
                } else {
                    Text("Failed!")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}

