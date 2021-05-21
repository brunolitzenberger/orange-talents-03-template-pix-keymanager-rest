package br.com.zup.edu.keymanager

import br.com.zup.edu.KeyManagerGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid

@Controller("/api/v1/clientes/{clienteId}")
@Validated
class RegistraChaveController(@Inject private val registraChaveGrpc:KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceBlockingStub) {

    @Post("/pix")
    fun NovaChaveController(
        @Valid @Body request: NovaChavePixRequest,
        @PathVariable clienteId: String
    ): HttpResponse<Any> {
        val response = registraChaveGrpc.registrar(request.toModelGrpc(clienteId))
        return HttpResponse.created(HttpResponse.uri("/api/v1/clientes/$clienteId/pix/${response.pixId}"))
    }
}