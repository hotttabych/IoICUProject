package io.ioi.oio.api

import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
data class YandexGPTRequest(
    val about: String,
    val type: String,
    val date_from: String? = null,
    val date_to: String? = null
)

@Serializable
data class YandexGPTResponse(val answer: String)


object ApiRepository {
    suspend fun getYandexGPTResponse(requestData: YandexGPTRequest): YandexGPTResponse? {
        return try {
            TODO("Установить адрес сервера")
            KtorClient.client.post("http://your-api-url.com/api/yandex-gpt/") {
                contentType(ContentType.Application.Json)
                setBody(requestData)
            }.body<YandexGPTResponse>()
        } catch (e: Exception) {
            null
        }
    }
}