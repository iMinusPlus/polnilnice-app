package view.components.content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dto.charging_station.AddressDTO
import dto.charging_station.ChargingStationDTO
import dto.charging_station.ConnectionDTO
import dto.charging_station.ConnectionTypeDTO
import dto.charging_station.enums.StationStatus
import java.time.LocalDate

@Composable
@Preview
fun StationsContent() {
    var chargingStations = remember { mutableStateListOf<ChargingStationDTO>() }

    LaunchedEffect(Unit) {
        val mockConnection = ConnectionDTO(
            id = 1,
            connectionType = ConnectionTypeDTO(
                id = 1,
                name = "Connection Type 1",
                discontinued = true,
                obsolete = false,
                title = "ConnectionTitle"
            ),
            amps = 1,
            reference = "reference",
            voltage = 321,
            powerKW = 31,
            currentType = 1,
            quantity = 3,
            comments = "comments"
        )

        val mockConnections = listOf(mockConnection)
        val mockStation1 = ChargingStationDTO(
            id = 1,
            dataProviderID = 1,
            usageCost = "test",
            usageTypeID = 1,
            address = AddressDTO(
                id = 1,
                title = "FERI polnilnica",
                town = "Maribor",
                postcode = "2000",
                country = "SLovenija",
                latitude = "test",
                longitude = "test"
            ),
            connections = mockConnections,
            dateCreated = LocalDate.now(),
            numberOfPoints = 1,
            statusType = StationStatus.FREE,
            dateLastVerified =  LocalDate.now(),
            dateLastConfirmed = LocalDate.now(),
            UUID = "test",
            dateAddedToOurApp = LocalDate.now(),
            comments = "test"
        )
        val mockStation2 = ChargingStationDTO(
            id = 1,
            dataProviderID = 1,
            usageCost = "test2",
            usageTypeID = 1,
            address = AddressDTO(
                id = 1,
                title = "title",
                town = "town",
                postcode = "2000",
                country = "slovenija",
                latitude = "ads",
                longitude = "test"
            ),
            connections = mockConnections,
            dateCreated = LocalDate.now(),
            numberOfPoints = 1,
            statusType = StationStatus.FREE,
            dateLastConfirmed = LocalDate.now(),
            dateLastVerified =  LocalDate.now(),
            UUID = "test",
            dateAddedToOurApp = LocalDate.now(),
            comments = "test"
        )

        chargingStations.clear()
        chargingStations.addAll(listOf(mockStation1, mockStation2))
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
            LazyRow {
                items(chargingStations) { item ->
                    ChargingStationCard(item)
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
            Text(text = chargingStation.address.title, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
            Text(text = "${chargingStation.address.town}, ${chargingStation.address.country}")
        }
    }
}
