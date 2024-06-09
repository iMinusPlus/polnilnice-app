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
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "id" to id.toString(),
            "dateLastVerified" to dateLastVerified.toString(),
            "UUID" to UUID.toString(),
            "dataProviderID" to dataProviderID.toString(),
            "usageCost" to usageCost,
            "usageTypeID" to usageTypeID.toString(),
            "address" to "",
            "connections" to "",
            "dateCreated" to dateCreated.toString(),
            "dateAddedToOurApp" to dateAddedToOurApp.toString(),
            "numberOfPoints" to numberOfPoints.toString(),
            "statusType" to statusType.toString(),
            "dateLastConfirmed" to dateLastConfirmed.toString(),
            "comments" to comments
        )
    }
}