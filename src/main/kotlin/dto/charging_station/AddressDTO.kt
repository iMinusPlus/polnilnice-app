package dto.charging_station

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class AddressDTO(
    @BsonId
    val _id: ObjectId = ObjectId(), //objectId is for mongodb
    val id: Int, //id is from openchargemap
    val title: String,
    val town: String,
    val postcode: String,
    val country: String,
    val latitude: String,
    val longitude: String
)
