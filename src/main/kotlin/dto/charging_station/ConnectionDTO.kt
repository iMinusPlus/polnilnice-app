package dto.charging_station

data class ConnectionDTO(
    val id: Int,
    val connectionType: Any,
    val reference: String,
    val amps: Int,
    val voltage: Int,
    val powerKW: Int,
    val currentType: Int,
    val quantity: Int,
    val comments: String
)