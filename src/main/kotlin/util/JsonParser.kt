package util

import com.google.gson.JsonElement
import com.google.gson.JsonParser

object JsonParser {
    fun parse(jsonString: String): JsonElement {
        val parser = JsonParser()
        return parser.parse(jsonString)
    }
}