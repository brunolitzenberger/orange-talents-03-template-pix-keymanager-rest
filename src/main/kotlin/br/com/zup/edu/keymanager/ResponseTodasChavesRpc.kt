package br.com.zup.edu.keymanager

import br.com.zup.edu.ShowAllPixKeyResponse
import br.com.zup.edu.TipoChave
import br.com.zup.edu.TipoConta

fun ShowAllPixKeyResponse.ChavePix.toResponseRest(): PixKeysResponse{
    return PixKeysResponse(
        pixId = pixId,
        tipoChave = when(tipoChave){
            TipoChave.CPF -> TipoChaveResponse.CPF
            TipoChave.TELEFONE -> TipoChaveResponse.TELEFONE
            TipoChave.EMAIL -> TipoChaveResponse.EMAIL
            TipoChave.ALEATORIA -> TipoChaveResponse.ALEATORIA
            else -> null
        },
        chave = chave,
        tipoConta = when(tipoConta){
            TipoConta.CONTA_CORRENTE -> TipoContaResponse.CONTA_CORRENTE
            TipoConta.CONTA_POUPANCA -> TipoContaResponse.CONTA_POUPANCA
            else -> null
        },
        createdAt = createdAt

    )
}