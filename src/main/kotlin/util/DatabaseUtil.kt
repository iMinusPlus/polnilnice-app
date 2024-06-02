package util

import com.google.gson.Gson
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.count
import org.bson.Document
import java.io.File

data class Config(val database: DatabaseConfig)
data class DatabaseConfig(
    val baseName: String,
    val connectionString: String
)


/**
 * Utility class for database operations
 * @property DatabaseUtil Make sure you close the client after using this utility
 */
object DatabaseUtil {

    private val config = Gson().fromJson(File("config.json").readText(), Config::class.java)
    val client = MongoClient.create(config.database.connectionString)
    val database = client.getDatabase(config.database.baseName)

    suspend fun listAllCollection() {
        val count = database.listCollectionNames().count()
        println("Collection count $count")

        print("Collection in this database are -----------> ")
        database.listCollectionNames().collect { print(" $it") }
        println()
    }

    suspend fun testDB() {
        try {
            val command = Document("ping", 1)
            database.runCommand<Document>(command)
            println("pinged deployment")
        } catch (e: Exception) {
            println("failed to ping deployment")
        }

        listAllCollection()
    }
}