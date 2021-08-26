package com.talkdesk.infrastructure

import com.talkdesk.infrastructure.extension.JacksonExtension
import feign.Feign
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class FeignBuilder {

    @Value("\${api.talk.desk.sector.url}")
    lateinit var sectorEndpoint: String

    private val jacksonEncoder by lazy { JacksonEncoder(JacksonExtension.jacksonObjectMapper) }
    private val jacksonDecoder by lazy { JacksonDecoder(JacksonExtension.jacksonObjectMapper) }

    fun buildFeign() = Feign.builder()
        .encoder(jacksonEncoder)
        .decoder(jacksonDecoder)
        .errorDecoder(TalkDeskService.TalkDeskApiDecoder())
        .target(TalkDeskService::class.java, sectorEndpoint)!!

}
