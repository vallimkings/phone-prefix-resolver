package com.talkdesk.exception

import com.talkdesk.dto.ResponseErrorDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(NumberIsEmptyException::class)
    fun handleEmptyList(e: NumberIsEmptyException): ResponseEntity<ResponseErrorDTO> =
        ResponseEntity.badRequest()
            .body(ResponseErrorDTO(e.message.toString(), HttpStatus.BAD_REQUEST.name))

    @ExceptionHandler(IntegrationException::class)
    fun handleIntegrationError(e: IntegrationException): ResponseEntity<ResponseErrorDTO> =
        ResponseEntity.internalServerError()
            .body(ResponseErrorDTO(e.message.toString(), HttpStatus.INTERNAL_SERVER_ERROR.name))

}
