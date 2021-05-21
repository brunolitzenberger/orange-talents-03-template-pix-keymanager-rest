package br.com.zup.edu.keymanager

import br.com.zup.edu.TipoChave
import br.com.zup.edu.TipoChave.*

enum class RequestTipoChave(val tipo: TipoChave) {
    CPF(TipoChave.CPF),
    TELEFONE(TipoChave.TELEFONE),
    EMAIL(TipoChave.EMAIL),
    ALEATORIA(TipoChave.ALEATORIA)

}