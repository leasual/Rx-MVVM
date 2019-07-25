package com.wesoft.rxmvvm.utils

import com.google.gson.*

object JsonUtil {
    private val gson = Gson()
    private val parser = JsonParser()

    fun isTheSame(a: Any, b: Any): Boolean {
        return isTheSame(
            gson.toJson(a),
            gson.toJson(b)
        )
    }

    private fun isTheSame(a: String, b: String): Boolean {
        if (a == b) {
            return true
        }
        val aElement =  parser.parse(a)
        val bElement =  parser.parse(b)
        if (gson.toJson(aElement) == gson.toJson(bElement)) {
            return true
        }
        return isTheSame(aElement, bElement)
    }

    private fun isTheSame(a: JsonElement, b: JsonElement): Boolean {
        return if (a.isJsonObject && b.isJsonObject) {
            isTheSame(a as JsonObject, b as JsonObject)
        }else if (a.isJsonArray && b.isJsonArray) {
            isTheSame(a as JsonArray, b as JsonArray)
        } else if (a.isJsonPrimitive && b.isJsonPrimitive) {
            isTheSame(a as JsonPrimitive, b as JsonPrimitive)
        } else if (a.isJsonNull && b.isJsonNull) {
            isTheSame(a as JsonNull, b as JsonNull)
        } else {
            false
        }
    }

    private fun isTheSame(a: JsonObject, b: JsonObject): Boolean {
        val aSet = a.keySet()
        val bSet = b.keySet()
        if (aSet != bSet) {
            return false
        }
        for (key in aSet) {
            if (!isTheSame(a[key], b[key])) {
                return false
            }
        }
        return true
    }

    private fun isTheSame(a: JsonArray, b: JsonArray): Boolean {
        if (a.size() != b.size()) return false
        val aList = toSortList(a)
        val bList = toSortList(b)
        for (index in 0 until a.size()) {
            if (!isTheSame(aList[index], bList[index])) {
                return false
            }
        }
        return true
    }

    private fun isTheSame(a: JsonPrimitive, b: JsonPrimitive): Boolean {
        return a == b
    }

    private fun isTheSame(a: JsonNull, b: JsonNull): Boolean {
        return true
    }

    private fun toSortList(a: JsonArray): List<JsonElement> {
        val list = arrayListOf<JsonElement>()
        a.forEach { list.add(it) }
        return list.sortedWith(compareBy { gson.toJson(it) })
    }
}