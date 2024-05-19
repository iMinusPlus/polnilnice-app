package view.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun SideBarMenu() {
    //todo polepsati in spremeniti
    Column(
        modifier = Modifier
            .width(200.dp)
            .fillMaxHeight()
            .background(Color.Gray)
            .padding(16.dp)
    ) {
        Text("Add charging station", color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Add user", color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Charging stations", color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Users", color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Scraper", color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Generator", color = Color.White)
        Spacer(modifier = Modifier.height(20.dp))
        Text("About", color = Color.White)
    }
}