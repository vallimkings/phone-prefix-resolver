package com.talkdesk.service

import com.talkdesk.dto.IntegrationApiTalkDeskDTO

interface TalkDeskAPIIntegration {

    fun getSectorByNumber(number: String): IntegrationApiTalkDeskDTO

}