package util

import com.google.gson.JsonNull
import com.google.gson.JsonObject
import dto.charging_station.AddressDTO
import dto.charging_station.ChargingStationDTO
import dto.charging_station.ConnectionDTO
import dto.charging_station.ConnectionTypeDTO
import dto.charging_station.enums.StationStatus
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

object Scraper {

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }


    suspend fun scrapeFromOpenChargeAPI(): MutableList<ChargingStationDTO> {
        val response: String = client.get("https://api.openchargemap.io/v3/poi/?output=json&countrycode=SI&latitude=46.5547&longitude=15.6459&maxresults=100&distance=25&key=50062ab3-b707-4dea-9da7-c8611695a9ff")
        val jsonElement = CustomJsonParser.parse(response)
        val jsonObject = CustomJsonParser.convertJsonArrayToJsonObject(jsonElement.asJsonArray)


        val list = mutableListOf<JsonObject>()
        jsonObject.entrySet().forEach {
            list.add(it.value.asJsonObject)
        }

        val addressList = mutableListOf<AddressDTO>()
        val connectionTypesList = mutableListOf<ConnectionTypeDTO>()
        val connectionDTOList = mutableListOf<ConnectionDTO>()
        val chargingStationDTOList = mutableListOf<ChargingStationDTO>()

        list.forEach {
            val address = it.get("AddressInfo").asJsonObject
            val addressObject = AddressDTO(
                id = address.get("ID")?.asInt ?: 0,
                title = address?.get("Title")?.asString ?: "null",
                town = if (address.get("Town") == null || address.get("Town") is JsonNull) "null" else address.get("Town").asString,
                postcode = address.get("Postcode")?.asString ?: "null",
                country = address.get("Country")?.asJsonObject?.get("Title")?.asString ?: "null",
                latitude = address.get("Latitude")?.asString ?: "null",
                longitude = address.get("Longitude")?.asString ?: "null"
            )
            addressList.add(addressObject)

            val connections = it.get("Connections").asJsonArray
            val connectionObjectList = mutableListOf<ConnectionDTO>()
            connections.forEach { connection ->

                //region ConnectionType
                val connectionType = connection.asJsonObject.get("ConnectionType").asJsonObject
                val connectionTypeObject = ConnectionTypeDTO(
                    id = connectionType.get("ID")?.asInt ?: 0,
                    name = if (connectionType.get("FormalName") == null || connectionType.get("FormalName") is JsonNull) "null" else connectionType.get(
                        "FormalName"
                    ).asString,
                    discontinued = if (connectionType.get("IsDiscontinued") == null || connectionType.get("IsDiscontinued") is JsonNull) false else connectionType.get(
                        "IsDiscontinued"
                    ).asBoolean,
                    obsolete = if (connectionType.get("IsObsolete") == null || connectionType.get("IsObsolete") is JsonNull) false else connectionType.get(
                        "IsObsolete"
                    ).asBoolean,
                    title = if (connectionType.get("Title") == null || connectionType.get("Title") is JsonNull) "null" else connectionType.get(
                        "Title"
                    ).asString
                )
                connectionTypesList.add(connectionTypeObject)
                //endregion

                //region Connection

                val connectionObject = ConnectionDTO(
                    id = if (connection.asJsonObject.get("ID") == null || connection.asJsonObject.get("ID") is JsonNull) 0 else connection.asJsonObject.get(
                        "ID"
                    ).asInt,
                    connectionType = connectionTypeObject,
                    reference = if (connection.asJsonObject.get("Reference") == null || connection.asJsonObject.get(
                            "Reference"
                        ) is JsonNull
                    ) "null" else connection.asJsonObject.get("Reference").asString,
                    amps = if (connection.asJsonObject.get("Amps") == null || connection.asJsonObject.get("Amps") is JsonNull) 0 else connection.asJsonObject.get(
                        "Amps"
                    ).asInt,
                    voltage = if (connection.asJsonObject.get("Voltage") == null || connection.asJsonObject.get("Voltage") is JsonNull) 0 else connection.asJsonObject.get(
                        "Voltage"
                    ).asInt,
                    powerKW = if (connection.asJsonObject.get("PowerKW") == null || connection.asJsonObject.get("PowerKW") is JsonNull) 0 else connection.asJsonObject.get(
                        "PowerKW"
                    ).asInt,
                    currentType = if (connection.asJsonObject.get("CurrentTypeID") == null || connection.asJsonObject.get(
                            "CurrentTypeID"
                        ) is JsonNull
                    ) 0 else connection.asJsonObject.get("CurrentTypeID").asInt,
                    quantity = if (connection.asJsonObject.get("Quantity") == null || connection.asJsonObject.get("Quantity") is JsonNull) 0 else connection.asJsonObject.get(
                        "Quantity"
                    ).asInt,
                    comments = if (connection.asJsonObject.get("Comments") == null || connection.asJsonObject.get("Comments") is JsonNull) "null" else connection.asJsonObject.get(
                        "Comments"
                    ).asString
                )

                connectionDTOList.add(connectionObject)
                connectionObjectList.add(connectionObject)
                //endregion
            }

            //Todo: change statusType and comments
            val stationObject = ChargingStationDTO(
                id = it.get("ID")?.asInt ?: 0,
                dateLastVerified = it.get("DateLastVerified")?.asString?.let { it1 ->
                    Instant.parse(it1).atZone(ZoneId.systemDefault()).toLocalDate()
                }
                    ?: LocalDate.now(),
                UUID = if (it.get("UUID") == null || it.get("UUID") is JsonNull) "null" else it.get("UUID").asString,
                dataProviderID = if (it.get("DataProviderID") == null || it.get("DataProviderID") is JsonNull) 0 else it.get(
                    "DataProviderID"
                ).asInt,
                usageCost = if (it.get("UsageCost") == null || it.get("UsageCost") is JsonNull) "null" else it.get("UsageCost").asString,
                usageTypeID = if (it.get("UsageTypeID") == null || it.get("UsageTypeID") is JsonNull) 0 else it.get("UsageTypeID").asInt,
                address = addressObject,
                connections = connectionObjectList,
                dateCreated = it.get("DateCreated")?.asString?.let { it1 ->
                    Instant.parse(it1).atZone(ZoneId.systemDefault()).toLocalDate()
                } ?: LocalDate.now(),
                numberOfPoints = if (it.get("NumberOfPoints") == null || it.get("NumberOfPoints") is JsonNull) 0 else it.get(
                    "NumberOfPoints"
                ).asInt,
                statusType = StationStatus.FREE,
                dateLastConfirmed = if (it.get("DateLastConfirmed") == null || it.get("DateLastConfirmed") is JsonNull) LocalDate.now() else Instant.parse(
                    it.get("DateLastConfirmed").asString
                ).atZone(ZoneId.systemDefault()).toLocalDate(),
                comments = "null"
            )

            chargingStationDTOList.add(stationObject)
        }

        return chargingStationDTOList
    }


    //TODO: Implement this function
    suspend fun scrapeFromDDD(): String {
        return "hello"
    }
}