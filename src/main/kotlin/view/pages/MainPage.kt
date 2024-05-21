package view.pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import view.components.SideBarMenu
import view.components.content.Content
import view.state.MenuState

@Composable
@Preview
fun MainPage(menuState: MutableState<MenuState>) {
    Row(modifier = Modifier.fillMaxSize()) {
        SideBarMenu(menuState)
        Content(menuState)
    }
}