package dao.mongodb


import dao.ConnectionDao
import dto.charging_station.ConnectionDTO

class MongoDBConnection : ConnectionDao {
    override suspend fun getById(id: Int): ConnectionDTO? {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<ConnectionDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun create(t: ConnectionDTO): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: ConnectionDTO): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }

}