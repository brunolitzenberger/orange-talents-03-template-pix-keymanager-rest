package br.com.zup.edu.keymanager

import br.com.zup.edu.ShowPixKeyResponse
import br.com.zup.edu.TipoChave
import br.com.zup.edu.TipoConta

fun ShowPixKeyResponse.toModel(): ResponseChavePix {
    return ResponseChavePix(
        clienteId = clienteId,
        pixId = pixId,
        chave = ChaveResponse(
            tipo = when(chave.tipo){
               TipoChave.CPF -> TipoChaveResponse.CPF
               TipoChave.TELEFONE -> TipoChaveResponse.TELEFONE
               TipoChave.EMAIL -> TipoChaveResponse.EMAIL
               TipoChave.ALEATORIA -> TipoChaveResponse.ALEATORIA
                else -> null
            },
            chave = chave.chave,
            conta = ContaResponse(
                tipo = when(chave.conta.tipo){
                    TipoConta.CONTA_CORRENTE -> TipoContaResponse.CONTA_CORRENTE
                    TipoConta.CONTA_POUPANCA -> TipoContaResponse.CONTA_POUPANCA
                    else -> null
                },
                instituicao = chave.conta.instituicao,
                titular = chave.conta.titular,
                cpf = chave.conta.cpf,
                agencia = chave.conta.agencia,
                numeroDaConta = chave.conta.numeroDaConta
            )
        ),
        criadaEm = chave.criadaEm
    )
}