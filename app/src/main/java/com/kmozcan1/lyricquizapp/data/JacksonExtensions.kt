package com.kmozcan1.lyricquizapp.data

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */

// extension to parse received json strings
val mapper = jacksonObjectMapper()
inline fun <reified T> JsonNode.parse() : T? {
    return try {
        mapper.readValue<T>(mapper.writeValueAsString(this))
    } catch (e: Exception) {
        null
    }
}