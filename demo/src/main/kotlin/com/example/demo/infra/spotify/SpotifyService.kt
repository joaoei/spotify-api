package com.example.demo.infra.spotify

import com.example.demo.infra.spotify.model.*
import com.example.demo.model.Artist
import com.example.demo.service.ArtistService
import com.example.demo.service.AudioStreamingServiceInterface
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import org.json.JSONObject
import org.springframework.stereotype.Service
import org.springframework.web.servlet.ModelAndView
import java.net.URLEncoder

@Service
class SpotifyService (
    val spotifyClient: SpotifyClient,
    val artistService: ArtistService
) : AudioStreamingServiceInterface {
    private val baseUrl = "https://api.spotify.com/v1"
    private val clientId = "ace99ef223b74ec7a50d0112c7a8d006"
    private val clientSecret = "15d20d63d56143bbb6c2abfa71aaff63"
    private var token: String = ""
    private var code: String = ""

    private fun String.utf8(): String = URLEncoder.encode(this, "UTF-8")

    override fun requestUserInfoToken() : String {
        if (this.token != "") return this.token
        val params = mapOf(
            "grant_type" to "authorization_code",
            "redirect_uri" to "http://localhost:8080/callback",
            "code" to this.code,
            "client_id" to this.clientId,
            "client_secret" to this.clientSecret
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
        this.token = responseJSON.getString("access_token")
        return this.token
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

    override fun saveCode(code: String) {
        this.code = code
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
            .uri(URI.create("${baseUrl}/me/following?type=artist&limit=50"))
            .header("Authorization", "Bearer ${requestUserInfoToken()}")
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }

    override fun getCurrentUserProfileV2() : SpotifyUser {
        return spotifyClient.getUser("Bearer ${requestUserInfoToken()}");
    }

    override fun getUserArtistsByPopularity(popularity: Int) : List<Artist>? {
        val spotifyArtists = spotifyClient.getFollowedArtists("Bearer ${requestUserInfoToken()}").artists.items
        return artistService.getUserArtistsByPopularity(spotifyArtists, popularity)
    }

    /*
	- criar playlist de 20 musicas a partir de um artista
	  x pegar musicas populares desse artista e retornar - vem 10
	  x passar artista via url
	  x pegar artistas relacionados (filtrar mais populares) - vem 20
	  x pegar musicas populares desses artistas
	  x pegar data de lançamento da musica
	  . montar playlist com as 20 musicas mais populares

	  Playlist - service de streaming - data de criacao - usuario -

	  JUnit
	 */
    override fun createPlaylistByArtist(id: String): List<SpotifyTrack> {
        val idArtist = "3WrFJ7ztbogyGnTHbHJFl2"
        val auth = "Bearer ${requestUserInfoToken()}"
        val tracks = spotifyClient.getArtistsTopTracks(idArtist, auth).tracks
        val relatedArtists = spotifyClient.getRelatedArtistsByArtistId(idArtist, auth).artists

        for (artist : SpotifyArtist in relatedArtists) {
            val topTracks = spotifyClient.getArtistsTopTracks(artist.id, auth).tracks
            tracks.addAll(topTracks)
        }

        return tracks.sortedBy { it.popularity }
    }

    /* private fun requestBasicToken() : String {
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
    } */
}