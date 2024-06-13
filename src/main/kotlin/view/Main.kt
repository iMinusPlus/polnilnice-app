package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import view.pages.MainPage
import view.state.MenuState

@Composable
@Preview
fun App() {
    val menuState = remember { mutableStateOf(MenuState.ADDING_STATION) }
    MaterialTheme {
        MainPage(menuState)
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "BRUM BRUM") {
        App()
    }
}
