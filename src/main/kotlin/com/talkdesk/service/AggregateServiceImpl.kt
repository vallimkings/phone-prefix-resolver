package com.talkdesk.service

import com.talkdesk.dto.CompositionAggregateDTO
import com.talkdesk.dto.IntegrationApiTalkDeskDTO
import com.talkdesk.utils.ReadPrefixesFile
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class AggregateServiceImpl(
    val talkDeskAPIIntegration: TalkDeskAPIIntegration,
    val readPrefixesFile: ReadPrefixesFile
) : AggregateService {

    override fun aggregateNumbers(numbers: List<String>): Map<String, Map<String, Long>> =
        checkIfPrefixMatchAndCallApiToFetchSector(
            numberNormalized = NumberResolverImpl.normalizeNumber(numbers),
            prefixesFromFile = readPrefixesFile.getPrefixesFromFile()
        )

    private fun checkIfPrefixMatchAndCallApiToFetchSector(
        numberNormalized: List<String>,
        prefixesFromFile: List<String>
    ): Map<String, Map<String, Long>> {

        val numbersAggregated = arrayListOf<CompositionAggregateDTO>()

        numberNormalized.forEach { number ->
            prefixesFromFile.firstOrNull { number.startsWith(it) }
                ?.let { prefix ->
                    talkDeskAPIIntegration.getSectorByNumber(number = number)
                        .also {
                            addToListNumberAggregated(
                                numbersAggregated = numbersAggregated,
                                prefix = prefix,
                                integrationApiTalkDeskDto = it
                            )
                        }
                }
        }
        return buildResponseWithNumbersAggregated(numbersAggregated)
    }

    private fun addToListNumberAggregated(
        numbersAggregated: ArrayList<CompositionAggregateDTO>,
        prefix: String,
        integrationApiTalkDeskDto: IntegrationApiTalkDeskDTO
    ) {
        numbersAggregated.add(
            CompositionAggregateDTO(
                prefix = prefix,
                number = integrationApiTalkDeskDto.number,
                sector = integrationApiTalkDeskDto.sector
            )
        )
    }

    private fun buildResponseWithNumbersAggregated(compositionAggregateDTO: List<CompositionAggregateDTO>): Map<String, Map<String, Long>> {
        val numbersAggregated: MutableMap<String, Map<String, Long>> = HashMap()

        compositionAggregateDTO.stream().collect(Collectors.groupingBy { it.prefix })
            ?.let {
                it.forEach { (prefix, list) ->
                    list.stream().collect(Collectors.groupingBy({ it.sector }, Collectors.counting()))
                        .also { numbersAggregated[prefix] = it }
                }
            }
        return numbersAggregated
    }
}