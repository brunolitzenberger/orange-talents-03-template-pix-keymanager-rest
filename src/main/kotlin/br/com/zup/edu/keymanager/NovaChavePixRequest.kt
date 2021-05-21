package br.com.zup.edu.keymanager

import br.com.zup.edu.KeyManagerRequest
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
data class NovaChavePixRequest(
    @field:NotNull val tipoConta: RequestTipoConta?,
    @field:NotNull val tipoChave: RequestTipoChave?,
    @field:Size(max = 77) val chave: String
) {

    fun toModelGrpc(clienteId: String): KeyManagerRequest? {
        return KeyManagerRequest
            .newBuilder()
            .setClienteId(clienteId)
            .setTipoChave(tipoChave?.tipo)
            .setChave(chave)
            .setTipoConta(tipoConta?.tipo)
            .build()
    }

}
