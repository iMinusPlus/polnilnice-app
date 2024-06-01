package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dto.charging_station.ChargingStationDTO
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import util.Scraper.scrapeFromDDD
import util.Scraper.scrapeFromOpenChargeAPI

@Composable
@Preview
fun ScraperContent() {
    //TODO

    val stations = remember { mutableStateOf(listOf<ChargingStationDTO>()) }
    val isLoading = remember { mutableStateOf(false) }
    val hasLoaded = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ) { // Wrap the dropdown and button in a Row
                DropDownMenu(stations, isLoading)

                Spacer(modifier = Modifier.width(16.dp)) // Add some space between the dropdown and the button

                Button(onClick = { /* Handle button click */ }, enabled = hasLoaded.value) {
                    Text("Save")
                }
            }

            if (isLoading.value) { // Add this block
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
                hasLoaded.value = false
            } else {
                LazyColumn {
                    items(stations.value) { station ->
                        StationCard(station)
                    }
                }
                hasLoaded.value = true
            }
        }
    }
}

sealed class FunctionResult {
    data class ResultA(val data: List<ChargingStationDTO>) : FunctionResult()
    data class ResultB(val data: String) : FunctionResult()
}

@OptIn(ExperimentalMaterialApi::class, DelicateCoroutinesApi::class)
@Composable
fun DropDownMenu(stations: MutableState<List<ChargingStationDTO>>, isLoading: MutableState<Boolean>) {

    val functions: Map<String, suspend () -> FunctionResult> = mapOf(
        "OpenChargeMap" to { FunctionResult.ResultA(scrapeFromOpenChargeAPI()) },
        "Other" to { FunctionResult.ResultB(scrapeFromDDD()) },
    )

    var selected by remember { mutableStateOf(functions.entries.first()) }
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .width(400.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
        ) {
            TextField(
                value = selected.key,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                }
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                functions.forEach { function ->
                    DropdownMenuItem(
                        onClick = {
                            selected = function
                            isExpanded = false
                            GlobalScope.launch {
                                isLoading.value = true
                                when (val result = function.value.invoke()) {
                                    is FunctionResult.ResultA -> {
                                        // Handle ResultA
                                        stations.value = result.data
                                    }

                                    is FunctionResult.ResultB -> {
                                        // Handle ResultB
                                        println(result.data)
                                    }
                                }
                                isLoading.value = false
                            }
                        }
                    ) {
                        Text(function.key)
                    }
                }
            }
        }
    }
}

@Composable
fun StationCard(station: ChargingStationDTO) {
//    var id by remember { mutableStateOf(station.id.toString()) }
    var UUID by remember { mutableStateOf(station.UUID) }
    var numberOfPoints by remember { mutableStateOf(station.numberOfPoints.toString()) }
    var usageCost by remember { mutableStateOf(station.usageCost) }
    var dateCreated by remember { mutableStateOf(station.dateCreated.toString()) }
    var address by remember { mutableStateOf(station.address.toString()) }
    var connections by remember { mutableStateOf(station.connections.toString()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
//            TextField(
//                value = id,
//                onValueChange = { id = it },
//                label = { Text("Station ID") },
//                modifier = Modifier.fillMaxWidth()
//            )
            Text("ID: ${station.id}")
            TextField(
                value = UUID,
                onValueChange = { UUID = it },
                label = { Text("Station UUID") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = numberOfPoints,
                onValueChange = { numberOfPoints = it },
                label = { Text("Number of points") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = usageCost,
                onValueChange = { usageCost = it },
                label = { Text("Usage cost") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = dateCreated,
                onValueChange = { dateCreated = it },
                label = { Text("Station date created") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Station address") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = connections,
                onValueChange = { connections = it },
                label = { Text("Station address") },
                modifier = Modifier.fillMaxWidth()
            )
            // Add more fields as needed
        }
    }
}