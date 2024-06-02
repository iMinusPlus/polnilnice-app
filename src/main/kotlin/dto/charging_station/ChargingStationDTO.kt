package dto.charging_station

import dto.charging_station.enums.StationStatus
import java.time.LocalDate

data class ChargingStationDTO(
    val id: Int,
    var dateLastVerified: LocalDate,
    val UUID: String,
    var dataProviderID: Int,
    var usageCost: String,
    var usageTypeID: Int,
    var address: AddressDTO,
    var connections: List<ConnectionDTO>,
    var dateCreated: LocalDate,
    var dateAddedToOurApp: LocalDate = LocalDate.now(),
    var numberOfPoints: Int,
    var statusType: StationStatus,
    var dateLastConfirmed: LocalDate,
    var comments: String
)