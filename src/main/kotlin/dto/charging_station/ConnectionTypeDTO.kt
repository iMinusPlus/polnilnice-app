package dto.charging_station

data class ConnectionTypeDTO(
    val id: Int,
    val name: String,
    val discontinued: Boolean,
    val obsolete: Boolean,
    val title: String
)
