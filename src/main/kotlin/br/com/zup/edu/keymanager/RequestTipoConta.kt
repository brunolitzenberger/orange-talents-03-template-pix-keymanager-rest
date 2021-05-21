package br.com.zup.edu.keymanager

import br.com.zup.edu.TipoConta

enum class RequestTipoConta(val tipo: TipoConta) {
    CONTA_CORRENTE(TipoConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoConta.CONTA_POUPANCA)
}