package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import view.state.MenuState

@Composable
@Preview
fun Content(menuState: MutableState<MenuState>) {
    Column {
        when (menuState.value) {
            MenuState.CHARGING_STATIONS -> DefaultContent()
            else -> DefaultContent()
        }
    }
}