package dao.mongodb


import dao.ChargingStationDao
import dto.charging_station.ChargingStationDTO

class MongoDBChargingStation : ChargingStationDao {
    override suspend fun getById(id: Int): ChargingStationDTO? {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<ChargingStationDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun create(t: ChargingStationDTO): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: ChargingStationDTO): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }

}