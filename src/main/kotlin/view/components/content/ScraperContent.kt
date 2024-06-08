package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dto.charging_station.ChargingStationDTO
import dto.charging_station.ConnectionDTO
import dto.charging_station.ConnectionTypeDTO
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

                Button(onClick = { println(stations) }, enabled = hasLoaded.value) {
                    Text("Save")
                }
            }

            if (isLoading.value) { // Add this block
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
                hasLoaded.value = false
            } else {
                LazyColumn {
                    itemsIndexed(stations.value) { index, station ->
                        StationCard(station) { updatedStation ->
                            stations.value = stations.value.toMutableList().also {
                                it[index] = updatedStation
                            }
                        }
                    }
                    println(stations)
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
fun StationCard(station: ChargingStationDTO, onStationChange: (ChargingStationDTO) -> Unit) {
    // These variables are used to hold the current state of the UI and update it when necessary.
    var numberOfPoints by remember { mutableStateOf(station.numberOfPoints) }
    var usageCost by remember { mutableStateOf(station.usageCost) }
    var addressTitle by remember { mutableStateOf(station.address.title.toString()) }
    var addressTown by remember { mutableStateOf(station.address.town.toString()) }
    var addressPostcode by remember { mutableStateOf(station.address.postcode.toString()) }
    var addressCountry by remember { mutableStateOf(station.address.country.toString()) }
    var addressLat by remember { mutableStateOf(station.address.latitude.toString()) }
    var addressLong by remember { mutableStateOf(station.address.longitude) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // region Station fields
            Text("ID: ${station.id}")
            TextField(
                value = numberOfPoints.toString(),
                onValueChange = {
                    numberOfPoints = it.toInt()
                    onStationChange(
                        station.copy(
                            numberOfPoints = it.toInt()
                        )
                    )
                },
                label = { Text("Number of points") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = usageCost.toString(),
                onValueChange = {
                    usageCost = it
                    onStationChange(
                        station.copy(
                            usageCost = it
                        )
                    )
                },
                label = { Text("Usage cost") },
                modifier = Modifier.fillMaxWidth()
            )
            Text("Date created: ${station.dateCreated}")
            // endregion
            Box(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .border(1.dp, Color.Black)
                    .padding(8.dp)
            ) {

                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text("Address:")
                    TextField(
                        value = addressTitle,
                        onValueChange = {
                            addressTitle = it
                            onStationChange(
                                station.copy(
                                    address = station.address.copy(
                                        title = it
                                    )
                                )
                            )
                        },
                        label = { Text("Station address") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = addressTown,
                        onValueChange = {
                            addressTown = it
                            onStationChange(
                                station.copy(
                                    address = station.address.copy(
                                        town = it
                                    )
                                )
                            )
                        },
                        label = { Text("Station town") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = addressPostcode,
                        onValueChange = {
                            addressPostcode = it
                            onStationChange(
                                station.copy(
                                    address = station.address.copy(
                                        postcode = it
                                    )
                                )
                            )
                        },
                        label = { Text("Station postcode") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = addressCountry,
                        onValueChange = {
                            addressCountry = it
                            onStationChange(
                                station.copy(
                                    address = station.address.copy(
                                        country = it
                                    )
                                )
                            )
                        },
                        label = { Text("Station country") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = addressLat,
                        onValueChange = {
                            addressLat = it
                            onStationChange(
                                station.copy(
                                    address = station.address.copy(
                                        latitude = it
                                    )
                                )
                            )
                        },
                        label = { Text("Station latitude") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = addressLong,
                        onValueChange = {
                            addressLong = it
                            onStationChange(
                                station.copy(
                                    address = station.address.copy(
                                        longitude = it
                                    )
                                )
                            )
                        },
                        label = { Text("Station longitude") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Text("Connections:")
            LazyColumn(modifier = Modifier.height(500.dp).border(1.dp, Color.Black)) {
                items(station.connections) { connection ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .padding(8.dp)
                            .border(1.dp, Color.Black)
                    ) {
                        Column {
                            Text("Connection ID: ${connection.id}")
                            ConnectionCard(connection)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConnectionCard(connection: ConnectionDTO) {
    var id by remember { mutableStateOf(connection.id) }
    var reference by remember { mutableStateOf(connection.reference) }
    var amps by remember { mutableStateOf(connection.amps) }
    var voltage by remember { mutableStateOf(connection.voltage) }
    var powerKW by remember { mutableStateOf(connection.powerKW) }
    var currentType by remember { mutableStateOf(connection.currentType) }
    var quantity by remember { mutableStateOf(connection.quantity) }
    var comments by remember { mutableStateOf(connection.comments) }
    var connectionType by remember { mutableStateOf(connection.connectionType) }

    Column {
        TextField(
            value = id.toString(),
            onValueChange = { id = it.toInt() },
            label = { Text("Connection ID") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = reference.toString(),
            onValueChange = { reference = it },
            label = { Text("Reference (website)") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = amps.toString(),
            onValueChange = { amps = it.toInt() },
            label = { Text("Amps") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = voltage.toString(),
            onValueChange = { voltage = it.toInt() },
            label = { Text("Voltage") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = powerKW.toString(),
            onValueChange = { powerKW = it.toInt() },
            label = { Text("Power KW") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = currentType.toString(),
            onValueChange = { currentType = it.toInt() },
            label = { Text("Current Type") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = quantity.toString(),
            onValueChange = { quantity = it.toInt() },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = comments,
            onValueChange = { comments = it },
            label = { Text("Comments") },
            modifier = Modifier.fillMaxWidth()
        )
        ConnectionTypeCard(connectionType)
    }
}

@Composable
fun ConnectionTypeCard(connectionType: ConnectionTypeDTO) {
    var name by remember { mutableStateOf(connectionType.name) }
    var discontinued by remember { mutableStateOf(connectionType.discontinued.toString()) }
    var obsolete by remember { mutableStateOf(connectionType.obsolete.toString()) }
    var title by remember { mutableStateOf(connectionType.title) }

    Box(modifier = Modifier.fillMaxWidth().padding(1.dp).border(1.dp, Color.Black)) {
        Column {
            Row {
                TextField(
                    value = connectionType.name,
                    onValueChange = { connectionType.name = it },
                    label = { Text("Connection Type") },
                    modifier = Modifier.weight(1f)
                )
                TextField(
                    value = connectionType.discontinued.toString(),
                    onValueChange = { connectionType.discontinued = it.toBoolean() },
                    label = { Text("Discontinued") },
                    modifier = Modifier.weight(1f)
                )
            }
            Row {
                TextField(
                    value = connectionType.obsolete.toString(),
                    onValueChange = { connectionType.obsolete = it.toBoolean() },
                    label = { Text("Obsolete") },
                    modifier = Modifier.weight(1f)
                )
                TextField(
                    value = connectionType.title,
                    onValueChange = { connectionType.title = it },
                    label = { Text("Title") },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}