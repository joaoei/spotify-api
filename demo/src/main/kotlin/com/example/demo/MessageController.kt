package com.example.demo

import com.example.demo.infra.spotify.model.SpotifyTrack
import com.example.demo.infra.spotify.model.SpotifyUser
import com.example.demo.model.Artist
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

	@RequestMapping(value = ["/create-playlist-by-artist/{id}"])
	fun createPlaylistByArtist(@PathVariable(value = "id") id: String) : List<SpotifyTrack> {
		return streamingService.createPlaylistByArtist(id)
	}
}