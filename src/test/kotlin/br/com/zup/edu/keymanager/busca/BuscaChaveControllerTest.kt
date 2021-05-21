package br.com.zup.edu.keymanager.busca

import br.com.zup.edu.*
import br.com.zup.edu.grpc.KeyManagerFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest.GET
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
internal class BuscaChaveControllerTest() {

    @Inject
    lateinit var grpcStub: KeyManagerShowServiceGrpc.KeyManagerShowServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `deve encontrar uma chave pix pelo clienteId e pixId`() {

        val clienteId = "c56dfef4-7901-44fb-84e2-a2cefb157890"
        val pixId = "c56dfef4-7901-44fb-84e2-a2cefb157890"

        val requestGrpc = ShowPixKeyRequest.newBuilder()
            .setPixId(ShowPixKeyRequest.PixIdFilter.newBuilder().setPixId(pixId).setClienteId(clienteId).build())
            .build()
        BDDMockito.given(grpcStub.listar(requestGrpc)).willReturn(responseGrpc(pixId = pixId, clienteId = clienteId))
        val request = GET<Any>("/api/v1/clientes/$clienteId/pix/$pixId")
        val response = client.toBlocking().exchange(request, Any::class.java)
        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())

    }


    private fun responseGrpc(
        clienteId: String,
        pixId: String
    ) =
        ShowPixKeyResponse.newBuilder()
            .setClienteId(clienteId)
            .setPixId(pixId)
            .setChave(ShowPixKeyResponse.ChavePix
                .newBuilder()
                .setTipo(TipoChave.ALEATORIA)
                .setChave("")
                .setConta(
                    ShowPixKeyResponse.ChavePix.MostrarPixResponse.newBuilder()
                        .setTipo(TipoConta.CONTA_CORRENTE)
                        .setInstituicao("123456")
                        .setTitular("João")
                        .setCpf("0505050")
                        .setAgencia("132456")
                        .setNumeroDaConta("123")
                        .build()
                )
                .setCriadaEm(LocalDateTime.now().toString())
            ).build()

    @Factory
    @Replaces(factory = KeyManagerFactory::class)
    internal class MockitoStubFactory {
        @Singleton
        fun mockStub() = Mockito.mock(KeyManagerShowServiceGrpc.KeyManagerShowServiceBlockingStub::class.java)
    }
}