package com.example.demo

import com.example.demo.infra.spotify.SpotifyClient
import com.example.demo.infra.spotify.model.SpotifyArtists
import com.example.demo.infra.spotify.model.SpotifyTrack
import com.example.demo.infra.spotify.model.SpotifyTracks
import com.example.demo.infra.spotify.model.SpotifyUser
import com.example.demo.model.Artist
import com.example.demo.service.ArtistService
import com.example.demo.service.AudioStreamingServiceInterface
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView


@RestController
class MessageController (
	val streamingService: AudioStreamingServiceInterface
) {
	@GetMapping("/")
	fun index() : ModelAndView {
		return streamingService.login()
	}

	@GetMapping("/callback")
	fun saveCode(@RequestParam code: String, @RequestParam state: String = "") : String {
		streamingService.saveCode(code)
		return if (code != "") "Login feito com sucesso! $code" else "Falha ao fazer login"
	}

	@GetMapping("/user-info")
	fun getUserInfo() : String {
		return streamingService.getCurrentUserProfile()
	}

	@GetMapping("/user-info-v2")
	fun getUserInfoV2() : SpotifyUser {
		return streamingService.getCurrentUserProfileV2()
	}

	@GetMapping("/followed-artists")
	fun getFollowedArtists(@RequestParam popularity: Int) : List<Artist>? {
		return streamingService.getUserArtistsByPopularity(popularity)
	}

	@GetMapping("/create-playlist-by-artist")
	fun createPlaylistByArtist(@RequestParam id: String) : List<SpotifyTrack> {
		return streamingService.createPlaylistByArtist(id)
	}
}