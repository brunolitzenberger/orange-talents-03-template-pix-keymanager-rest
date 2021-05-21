package br.com.zup.edu.keymanager

import br.com.zup.edu.KeyManagerShowServiceGrpc
import br.com.zup.edu.ShowPixKeyRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import javax.inject.Inject

@Controller("/api/v1/clientes/{clienteId}")
class BuscaChaveController(@Inject private val buscaChaveGrpc:KeyManagerShowServiceGrpc.KeyManagerShowServiceBlockingStub) {

    @Get("/pix/{pixId}")
    fun buscaChave(
        @PathVariable clienteId: String,
        @PathVariable pixId: String
    ): HttpResponse<Any> {
        val response = buscaChaveGrpc.listar(
            ShowPixKeyRequest.newBuilder()
                .setPixId(ShowPixKeyRequest.PixIdFilter
                    .newBuilder()
                    .setPixId(pixId)
                    .setClienteId(clienteId)
                    .build()
                )
                .build()
        )

        return HttpResponse.ok(response.toModel())
    }



}