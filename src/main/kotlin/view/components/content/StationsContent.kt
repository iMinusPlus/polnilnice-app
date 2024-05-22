package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dto.charging_station.AddressDTO
import dto.charging_station.ChargingStationDTO
import dto.charging_station.ConnectionDTO
import dto.charging_station.enums.StationStatus
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
@Preview
fun StationsContent() {
    //TODO

    var chargingStations = remember { mutableListOf<ChargingStationDTO>() }

    LaunchedEffect(Unit) {
        //todo
        var mockConnections = emptyList<ConnectionDTO>()
        val mockStation1 = ChargingStationDTO(
            id = 1,
            dataProviderID = 1,
            usageCost = "test",
            usageTypeID = 1,
            address = AddressDTO(
                id = 1,
                title = "test",
                town = "test",
                postcode = "test",
                country = "test",
                latitude = "test",
                longitude = "test"
            ),
            connections = mockConnections,
            dateCreated = LocalDate.now(),
            numberOfPoints = 1,
            statusType = StationStatus.FREE,
            dateLastConfirmed = LocalDate.now(),
        )
        val mockStation2 = ChargingStationDTO(
            id = 1,
            dataProviderID = 1,
            usageCost = "test2",
            usageTypeID = 1,
            address = AddressDTO(
                id = 1,
                title = "test",
                town = "test",
                postcode = "test",
                country = "test",
                latitude = "test",
                longitude = "test"
            ),
            connections = mockConnections,
            dateCreated = LocalDate.now(),
            numberOfPoints = 1,
            statusType = StationStatus.FREE,
            dateLastConfirmed = LocalDate.now(),
        )

        chargingStations = listOf(mockStation1, mockStation2).toMutableList()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.Center)
        ) {
//            Text("CHARGING STATIONS")
            LazyColumn {
                items(chargingStations) { item ->
                    ChargingStationCard(item)
                }
            }
        }
    }
}

@Composable
@Preview
fun ChargingStationCard(chargingStation: ChargingStationDTO) {
    //Todo
    Text(text = chargingStation.usageCost)
}
