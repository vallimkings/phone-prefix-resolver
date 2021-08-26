package com.talkdesk.infrastructure.extension

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object JacksonExtension {

    val jacksonObjectMapper: ObjectMapper by lazy {
        ObjectMapper().registerKotlinModule()
            .also { it.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) }
    }

}