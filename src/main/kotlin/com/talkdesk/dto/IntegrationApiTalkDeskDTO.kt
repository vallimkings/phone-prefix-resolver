package com.talkdesk.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class IntegrationApiTalkDeskDTO(

    @JsonProperty("number")
    var number: String,

    @JsonProperty("sector")
    var sector: String,

)