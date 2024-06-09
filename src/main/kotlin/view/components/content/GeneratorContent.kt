package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dto.charging_station.AddressDTO
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import util.BackendUtil
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.concurrent.schedule

fun generateRandomCoordinates(): Pair<Double, Double> {
    val latitude = 46 + ThreadLocalRandom.current().nextDouble()
    val longitude = 15 + ThreadLocalRandom.current().nextDouble()
    return Pair(latitude, longitude)
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
@Preview
fun GeneratorContent() {
    val sliderValue = remember { mutableStateOf(0f) }
    val generated = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Generate addresses", fontSize = 24.sp, modifier = Modifier.padding(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(.9f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Slider(
                        modifier = Modifier.fillMaxWidth(.4f),
                        value = sliderValue.value,
                        onValueChange = { newValue ->
                            sliderValue.value = newValue
                        },
                        valueRange = 0f..10f
                    )
                    Text("Generate: ${sliderValue.value.toInt()} addresses")
                }
                Button(
                    onClick = {
                        val faker = Faker()
                        for (i in 0 until sliderValue.value.toInt()) {
                            val (latitude, longitude) = generateRandomCoordinates()
                            val randomInt = ThreadLocalRandom.current().nextInt(100000)
                            val address = AddressDTO(
                                id = randomInt,
                                title = faker.address.streetName(),
                                town = faker.address.city(),
                                postcode = faker.address.postcode(),
                                country = faker.address.country(),
                                latitude = latitude.toString(),
                                longitude = longitude.toString()
                            )
                            GlobalScope.launch {
                                BackendUtil.postAddress(address)
                            }
                        }
                        generated.value = true
                    },
                    enabled = sliderValue.value > 0 && !generated.value
                ) {
                    Text("Generate")
                }
            }
            if (generated.value) {
                Text("Addresses generated!", fontSize = 12.sp, modifier = Modifier.padding(16.dp))
                // after 2 seconds, hide the message
                Timer().schedule(2000) {
                    generated.value = false
                }
            }
        }
    }
}