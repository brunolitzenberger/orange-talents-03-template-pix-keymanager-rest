package br.com.zup.edu.keymanager

data class PixKeysResponse(
    val pixId: String,
    val tipoChave: TipoChaveResponse?,
    val chave: String,
    val tipoConta: TipoContaResponse?,
    val createdAt: String
) {
}