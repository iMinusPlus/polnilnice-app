package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dto.charging_station.enums.StationStatus
import view.components.CustomTextField

@Composable
@Preview
fun AddingStationContent() {
    var station = remember { mutableStateOf("") }
    var address = remember { mutableStateOf("") }

    val statusOptions = listOf(StationStatus.FREE, StationStatus.IN_USAGE, StationStatus.IN_REPAIR)
    var status by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

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
            Text("ADD CHARGING STATION")
            CustomTextField(station, "Enter station")
            Text("ADD ADDRESS")
            CustomTextField(address, "Enter address")

            //Todo obrazec

            //Todo urediti
            Text("SELECT STATUS")
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Button(
                    onClick = { expanded = !expanded },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                ) {
                    Text(if (status.isEmpty()) "Select status" else status)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    statusOptions.forEach { option ->
                        DropdownMenuItem(onClick = {
                            status = option.name
                            expanded = false
                        }) {
                            Text(option.toString())
                        }
                    }
                }
            }
        }
    }
}
