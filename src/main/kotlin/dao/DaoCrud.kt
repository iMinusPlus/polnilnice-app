package dao

interface DaoCrud<T> {
    fun getById(id: Int): T?
    fun getAll(): List<T>
    fun create(t: T): T
    fun update(t: T): T
    fun delete(id: Int): Boolean
}