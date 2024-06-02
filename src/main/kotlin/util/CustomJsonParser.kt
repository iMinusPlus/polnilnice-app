package util

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser

object CustomJsonParser {
    fun parse(jsonString: String): JsonElement {
        val parser = JsonParser()
        return parser.parse(jsonString)
    }

    fun convertJsonArrayToJsonObject(jsonArray: JsonArray): JsonObject {
        val jsonObject = JsonObject()
        jsonArray.forEachIndexed { index, jsonElement ->
            jsonObject.add(index.toString(), jsonElement)
        }
        return jsonObject
    }
}