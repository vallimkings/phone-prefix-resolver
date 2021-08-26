package com.talkdesk.service

import com.talkdesk.dto.IntegrationApiTalkDeskDTO
import com.talkdesk.utils.ReadPrefixesFile
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class AggregateServiceImplTest {

    @Mock
    lateinit var readPrefixesFile: ReadPrefixesFile

    @Mock
    lateinit var talkDeskAPIIntegration: TalkDeskAPIIntegrationImpl

    @InjectMocks
    lateinit var aggregateService: AggregateServiceImpl

    @Test
    fun `should aggregate one valid number`() {
        val numbers = listOf("+1983236248")

        Mockito.`when`(talkDeskAPIIntegration.getSectorByNumber(anyString()))
            .thenReturn(IntegrationApiTalkDeskDTO(number = numbers[0], sector = "Technology"))

        Mockito.`when`(readPrefixesFile.getPrefixesFromFile())
            .thenReturn(listOf("1"))

        val result = aggregateService.aggregateNumbers(numbers)
        Assertions.assertEquals("1", result.keys.first())
        Assertions.assertEquals("Technology", result.entries.first().value.entries.first().key)
        Assertions.assertEquals(1, result.entries.first().value.entries.first().value)
    }

    @Test
    fun `should aggregate two numbers given with prefix 1`() {
        val numbers = listOf("+1983236248", "+1 7490276403", "001382355A", "+351917382672", "+35191734022")

        Mockito.`when`(talkDeskAPIIntegration.getSectorByNumber(anyString()))
            .thenReturn(IntegrationApiTalkDeskDTO(number = numbers[0], sector = "Technology"))

        Mockito.`when`(readPrefixesFile.getPrefixesFromFile())
            .thenReturn(listOf("1"))

        val result = aggregateService.aggregateNumbers(numbers)
        Assertions.assertEquals("1", result.keys.first())
        Assertions.assertEquals("Technology", result.entries.first().value.entries.first().key)
        Assertions.assertEquals(2, result.entries.first().value.entries.first().value)
    }

    @Test
    fun `should not aggregate when there is a word on string`() {
        val numbers = listOf("+198323624A")

        Mockito.`when`(talkDeskAPIIntegration.getSectorByNumber(anyString()))
            .thenReturn(IntegrationApiTalkDeskDTO(number = numbers[0], sector = "Technology"))

        Mockito.`when`(readPrefixesFile.getPrefixesFromFile())
            .thenReturn(listOf("1"))


        val result = aggregateService.aggregateNumbers(numbers)
        Assertions.assertEquals(0, result.keys.size)
    }


    @Test
    fun `should not aggregate when given number is less than three characters`() {
        val numbers = listOf("+19")

        Mockito.`when`(talkDeskAPIIntegration.getSectorByNumber(anyString()))
            .thenReturn(IntegrationApiTalkDeskDTO(number = numbers[0], sector = "Technology"))

        Mockito.`when`(readPrefixesFile.getPrefixesFromFile())
            .thenReturn(listOf("1"))


        val result = aggregateService.aggregateNumbers(numbers)
        Assertions.assertEquals(0, result.keys.size)
    }

    @Test
    fun `should not aggregate when given number is more than thirteen characters`() {
        val numbers = listOf("+90911909119091")

        Mockito.`when`(talkDeskAPIIntegration.getSectorByNumber(anyString()))
            .thenReturn(IntegrationApiTalkDeskDTO(number = numbers[0], sector = "Technology"))

        Mockito.`when`(readPrefixesFile.getPrefixesFromFile())
            .thenReturn(listOf("1"))


        val result = aggregateService.aggregateNumbers(numbers)
        Assertions.assertEquals(0, result.keys.size)
    }


    @Test
    fun `should return empty when numbers list given empty`() {
        val numbers = listOf("+198323624A")

        Mockito.`when`(talkDeskAPIIntegration.getSectorByNumber(anyString()))
            .thenReturn(IntegrationApiTalkDeskDTO(number = numbers[0], sector = "Technology"))

        Mockito.`when`(readPrefixesFile.getPrefixesFromFile())
            .thenReturn(listOf("1"))


        val result = aggregateService.aggregateNumbers(listOf())
        Assertions.assertEquals(0, result.keys.size)
    }

}