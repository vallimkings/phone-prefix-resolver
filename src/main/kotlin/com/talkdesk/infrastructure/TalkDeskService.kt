package com.talkdesk.infrastructure

import com.talkdesk.dto.IntegrationApiTalkDeskDTO
import com.talkdesk.exception.IntegrationException
import feign.FeignException
import feign.Headers
import feign.Param
import feign.RequestLine
import feign.Response
import feign.codec.ErrorDecoder
import org.apache.logging.log4j.LogManager

interface TalkDeskService {

    @RequestLine("GET /{number}")
    @Headers("Content-Type: application/json", "Accept: application/json")
    fun getSectorByNumber(@Param("number") number: String): IntegrationApiTalkDeskDTO

    class TalkDeskApiDecoder : ErrorDecoder {

        private val logger = LogManager.getLogger(this.javaClass)

        override fun decode(message: String, response: Response): Exception {

            if (response.status() in 400..506) {
                logger.error("Response: {}", response)
                return TalkDeskIntegrationException(response.status(), String(response.body().asInputStream().readBytes()))
            }
            logger.debug("Response: {}", response)
            throw IntegrationException(String(response.body().asInputStream().readBytes()))
        }

        class TalkDeskIntegrationException(status: Int, message: String) : FeignException(status, message)

    }
}