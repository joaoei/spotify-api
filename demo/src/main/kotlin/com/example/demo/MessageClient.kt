package com.example.demo

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import org.json.JSONObject

class MessageClient {
    private val baseUrl = "https://api.spotify.com/v1"
    private var token: String = ""

    private fun requestToken() : String {
        if (this.token != "") return this.token

        val clientId = "ace99ef223b74ec7a50d0112c7a8d006"
        val clientSecret= "15d20d63d56143bbb6c2abfa71aaff63"
        val requestBody = "grant_type=client_credentials&client_id=$clientId&client_secret=$clientSecret"

        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://accounts.spotify.com/api/token"))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        val responseJSON = JSONObject(response.body())
        this.token = responseJSON.getString("access_token")
        return this.token
    }

    fun getGenres() : String {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("${baseUrl}/recommendations/available-genre-seeds"))
            .header("Authorization", "Bearer ${requestToken()}")
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }
}