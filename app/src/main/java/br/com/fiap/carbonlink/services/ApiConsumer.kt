package br.com.fiap.carbonlink.services

import br.com.fiap.carbonlink.model.User
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class ApiConsumer {
    suspend fun getUser(id: String): User? {
        val endpoint = "http://localhost:8080/usuario/$id"

        return withContext(Dispatchers.IO) {
            val url = URL(endpoint)
            val connection = url.openConnection() as HttpURLConnection

            try {
                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val json = inputStream.bufferedReader().use { it.readText() }

                    val gson = Gson()
                    gson.fromJson(json, User::class.java)
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            } finally {
                connection.disconnect()
            }

        }
    }
}