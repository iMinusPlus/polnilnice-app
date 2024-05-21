package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import view.state.MenuState

@Composable
@Preview
fun Content(menuState: MutableState<MenuState>) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(end = 12.dp, top = 12.dp, bottom = 12.dp)
            .border(width = 1.dp, color = Color(0xFFd1cdcd), shape = RoundedCornerShape(5.dp))
    ) {
        when (menuState.value) {
            MenuState.CHARGING_STATIONS -> DefaultContent()
            else -> DefaultContent()
        }
    }
}