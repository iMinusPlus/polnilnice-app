package dto.charging_station

data class ConnectionTypeDTO(
    val id: Int,
    var name: String,
    var discontinued: Boolean,
    var obsolete: Boolean,
    var title: String
)
