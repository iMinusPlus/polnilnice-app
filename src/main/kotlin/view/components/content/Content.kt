package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import view.state.MenuState

@Composable
@Preview
fun Content() {
    val menuState = remember { mutableStateOf(MenuState.CHARGING_STATIONS) }
    Column {
        when (menuState.value) {
            MenuState.CHARGING_STATIONS -> DefaultContent()
            else -> DefaultContent()
        }
    }
}