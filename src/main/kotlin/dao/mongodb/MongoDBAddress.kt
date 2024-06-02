package dao.mongodb


import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.eq
import dao.AddressDao
import dto.charging_station.AddressDTO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.Document
import util.DatabaseUtil

class MongoDBAddress : AddressDao {

    private fun documentToAddressDTO(document: Document): AddressDTO {
        return AddressDTO(
            _id = document.getObjectId("_id"),
            id = document.getInteger("id"),
            title = document.getString("title"),
            town = document.getString("town") ?: "",
            postcode = document.getString("postcode"),
            country = document.getString("country"),
            latitude = document.getString("latitude"),
            longitude = document.getString("longitude")
        )
    }

    override suspend fun getById(id: Int): AddressDTO? {
        try {
            val collection = DatabaseUtil.database.getCollection<Document>("addresses")
            val queryParams = Filters
                .and(
                    eq("id", id)
                )
            val doc = collection.find(queryParams).limit(1).firstOrNull()
            return documentToAddressDTO(doc ?: Document())
        } catch (e: Exception) {
            println("Error in getting address by id: " + e.message)
        }
        return null
    }

    override suspend fun getAll(): List<AddressDTO> {
        try {
            val collection = DatabaseUtil.database.getCollection<Document>("addresses")
            val doc = collection.find().toList()
            val newList = mutableListOf<AddressDTO>()
            doc.forEach {
                newList.add(documentToAddressDTO(it))
            }
            return newList
        } catch (e: Exception) {
            println("Error in getting all addresses:" + e.message)
            return emptyList()
        }
    }

    override suspend fun create(t: AddressDTO): Boolean {
        try {
            val collection = DatabaseUtil.database.getCollection<AddressDTO>("addresses")
            collection.insertOne(t)
        } catch (e: Exception) {
            println("Error in creating address")
            return false
        }
        return true
    }

    override suspend fun update(t: AddressDTO): Boolean {
        try {
            val collection = DatabaseUtil.database.getCollection<AddressDTO>("addresses")
            collection.replaceOne(eq("id", t.id), t)
        } catch (e: Exception) {
            println("Error in updating address:" + e.message)
            return false
        }
        return true
    }

    override fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}