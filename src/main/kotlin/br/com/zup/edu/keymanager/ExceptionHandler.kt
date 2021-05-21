package br.com.zup.edu.keymanager

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

@Singleton
class RestExceptionHandler: ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {


    override fun handle(request: HttpRequest<*>, exception: StatusRuntimeException): HttpResponse<Any> {

        val code = exception.status.code
        val description = exception.status.description ?: ""
        val (httpStatus, message) = when (code) {
            Status.NOT_FOUND.code -> Pair(HttpStatus.NOT_FOUND, description)
            Status.INVALID_ARGUMENT.code -> Pair(HttpStatus.BAD_REQUEST, "Dados da requisição estão inválidos") // TODO: melhoria: extrair detalhes do erro
            Status.ALREADY_EXISTS.code -> Pair(HttpStatus.UNPROCESSABLE_ENTITY, description)
            else ->  {
                Pair(HttpStatus.INTERNAL_SERVER_ERROR, "Nao foi possivel completar a requisição devido ao erro: ${description} (${code})")
            }
        }

        return HttpResponse
            .status<JsonError>(httpStatus)
            .body(JsonError(message))
    }
}