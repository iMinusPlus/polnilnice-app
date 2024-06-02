package view

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import dao.mongodb.MongoDBAddress
import dto.charging_station.AddressDTO
import kotlinx.coroutines.flow.count
import org.bson.Document
import util.DatabaseUtil


suspend fun main() {

    DatabaseUtil.testDB()

    val item = AddressDTO(
        id = 1,
        title = "title",
        town = "town1",
        postcode = "postcode",
        country = "country",
        latitude = "latitude",
        longitude = "longitude"
    )

////    val daoItem = MongoDBAddress().create(item)
//    val daoItem2 = MongoDBAddress().getById(1)
//    val daoItem3 = MongoDBAddress().getById(296288)
//    println(daoItem3?.town)

//    val list = MongoDBAddress().getAll()
//    list.forEach(::println)

    item._id = null
    MongoDBAddress().update(item)

    DatabaseUtil.client.close()
}
