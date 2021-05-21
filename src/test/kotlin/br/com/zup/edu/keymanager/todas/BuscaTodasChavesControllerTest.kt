package br.com.zup.edu.keymanager.todas

import br.com.zup.edu.*
import br.com.zup.edu.grpc.KeyManagerFactory
import br.com.zup.edu.keymanager.PixKeysResponse
import br.com.zup.edu.keymanager.toResponseRest
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton


@MicronautTest(transactional = false)
internal class BuscaTodasChavesControllerTest(){


    @Inject
    lateinit var grpcStub: KeyManagerShowAllServiceGrpc.KeyManagerShowAllServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient


    @Test
    fun `deve trazer todas as chaves do cliente`(){
        val clienteId = "c56dfef4-7901-44fb-84e2-a2cefb157890"

        val requestGrpc = ShowAllPixKeyRequest.newBuilder()
            .setClienteId(clienteId)
            .build()
        BDDMockito.given(grpcStub.all(requestGrpc)).willReturn(responseBuilder(clienteId))
        val request = HttpRequest.GET<Any>("/api/v1/clientes/$clienteId/pix")
        val response = client.toBlocking().exchange(request, List::class.java)
        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(response.body().size, 4)
    }

    fun responseBuilder(clienteId: String) : ShowAllPixKeyResponse {

        val response1 = ShowAllPixKeyResponse
            .ChavePix
            .newBuilder()
            .setPixId("123")
            .setTipoChave(TipoChave.CPF)
            .setChave("   ")
            .setCreatedAt(LocalDateTime.now().toString())
            .build()

        val response2 = ShowAllPixKeyResponse
            .ChavePix
            .newBuilder()
            .setPixId("1234")
            .setTipoChave(TipoChave.TELEFONE)
            .setChave("    ")
            .setCreatedAt(LocalDateTime.now().toString())
            .build()

        val response3 = ShowAllPixKeyResponse
            .ChavePix
            .newBuilder()
            .setPixId("1234")
            .setTipoChave(TipoChave.EMAIL)
            .setChave("    ")
            .setCreatedAt(LocalDateTime.now().toString())
            .build()

        val response4 = ShowAllPixKeyResponse
            .ChavePix
            .newBuilder()
            .setPixId("1234")
            .setTipoChave(TipoChave.ALEATORIA)
            .setChave("    ")
            .setCreatedAt(LocalDateTime.now().toString())
            .build()


        return ShowAllPixKeyResponse
            .newBuilder()
            .setClienteId(clienteId)
            .addAllChavePix(listOf(response1, response2, response3, response4))
            .build()
    }


    @Factory
    @Replaces(factory = KeyManagerFactory::class)
    internal class MockitoStubFactory {
        @Singleton
        fun mockStub() = Mockito.mock(KeyManagerShowAllServiceGrpc.KeyManagerShowAllServiceBlockingStub::class.java)
    }
}