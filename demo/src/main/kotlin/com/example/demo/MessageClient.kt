package com.example.demo

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import org.json.JSONObject
import org.springframework.stereotype.Service
import org.springframework.web.servlet.ModelAndView
import java.net.URLEncoder

@Service
class MessageClient : MessageClientInterface {
    private val baseUrl = "https://api.spotify.com/v1"
    private val clientId = "ace99ef223b74ec7a50d0112c7a8d006"
    private val clientSecret = "15d20d63d56143bbb6c2abfa71aaff63"
    private var tokenBasic: String = ""
    private var tokenUserInfo: String = ""
    private var code: String = ""

    private fun String.utf8(): String = URLEncoder.encode(this, "UTF-8")
    private fun requestBasicToken() : String {
        if (this.tokenBasic != "") return this.tokenBasic

        val requestBody = "grant_type=client_credentials&client_id=${this.clientId}&client_secret=${this.clientSecret}"

        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://accounts.spotify.com/api/token"))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        val responseJSON = JSONObject(response.body())
        this.tokenBasic = responseJSON.getString("access_token")
        return this.tokenBasic
    }
    private fun requestUserInfoToken() : String {
        if (this.tokenUserInfo != "") return this.tokenUserInfo
        val params = mapOf(
            "grant_type" to "authorization_code",
            "redirect_uri" to "http://localhost:8080/callback",
            "code" to "${this.code}",
            "client_id" to "${this.clientId}",
            "client_secret" to "${this.clientSecret}"
        )
        val urlParams = params.map {(k, v) -> "${(k.utf8())}=${v.utf8()}"}
            .joinToString("&")

        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://accounts.spotify.com/api/token"))
            .POST(HttpRequest.BodyPublishers.ofString(urlParams))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        val responseJSON = JSONObject(response.body())
        this.tokenUserInfo = responseJSON.getString("access_token")
        return this.tokenUserInfo

        return ""
    }
    override fun login(): ModelAndView {
        // save code
        val clientId = "ace99ef223b74ec7a50d0112c7a8d006"
        // Recomendado passar um "state" similar a crypto.randomBytes(60).toString('hex').slice(0, length);
        val params = mapOf(
            "response_type" to "code",
            "client_id" to clientId,
            "scope" to "user-read-private user-read-email user-library-read user-library-modify user-read-recently-played user-top-read user-library-read user-follow-read playlist-read-private playlist-modify-public playlist-modify-private playlist-read-collaborative",
            "redirect_uri" to "http://localhost:8080/callback"
        )
        val urlParams = params.map {(k, v) -> "${(k.utf8())}=${v.utf8()}"}
            .joinToString("&")

        return ModelAndView("redirect:https://accounts.spotify.com/authorize?${urlParams}")
    }

    override fun saveCode(code: String) : Unit {
        this.code = code;
    }

    override fun getCode() : String {
        return this.code;
    }

    override fun getGenres() : String {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("${baseUrl}/recommendations/available-genre-seeds"))
            .header("Authorization", "Bearer ${requestBasicToken()}")
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }

    override fun getCurrentUserProfile() : String {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("${baseUrl}/me"))
            .header("Authorization", "Bearer ${requestUserInfoToken()}")
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }

    override fun getFollowedArtists() : String {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("${baseUrl}/me/following?type=artist"))
            .header("Authorization", "Bearer ${requestUserInfoToken()}")
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }
}