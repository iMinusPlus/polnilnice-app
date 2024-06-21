package dto.charging_station

data class ConnectionDTO(
    val id: Int,
    val connectionType: ConnectionTypeDTO,
    val reference: String,
    val amps: Int,
    val voltage: Int,
    val powerKW: Int,
    val currentType: Int,
    val quantity: Int,
    val comments: String
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "id" to id.toString(),
            "connectionType" to "",
            "reference" to reference,
            "amps" to amps.toString(),
            "voltage" to voltage.toString(),
            "powerKW" to powerKW.toString(),
            "currentType" to currentType.toString(),
            "quantity" to quantity.toString(),
            "comments" to comments
        )
    }
}