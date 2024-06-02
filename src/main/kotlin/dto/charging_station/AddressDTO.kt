package dto.charging_station

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class AddressDTO(
    @BsonId
    var _id: ObjectId? = ObjectId(), //objectId is for mongodb
    var id: Int, //id is from openchargemap
    var title: String,
    var town: String?,
    var postcode: String,
    var country: String,
    var latitude: String,
    var longitude: String
)
