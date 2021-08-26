package com.talkdesk.controller

import com.talkdesk.service.AggregateService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/aggregate")
class AggregateController(
    val aggregateService: AggregateService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun validNumber(@RequestBody numbers: List<String>): Map<String, Map<String, Long>> =
        aggregateService.aggregateNumbers(numbers)

}

