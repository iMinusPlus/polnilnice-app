package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import dto.charging_station.AddressDTO
import dto.charging_station.ConnectionDTO
import dto.charging_station.ConnectionTypeDTO
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import util.CustomJsonParser

@Composable
@Preview
fun ScraperContent() {
    //TODO

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
            Text("Chose source to scrape data from:")
            DropDownMenu()

            Button(onClick = {
                scrapeFromOpenChargeAPI("https://api.openchargemap.io/v3/poi/?output=json&countrycode=SI&latitude=46.5547&longitude=15.6459&maxresults=100&distance=25&key=50062ab3-b707-4dea-9da7-c8611695a9ff")
            }) {
                Text("Scrape data from OpenChargeMap API")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenu() {

    val urla: Map<String, String> = mapOf(
        "OpenChargeMap" to "https://api.openchargemap.io/v3/poi/?output=json&countrycode=SI&latitude=46.5547&longitude=15.6459&maxresults=100&distance=25&key=50062ab3-b707-4dea-9da7-c8611695a9ff",
        "Other" to "http://example.com",
        "Other 2" to "http://example2.com",
    )

    var selected by remember { mutableStateOf(urla.entries.first()) }
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                urla.forEach { url ->
                    DropdownMenuItem(
                        onClick = {
                            selected = url
                            isExpanded = false
                        }
                    ) {
                        Text(url.key)
                    }
                }
            }
        }
    }
}


val client = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}

fun scrapeFromOpenChargeAPI(url: String) {
    GlobalScope.launch {
        val response: String = client.get(url)
        val jsonElement = CustomJsonParser.parse(response)
        val jsonObject = CustomJsonParser.convertJsonArrayToJsonObject(jsonElement.asJsonArray)


        val list = mutableListOf<JsonObject>()
        jsonObject.entrySet().forEach {
            list.add(it.value.asJsonObject)
        }

        val addressList = mutableListOf<AddressDTO>()
        list.forEach {
            val address = it.get("AddressInfo").asJsonObject
//            println(address)
            addressList.add(
                AddressDTO(
                    id = address.get("ID")?.asInt ?: 0,
                    title = address?.get("Title")?.asString ?: "null",
                    town = if (address.get("Town") == null || address.get("Town") is JsonNull) "null" else address.get("Town").asString,
                    postcode = address.get("Postcode")?.asString ?: "null",
                    country = address.get("Country")?.asJsonObject?.get("Title")?.asString ?: "null",
                    latitude = address.get("Latitude")?.asString ?: "null",
                    longitude = address.get("Longitude")?.asString ?: "null"
                )
            )
        }

//        addressList.forEach {
//            println(it)
//        }

        val connectionTypesList = mutableListOf<ConnectionTypeDTO>()
        val connectionDTOList = mutableListOf<ConnectionDTO>()
        list.forEach {
            val connections = it.get("Connections").asJsonArray
            connections.forEach { connection ->
                val connectionType = connection.asJsonObject.get("ConnectionType").asJsonObject
                connectionTypesList.add(
                    ConnectionTypeDTO(
                        id = connectionType.get("ID")?.asInt ?: 0,
                        name = if (connectionType.get("FormalName") == null || connectionType.get("FormalName") is JsonNull) "null" else connectionType.get(
                            "FormalName"
                        ).asString,
                        discontinued = if (connectionType.get("IsDiscontinued") == null || connectionType.get("IsDiscontinued") is JsonNull) false else connectionType.get(
                            "IsDiscontinued"
                        ).asBoolean,
                        obsolete = if (connectionType.get("IsObsolete") == null || connectionType.get("IsObsolete") is JsonNull) false else connectionType.get(
                            "IsObsolete"
                        ).asBoolean,
                        title = if (connectionType.get("Title") == null || connectionType.get("Title") is JsonNull) "null" else connectionType.get(
                            "Title"
                        ).asString
                    )
                )

//                println(connection)
                connectionDTOList.add(
                    ConnectionDTO(
//                        id = connection.asJsonObject.get("ID")?.asInt ?: 0,
                        id = if (connection.asJsonObject.get("ID") == null || connection.asJsonObject.get("ID") is JsonNull) 0 else connection.asJsonObject.get("ID").asInt,
                        connectionType = if (connection.asJsonObject.get("ConnectionType") == null || connection.asJsonObject.get("ConnectionType") is JsonNull) 0 else connection.asJsonObject.get("ConnectionType").asJsonObject.get("ID").asInt,
                        reference = if (connection.asJsonObject.get("Reference") == null || connection.asJsonObject.get("Reference") is JsonNull) "null" else connection.asJsonObject.get("Reference").asString,
                        amps = if (connection.asJsonObject.get("Amps") == null || connection.asJsonObject.get("Amps") is JsonNull) 0 else connection.asJsonObject.get("Amps").asInt,
                        voltage = if (connection.asJsonObject.get("Voltage") == null || connection.asJsonObject.get("Voltage") is JsonNull) 0 else connection.asJsonObject.get("Voltage").asInt,
                        powerKW = if (connection.asJsonObject.get("PowerKW") == null || connection.asJsonObject.get("PowerKW") is JsonNull) 0 else connection.asJsonObject.get("PowerKW").asInt,
                        currentType = if (connection.asJsonObject.get("CurrentTypeID") == null || connection.asJsonObject.get("CurrentTypeID") is JsonNull) 0 else connection.asJsonObject.get("CurrentTypeID").asInt,
                        quantity = if (connection.asJsonObject.get("Quantity") == null || connection.asJsonObject.get("Quantity") is JsonNull) 0 else connection.asJsonObject.get("Quantity").asInt,
                        comments = if (connection.asJsonObject.get("Comments") == null || connection.asJsonObject.get("Comments") is JsonNull) "null" else connection.asJsonObject.get("Comments").asString
                    )
                )
            }
        }

//        connectionTypesList.forEach {
//            println(it)
//        }

        connectionDTOList.forEach {
            println(it)
        }

    }
}

