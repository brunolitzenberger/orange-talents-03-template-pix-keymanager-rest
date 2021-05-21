package br.com.zup.edu.keymanager

import io.micronaut.core.annotation.Introspected

@Introspected
data class ResponseChavePix(
    val clienteId: String?,
    val pixId: String?,
    val chave: ChaveResponse,
    val criadaEm: String?

) {
}
@Introspected
data class ChaveResponse(
    val tipo: TipoChaveResponse?,
    val chave: String?,
    val conta: ContaResponse
) {

}

enum class TipoChaveResponse {
    CPF,
    TELEFONE,
    EMAIL,
    ALEATORIA

}

@Introspected
class ContaResponse(
    val tipo: TipoContaResponse?,
    val instituicao: String?,
    val titular: String?,
    val cpf: String?,
    val agencia: String?,
    val numeroDaConta: String?
) {

}

enum class TipoContaResponse {
    CONTA_CORRENTE,
    CONTA_POUPANCA
}
