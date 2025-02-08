package io.ioi.oio.api

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class ApiViewModel : ViewModel() {
    suspend fun getSources(
        about: String,
        type: String,
        startYear: String,
        endYear: String
    ): String? = withContext(Dispatchers.IO) {
        ApiRepository.getYandexGPTResponse(
            YandexGPTRequest(
                about,
                type,
                startYear,
                endYear
            )
        )?.answer
    }

    suspend fun getQuestions(
        file: File,
        maxQuestions: Int = 10
    ): String? = withContext(Dispatchers.IO) {
        ApiRepository.uploadFile(
            file,
            maxQuestions
        )
    }
}
