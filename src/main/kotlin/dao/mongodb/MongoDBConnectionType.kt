package dao.mongodb


import dao.ConnectionTypeDao
import dto.charging_station.ConnectionTypeDTO

class MongoDBConnectionType : ConnectionTypeDao {
    override suspend fun getById(id: Int): ConnectionTypeDTO? {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<ConnectionTypeDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun create(t: ConnectionTypeDTO): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: ConnectionTypeDTO): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }

}