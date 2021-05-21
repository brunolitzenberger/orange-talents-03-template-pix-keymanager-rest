package br.com.zup.edu.keymanager

import br.com.zup.edu.DeletePixKeyRequest
import br.com.zup.edu.KeyManagerRemoveServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import javax.inject.Inject

@Controller("/api/v1/clientes/{clienteId}")
class RemoveChaveController(@Inject private val deleteKeyManager: KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub) {


    @Delete("/pix/{pixId}")
    fun removeChave(
        @PathVariable clienteId: String,
        @PathVariable pixId: String
    ): HttpResponse<Any> {
       deleteKeyManager.remover(DeletePixKeyRequest
           .newBuilder()
           .setClientId(clienteId)
           .setPixId(pixId)
           .build()
       )
        return HttpResponse.ok()

    }

}