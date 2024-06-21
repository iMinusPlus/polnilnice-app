package dto.charging_station

data class ConnectionTypeDTO(
    val id: Int,
    var name: String,
    var discontinued: Boolean,
    var obsolete: Boolean,
    var title: String
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "id" to id.toString(),
            "name" to name,
            "discontinued" to discontinued.toString(),
            "obsolete" to obsolete.toString(),
            "title" to title
        )
    }
}
