package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import dto.charging_station.enums.StationStatus
import kotlin.math.exp

@Composable
@Preview
fun AddingStationContent() {
    var station = remember { mutableStateOf("") }
    var address = remember { mutableStateOf("") }

    val statusOptions = listOf(StationStatus.FREE, StationStatus.IN_USAGE, StationStatus.IN_REPAIR)
//    var status = remember { mutableStateOf("") }
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
            Box {
                Button(onClick = { expanded = !expanded }) {
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

@Composable
@Preview
fun CustomTextField(state: MutableState<String>, placeholder: String) {
    TextField(
        value = state.value,
        onValueChange = { state.value = it },
        shape = RoundedCornerShape(12.dp),
        placeholder = { Text(placeholder) })
}
