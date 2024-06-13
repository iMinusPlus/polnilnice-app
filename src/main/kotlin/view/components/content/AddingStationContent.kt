package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dto.charging_station.AddressDTO
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import util.BackendUtil
import view.components.CustomTextField
import java.util.*
import kotlin.concurrent.schedule

@OptIn(DelicateCoroutinesApi::class)
@Composable
@Preview
fun AddingStationContent() {
    // Add this line to create a mutable state for the button's enabled state
    var isButtonEnabled by remember { mutableStateOf(true) }
    val generated = remember { mutableStateOf(false) }
    var id = remember { mutableStateOf("") }
    var country = remember { mutableStateOf("") }
    var town = remember { mutableStateOf("") }
    var postcode = remember { mutableStateOf("") }
    var title = remember { mutableStateOf("") }
    var latitude = remember { mutableStateOf("") }
    var longitude = remember { mutableStateOf("") }


//    var station = remember { mutableStateOf("") }
//    var address = remember { mutableStateOf("") }
//    val statusOptions = listOf(StationStatus.FREE, StationStatus.IN_USAGE, StationStatus.IN_REPAIR)
//    var status by remember { mutableStateOf("") }
//    var expanded by remember { mutableStateOf(false) }

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
            Text("ADD ADDRESS")
            CustomTextField(country, "Country")
            CustomTextField(town, "Town")
            CustomTextField(postcode, "Postcode")
            CustomTextField(title, "Address title")
            CustomTextField(latitude, "Longitude")
            CustomTextField(longitude, "Latitude")
            CustomTextField(id, "ID")

            Button(onClick = {
                if (id.value.isNotBlank() && country.value.isNotBlank() && town.value.isNotBlank() &&
                    postcode.value.isNotBlank() && title.value.isNotBlank() &&
                    latitude.value.isNotBlank() && longitude.value.isNotBlank()
                ) {

                    isButtonEnabled = false
                    GlobalScope.launch {
//                    println("Button clicked: " + address.value)
                        var parsedId = 0
                        try {
                            parsedId = id.value.toInt()
                        } catch (e: NumberFormatException) {
                            // Handle the case where id is not parsable to an integer
                            parsedId = 0
                        }
                        val newAddress = AddressDTO(
                            id = parsedId,
                            country = country.value,
                            town = town.value,
                            postcode = postcode.value,
                            title = title.value,
                            latitude = latitude.value,
                            longitude = longitude.value
                        )
                        BackendUtil.postAddress(newAddress)
                        isButtonEnabled = true
                        generated.value = true
                    }
                }
            }) {
                Text("Add address")
            }

            if (generated.value) {
                Text("Address added!", fontSize = 12.sp, modifier = Modifier.padding(16.dp))
                // after 2 seconds, hide the message
                Timer().schedule(2000) {
                    generated.value = false
                }
            }
        }
    }
}
