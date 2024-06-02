package dao

interface DaoCrud<T> {
    suspend fun getById(id: Int): T?
    suspend fun getAll(): List<T>
    suspend fun create(t: T): Boolean
    suspend fun update(t: T): Boolean
    fun delete(id: Int): Boolean
}