package view

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.count
import org.bson.Document
import util.DatabaseUtil


suspend fun main() {

    DatabaseUtil.testDB()
    DatabaseUtil.client.close()
}
