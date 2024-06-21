package util

import com.google.gson.Gson
import com.google.gson.JsonObject
import dto.user.UserDTO
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*


object UserUtil {
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun postUser(user: UserDTO): Boolean {
        try {
            val response = BackendUtil.postData("http://52.174.127.46:3000/users", user.toMap())
            return true
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun postRemoveUser(user: UserDTO): Boolean {
        try {
            val response = BackendUtil.postData("http://52.174.127.46:3000/users/remove", user.toMap())
            return true
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun getAllUsers(): MutableList<UserDTO> {
        val response: String = client.get("http://52.174.127.46:3000/users")
        val jsonElement = CustomJsonParser.parse(response)
        val jsonObject = CustomJsonParser.convertJsonArrayToJsonObject(jsonElement.asJsonArray)

        val list = mutableListOf<JsonObject>()
        jsonObject.entrySet().forEach {
            list.add(it.value.asJsonObject)
        }
        val users = mutableListOf<UserDTO>()

        list.forEach {
            users.add(convertToUser(it))
        }
        println(Gson().toJson(users))
        return users
    }

    fun convertToUser(from: JsonObject): UserDTO {
        val user = from.asJsonObject
        val userObject = UserDTO(
            id = user.get("_id").asString,
            username = user.get("username").asString,
            email = user.get("email").asString,
            password = ""
        )
        return userObject
    }
}