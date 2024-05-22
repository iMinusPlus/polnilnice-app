package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import util.JsonParser

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
                scrape("https://api.openchargemap.io/v3/poi/?output=json&countrycode=SI&latitude=46.5547&longitude=15.6459&maxresults=100&distance=25&key=50062ab3-b707-4dea-9da7-c8611695a9ff")
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

fun scrape(url: String) {
    GlobalScope.launch {
        val response: String = client.get(url)
        val jsonElement = JsonParser.parse(response)
        println(jsonElement)
    }
}

