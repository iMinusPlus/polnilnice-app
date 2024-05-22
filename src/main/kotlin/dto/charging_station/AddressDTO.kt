package dto.charging_station


data class AddressDTO(
    val id: Int,
    val title: String,
    val town: String,
    val postcode: String,
    val country: String,
    val latitude: String,
    val longitude: String
)
