package util

import com.google.gson.Gson
import com.google.gson.JsonObject
import dto.charging_station.AddressDTO
import dto.charging_station.ChargingStationDTO
import dto.charging_station.ConnectionDTO
import dto.charging_station.ConnectionTypeDTO
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class ResponseData(
    val message: String
)

object BackendUtil {

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun <K, T> postData(url: String, data: Map<K, T>): String {
        val response: String = client.post(url) {
            contentType(ContentType.Application.Json)
            body = data
        }
        return response
    }

    suspend fun postAddress(address: AddressDTO): String {
        try {
            val res = postData("http://elektropolnilnice.eu:3000/address/app", address.toMap())
            return Json.decodeFromString<ResponseData>(res).message
        } catch (e: Exception) {
            println("Error: $e")
            return e.toString()
        }
    }

    suspend fun postConnectionType(connType: ConnectionTypeDTO): String {
        try {
            val res = postData("http://elektropolnilnice.eu:3000/connectiontype/app", connType.toMap())
            return Json.decodeFromString<ResponseData>(res).message
        } catch (e: Exception) {
            println("Error: $e")
            return e.toString()
        }
    }

    suspend fun postConnection(connection: ConnectionDTO): String {
        try {
            val resConnectionType = postConnectionType(connection.connectionType)
            val connectionMap = connection.toMap().toMutableMap()
            connectionMap["connectionType"] = resConnectionType
            val res = postData("http://elektropolnilnice.eu:3000/connection/app", connectionMap.toMap())
            return Json.decodeFromString<ResponseData>(res).message
        } catch (e: Exception) {
            println("Error: $e")
            return e.toString()
        }
    }

    suspend fun postStation(station: ChargingStationDTO): String {
        try {
            val resAddress = postAddress(station.address)
            val resConnections = station.connections.map { postConnection(it) }
            val stationMap = station.toMap().toMutableMap()
            stationMap["address"] = resAddress
            stationMap["connections"] = resConnections.toString()
            val res = postData("http://elektropolnilnice.eu:3000/elektroPolnilnice/app", stationMap.toMap())
            println(res)
            return res
        } catch (e: Exception) {
            println("Error: $e")
            return e.toString()
        }
    }

    suspend fun getAddresses(): List<AddressDTO> {
        val response: String = client.get("http://elektropolnilnice.eu:3000/address")
        val jsonElement = CustomJsonParser.parse(response)
        val jsonObject = CustomJsonParser.convertJsonArrayToJsonObject(jsonElement.asJsonArray)

        val list = mutableListOf<JsonObject>()
        jsonObject.entrySet().forEach {
            list.add(it.value.asJsonObject)
        }
        val addresses = mutableListOf<AddressDTO>()

        list.forEach {
            addresses.add(convertToAddress(it))
        }
        println(Gson().toJson(addresses))
        return addresses
    }

    fun convertToAddress(from: JsonObject): AddressDTO {
        val address = from.asJsonObject
        println(address)
        val addressObject = AddressDTO(
            id = address.get("id").asInt,
            country = address.get("country")?.asString ?: "default",
            latitude = address.get("latitude").asString ?: "default",
            longitude = address.get("longitude").asString ?: "default",
            postcode = address.get("postcode").asString,
            title = address.get("title").asString,
            town = address.get("town").asString,
        )
        return addressObject
    }
}