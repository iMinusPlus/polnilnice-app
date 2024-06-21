package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dto.charging_station.AddressDTO
import dto.charging_station.ChargingStationDTO
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import util.BackendUtil

@Composable
@Preview
fun StationsContent() {
    var chargingStations = remember { mutableStateListOf<ChargingStationDTO>() }
    var addresses = remember { mutableStateListOf<AddressDTO>() }

    LaunchedEffect(Unit) {
        BackendUtil.getAddresses().forEach {
            addresses.add(it)
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stations") },
                actions = {
                    IconButton(onClick = {
                        // Clear old data
                        chargingStations.clear()
                        addresses.clear()

                        GlobalScope.launch {
                            BackendUtil.getAddresses().forEach {
                                addresses.add(it)
                            }
                        }
                    }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(260.dp),
                    content = {
                        items(addresses.size) { index ->
                            AddressCard(addresses[index])
                        }
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun ChargingStationCard(chargingStation: ChargingStationDTO) {
    //Todo
    Box(
        modifier = Modifier
            .padding(10.dp)
            .border(width = 1.dp, color = Color(0xFFd1cdcd), shape = RoundedCornerShape(5.dp))
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource("icons/EvCharger.svg"),
                contentDescription = null,
                modifier = Modifier.padding(20.dp).size(50.dp),
                tint = Color(0xFF5c6cfa)
            )
            Text(
                text = chargingStation.address.title,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )
            Text(text = "${chargingStation.address.town}, ${chargingStation.address.country}")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, DelicateCoroutinesApi::class)
@Composable
@Preview
fun AddressCard(address: AddressDTO) {
    var isEnabeled by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .height(300.dp)
            .width(300.dp)
            .padding(10.dp)
            .border(width = 1.dp, color = Color(0xFFd1cdcd), shape = RoundedCornerShape(5.dp))
            .padding(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource("icons/EvCharger.svg"),
                contentDescription = null,
                modifier = Modifier.padding(20.dp).size(50.dp),
                tint = Color(0xFF5c6cfa)
            )
            Text(
                text = address.title,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )
            Text(text = "ID: ${address.id}", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 9.sp))
            Text(
                text = "Latitude: ${address.latitude}",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 13.sp)
            )
            Text(
                text = "Longitude: ${address.longitude}",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 13.sp)
            )
            Text(text = "${address.town} - ${address.country}")

            Button(onClick = {
                isEnabeled = false
                GlobalScope.launch {
                    BackendUtil.postRemoveAddress(address)
                }
            }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red), enabled = isEnabeled) {
                Text("Delete")
            }
        }
    }
}
