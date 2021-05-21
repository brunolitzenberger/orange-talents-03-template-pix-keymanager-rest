package br.com.zup.edu.grpc

import br.com.zup.edu.KeyManagerGrpcServiceGrpc
import br.com.zup.edu.KeyManagerRemoveServiceGrpc
import br.com.zup.edu.KeyManagerShowAllServiceGrpc
import br.com.zup.edu.KeyManagerShowServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerFactory(@GrpcChannel("keyManagerGrpc") val channel: ManagedChannel) {

    @Singleton
    fun registrar() = KeyManagerGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listar() = KeyManagerShowServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun all() = KeyManagerShowAllServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun delete() = KeyManagerRemoveServiceGrpc.newBlockingStub(channel)

}