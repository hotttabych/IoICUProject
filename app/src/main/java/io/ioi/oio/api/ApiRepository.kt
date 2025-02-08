package io.ioi.oio.api

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.json.JSONObject
import io.ktor.client.request.forms.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File

data class YandexGPTRequest(
    val about: String,
    val type: String,
    val date_from: String? = null,
    val date_to: String? = null
) {
    fun toJson(): String {
        return JSONObject().apply {
            put("about", about)
            put("type", type)
            date_from?.let { put("date_from", it) }
            date_to?.let { put("date_to", it) }
        }.toString()
    }
}

data class YandexGPTResponse(val answer: String)

object ApiRepository {
    suspend fun getYandexGPTResponse(requestData: YandexGPTRequest): YandexGPTResponse? {
        return try {
            val response = KtorClient.client.post("http://192.168.9.117:8000/api/sources/") {
                contentType(ContentType.Application.Json)
                setBody(requestData.toJson())
            }
            val responseBody = response.body<String>()
            Log.i("RESPONSE", responseBody)
            val jsonResponse = JSONObject(responseBody)
            YandexGPTResponse(jsonResponse.getString("answer"))
        } catch (e: Exception) {
            Log.e("API_ERROR", "Failed to connect", e)
            null
        }
    }

    suspend fun uploadFile(file: File, maxQuestions: Int = 10): String? {
        return try {
            val contentType = when (file.extension.lowercase()) {
                "pdf" -> "application/pdf"
                "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                "pptx" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation"
                else -> return null
            }

            val response: HttpResponse = KtorClient.client.post("http://192.168.9.117:8000/api/gimini-questions/") {
                contentType(ContentType.MultiPart.FormData)
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("max_questions", maxQuestions.toString())
                            append("file", file.readBytes(), Headers.build {
                                append(HttpHeaders.ContentType, contentType)
                                append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                            })
                        }
                    )
                )
            }
            response.bodyAsText()
        } catch (e: Exception) {
            null
        }
    }

}