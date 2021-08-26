package com.talkdesk.utils

import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList


@Component
class ReadPrefixesFile {

    fun getPrefixesFromFile() =
        Files.lines(Path.of("src/main/resources/prefixes.txt")).toList()
}

