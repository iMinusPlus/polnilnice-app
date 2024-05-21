package view.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import view.state.MenuState

@Composable
@Preview
fun SideBarMenu(menuState: MutableState<MenuState>) {
    val currentState by remember { mutableStateOf(menuState) }
    fun handleStateChange(newState: MenuState) {
        currentState.value = newState
    }

    Column(
        modifier = Modifier
            .width(200.dp)
            .fillMaxHeight()
            .padding(12.dp)
            .border(width = 1.dp, color = Color(0xFFb3b3b3))
    ) {
        SideBarItem(
            text = "Add charging station",
            stateToChangeTo = MenuState.ADDING,
            icon = Icons.Default.Add,
            onStateChange = ::handleStateChange
        )
        SideBarItem(
            text = "Add user",
            stateToChangeTo = MenuState.ADDING,
            icon = Icons.Default.Add,
            onStateChange = ::handleStateChange
        )
        SideBarItem(
            text = "Charging stations",
            stateToChangeTo = MenuState.CHARGING_STATIONS,
            iconPath = "icons/EvCharger.svg",
            onStateChange = ::handleStateChange
        )
        SideBarItem(
            text = "Users",
            stateToChangeTo = MenuState.ADDING,
            icon = Icons.Default.Person,
            onStateChange = ::handleStateChange
        )
        SideBarItem(
            text = "Scraper",
            stateToChangeTo = MenuState.ADDING,
            icon = Icons.Default.Search,
            onStateChange = ::handleStateChange
        )
        SideBarItem(
            text = "Generator",
            stateToChangeTo = MenuState.ADDING,
            icon = Icons.Default.Build,
            onStateChange = ::handleStateChange
        )
        Spacer(modifier = Modifier.height(30.dp))
        SideBarItem(
            text = "About",
            stateToChangeTo = MenuState.ADDING,
            icon = Icons.Default.Info,
            onStateChange = ::handleStateChange
        )
    }
}

@Composable
fun SideBarItem(
    text: String,
    stateToChangeTo: MenuState,
    icon: ImageVector? = null,
    iconPath: String? = null,
    onStateChange: (MenuState) -> Unit
) {
    if (iconPath == null && icon == null) {
        throw IllegalArgumentException("iconPath and icon cannot be both null")
    }

    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = { onStateChange(stateToChangeTo) })
    ) {
        if (iconPath != null) {
            Icon(painter = painterResource(iconPath), contentDescription = null, modifier = Modifier.padding(10.dp))
            Text(text, modifier = Modifier.padding(10.dp))
        } else {
            Icon(imageVector = icon!!, contentDescription = null, modifier = Modifier.padding(10.dp))
            Text(text, modifier = Modifier.padding(8.dp))
        }
    }
}