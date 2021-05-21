package br.com.zup.edu.keymanager.remover

import br.com.zup.edu.DeletePixKeyRequest
import br.com.zup.edu.DeletePixKeyResponse
import br.com.zup.edu.KeyManagerRemoveServiceGrpc
import br.com.zup.edu.grpc.KeyManagerFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest(transactional = false)
internal class RemoveChaveControllerTest(){

    @Inject
    lateinit var grpcStub: KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `deve remover uma chave pix`(){
        val clienteId = "c56dfef4-7901-44fb-84e2-a2cefb157890"
        val pixId = "c56dfef4-7901-44fb-84e2-a2cefb157890"

        val requestGrpc = DeletePixKeyRequest
            .newBuilder()
            .setClientId(clienteId)
            .setPixId(pixId)
            .build()
        val responseGrpc = DeletePixKeyResponse.newBuilder().setMessage("Chave removida").build()
        given(grpcStub.remover(requestGrpc)).willReturn(responseGrpc)
        val request = HttpRequest.DELETE<Any>("/api/v1/clientes/$clienteId/pix/$pixId")
        val response = client.toBlocking().exchange(request, Any::class.java)
        assertEquals(HttpStatus.OK, response.status)

    }

    @Factory
    @Replaces(factory = KeyManagerFactory::class)
    internal class MockitoStubFactory{
        @Singleton
        fun mockStub() = Mockito.mock(KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub::class.java)
    }


}