package util

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*


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


}