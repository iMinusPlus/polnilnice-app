package dto.charging_station

data class AddressDTO(
    var id: Int, //id is from openchargemap
    var title: String,
    var town: String?,
    var postcode: String,
    var country: String,
    var latitude: String,
    var longitude: String
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "id" to id.toString(),
            "title" to title,
            "town" to town.toString(),
            "postcode" to postcode,
            "country" to country,
            "latitude" to latitude,
            "longitude" to longitude
        )
    }
}
