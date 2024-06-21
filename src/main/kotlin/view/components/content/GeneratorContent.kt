package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dto.charging_station.AddressDTO
import dto.user.UserDTO
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import util.BackendUtil
import util.UserUtil
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
    val sliderAddressValue = remember { mutableStateOf(0f) }
    val sliderUserValue = remember { mutableStateOf(0f) }
    val generated = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Generator") },
                actions = {
                    IconButton(onClick = {
                        if (sliderAddressValue.value.toInt() > 0) {
                            val faker = Faker()
                            for (i in 0 until sliderAddressValue.value.toInt()) {
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
                            sliderAddressValue.value = 0f;
                            generated.value = true
                        }

                        if (sliderUserValue.value.toInt() > 0) {
                            val faker = Faker()
                            for (i in 0 until sliderUserValue.value.toInt()) {
                                val user = UserDTO(
                                    username = faker.name.firstName(),
                                    password = faker.internet.macAddress(),
                                    email = faker.internet.email(),
                                    id = "null"
                                )
                                GlobalScope.launch {
                                    UserUtil.postUser(user)
                                }
                            }
                            sliderUserValue.value = 0f;
                            generated.value = true
                        }
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add")
                    }
                }
            )
        }
    ) {
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
                // region address generation
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
                            value = sliderAddressValue.value,
                            onValueChange = { newValue ->
                                sliderAddressValue.value = newValue
                            },
                            valueRange = 0f..10f
                        )
                        Text("Generate: ${sliderAddressValue.value.toInt()} addresses")
                    }
//                    Button(
//                        onClick = {
//                            val faker = Faker()
//                            for (i in 0 until sliderAddressValue.value.toInt()) {
//                                val (latitude, longitude) = generateRandomCoordinates()
//                                val randomInt = ThreadLocalRandom.current().nextInt(100000)
//                                val address = AddressDTO(
//                                    id = randomInt,
//                                    title = faker.address.streetName(),
//                                    town = faker.address.city(),
//                                    postcode = faker.address.postcode(),
//                                    country = faker.address.country(),
//                                    latitude = latitude.toString(),
//                                    longitude = longitude.toString()
//                                )
//                                GlobalScope.launch {
//                                    BackendUtil.postAddress(address)
//                                }
//                            }
//                            generated.value = true
//                        },
//                        enabled = sliderAddressValue.value > 0 && !generated.value
//                    ) {
//                        Text("Generate")
//                    }
                }
                // endregion
                // region user generation
                Divider(Modifier.padding(16.dp))
                Text("Generate users", fontSize = 24.sp, modifier = Modifier.padding(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(.9f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Slider(
                            modifier = Modifier.fillMaxWidth(.4f),
                            value = sliderUserValue.value,
                            onValueChange = { newValue ->
                                sliderUserValue.value = newValue
                            },
                            valueRange = 0f..10f
                        )
                        Text("Generate: ${sliderUserValue.value.toInt()} users")
                    }
//                    Button(
//                        onClick = {
//                            val faker = Faker()
//                            for (i in 0 until sliderUserValue.value.toInt()) {
//                                val user = UserDTO(
//                                    username = faker.name.firstName(),
//                                    password = faker.internet.macAddress(),
//                                    email = faker.internet.email(),
//                                    id = "null"
//                                )
//                                GlobalScope.launch {
//                                    UserUtil.postUser(user)
//                                }
//                            }
//                            generated.value = true
//                        },
//                        enabled = sliderAddressValue.value > 0 && !generated.value
//                    ) {
//                        Text("Generate")
//                    }
                }
                // endregion
                Divider(Modifier.padding(16.dp))
                if (generated.value) {
                    Text("Values generated!", fontSize = 12.sp, modifier = Modifier.padding(16.dp))
                    // after 2 seconds, hide the message
                    Timer().schedule(2000) {
                        generated.value = false
                    }
                }
            }
        }
    }
}