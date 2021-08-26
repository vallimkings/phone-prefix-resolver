package com.talkdesk.service

interface AggregateService {

    fun aggregateNumbers(numbers: List<String>): Map<String, Map<String, Long>>

}