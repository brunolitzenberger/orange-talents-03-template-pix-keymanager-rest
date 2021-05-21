package br.com.zup.edu.keymanager

import br.com.zup.edu.KeyManagerShowAllServiceGrpc
import br.com.zup.edu.ShowAllPixKeyRequest
import br.com.zup.edu.ShowPixKeyRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import javax.inject.Inject

@Controller("/api/v1/clientes/{clienteId}")
class BuscaTodasChavesController(@Inject val buscaTodasChavesRpc: KeyManagerShowAllServiceGrpc.KeyManagerShowAllServiceBlockingStub) {


    @Get("/pix")
    fun buscaTodas(
        @PathVariable clienteId: String
    ): HttpResponse<Any> {
        val response = buscaTodasChavesRpc.all(
            ShowAllPixKeyRequest.newBuilder()
                .setClienteId(clienteId)
                .build()
        )
        val responseMapped = response.chavePixList.map {
            it.toResponseRest()
        }
        return HttpResponse.ok(responseMapped)
    }

}