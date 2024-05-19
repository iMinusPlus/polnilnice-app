package view.state.pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import view.components.SideBarMenu
import view.components.content.Content

@Composable
@Preview
fun MainPage() {
    Row(modifier = Modifier.fillMaxSize()) {
        SideBarMenu()
        Content()
    }
}