package com.talkdesk.service

import com.talkdesk.dto.IntegrationApiTalkDeskDTO
import com.talkdesk.infrastructure.FeignBuilder
import org.springframework.stereotype.Service

@Service
class TalkDeskAPIIntegrationImpl(
    private val feignBuilder: FeignBuilder
) : TalkDeskAPIIntegration {

    override fun getSectorByNumber(number: String): IntegrationApiTalkDeskDTO =
        feignBuilder.buildFeign().getSectorByNumber(number)

}