package br.com.zup.edu.keymanager.handler

import br.com.zup.edu.keymanager.RestExceptionHandler
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RestExceptionHandlerTest {

    val errorRequest = HttpRequest.GET<Any>("/")

    @Test
    internal fun `deve retornar 404 quando statusException for not found`() {

        val mensagem = "nao encontrado"
        val notFoundException = StatusRuntimeException(
            Status.NOT_FOUND
            .withDescription(mensagem))

        val resposta = RestExceptionHandler().handle(errorRequest, notFoundException)

        assertEquals(HttpStatus.NOT_FOUND, resposta.status)
        assertNotNull(resposta.body())
        assertEquals(mensagem, (resposta.body() as JsonError).message)
    }

    @Test
    internal fun `deve retornar 422 quando statusException for already existis`() {

        val mensagem = "chave ja existente"
        val alreadyExistsException = StatusRuntimeException(Status.ALREADY_EXISTS
            .withDescription(mensagem))

        val resposta = RestExceptionHandler().handle(errorRequest, alreadyExistsException)

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, resposta.status)
        assertNotNull(resposta.body())
        assertEquals(mensagem, (resposta.body() as JsonError).message)
    }

    @Test
    internal fun `deve retornar 400 quando statusException for invalid argument`() {

        val mensagem = "Dados da requisição estão inválidos"
        val invalidArgumentException = StatusRuntimeException(Status.INVALID_ARGUMENT)

        val resposta = RestExceptionHandler().handle(errorRequest, invalidArgumentException)

        assertEquals(HttpStatus.BAD_REQUEST, resposta.status)
        assertNotNull(resposta.body())
        assertEquals(mensagem, (resposta.body() as JsonError).message)
    }

    @Test
    internal fun `deve retornar 500 quando qualquer outro erro for lancado`() {

        val internalException = StatusRuntimeException(Status.INTERNAL)

        val resposta = RestExceptionHandler().handle(errorRequest, internalException)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resposta.status)
        assertNotNull(resposta.body())
        assertTrue((resposta.body() as JsonError).message.contains("INTERNAL"))
    }
}