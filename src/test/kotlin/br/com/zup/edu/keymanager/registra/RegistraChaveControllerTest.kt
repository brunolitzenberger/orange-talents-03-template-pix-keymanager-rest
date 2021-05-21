package br.com.zup.edu.keymanager.registra

import br.com.zup.edu.KeyManagerGrpcServiceGrpc
import br.com.zup.edu.KeyManagerResponse
import br.com.zup.edu.grpc.KeyManagerFactory
import br.com.zup.edu.keymanager.NovaChavePixRequest
import br.com.zup.edu.keymanager.RequestTipoChave
import br.com.zup.edu.keymanager.RequestTipoConta
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RegistraChaveControllerTest() {

    @Inject
    lateinit var grpcStub: KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient


    @Test
    fun `deve gerar erro com maximo de caracteres maior que 77`() {

        val clienteId = "c56dfef4-7901-44fb-84e2-a2cefb157890"
        val pixId = UUID.randomUUID().toString()

        val responseGrpc = KeyManagerResponse
            .newBuilder()
            .setPixId(pixId)
            .build()
        val requestDto = NovaChavePixRequest(
            RequestTipoConta.CONTA_CORRENTE,
            RequestTipoChave.ALEATORIA,
            "121321324564654894898789789465156165165156156888978789465498fsfsdgiosdasfhasuhfSHFAOSIHFAUIFHUIAHFASUIHFUIASHFUIASFUIASHFUIASHFIUAHFUIASHFUIHASUIFHASIUIHASUIFHUIHFA48498489498498"
        )

        given(grpcStub.registrar(requestDto.toModelGrpc(clienteId))).willReturn(responseGrpc)

        val erro = assertThrows(HttpClientResponseException::class.java) {
            client.toBlocking()
                .exchange<NovaChavePixRequest, Any>(HttpRequest.POST("/api/v1/clientes/$clienteId/pix", requestDto))
        }
        assertEquals(HttpStatus.BAD_REQUEST.code, erro.status.code)
        assertEquals("request.chave: size must be between 0 and 77", erro.localizedMessage)

    }

    @Test
    fun `deve gerar erro com tipo da conta nulo`() {

        val clienteId = "c56dfef4-7901-44fb-84e2-a2cefb157890"

        val requestDto = NovaChavePixRequest(null, RequestTipoChave.ALEATORIA, "498")


        val erro = assertThrows(HttpClientResponseException::class.java) {
            client.toBlocking()
                .exchange<NovaChavePixRequest, Any>(HttpRequest.POST("/api/v1/clientes/$clienteId/pix", requestDto))
        }
        assertEquals(HttpStatus.BAD_REQUEST.code, erro.status.code)
        assertEquals("request.tipoConta: must not be null", erro.localizedMessage)

    }

    @Test
    fun `deve gerar erro com tipo de chave nulo`() {

        val clienteId = "c56dfef4-7901-44fb-84e2-a2cefb157890"
        val requestDto = NovaChavePixRequest(RequestTipoConta.CONTA_CORRENTE, null, "498")


        val erro = assertThrows(HttpClientResponseException::class.java) {
            client.toBlocking()
                .exchange<NovaChavePixRequest, Any>(HttpRequest.POST("/api/v1/clientes/$clienteId/pix", requestDto))
        }
        assertEquals(HttpStatus.BAD_REQUEST.code, erro.status.code)
        assertEquals("request.tipoChave: must not be null", erro.localizedMessage)


    }


    @Test
    fun `deve registrar uma chave pix`() {

        val clienteId = "c56dfef4-7901-44fb-84e2-a2cefb157890"
        val pixId = UUID.randomUUID().toString()

        val responseGrpc = KeyManagerResponse
            .newBuilder()
            .setPixId(pixId)
            .build()
        val requestDto = NovaChavePixRequest(RequestTipoConta.CONTA_CORRENTE, RequestTipoChave.ALEATORIA, " ")

        given(grpcStub.registrar(requestDto.toModelGrpc(clienteId))).willReturn(responseGrpc)
        val request = HttpRequest.POST("/api/v1/clientes/$clienteId/pix", requestDto)
        val response = client.toBlocking().exchange(request, NovaChavePixRequest::class.java)
        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.contains(pixId))

    }

    @Factory
    @Replaces(factory = KeyManagerFactory::class)
    internal class MockitoStubFactory {
        @Singleton
        fun mockStub() = Mockito.mock(KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceBlockingStub::class.java)
    }


}