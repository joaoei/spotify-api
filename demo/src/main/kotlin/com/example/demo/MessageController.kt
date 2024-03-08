package com.example.demo

import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView


@RestController
class MessageController (
	val messageClient: MessageClientInterface
) {
	@GetMapping("/")
	fun index() : String {
		return messageClient.getGenres()
	}

	@GetMapping("/login")
	fun login() : ModelAndView {
		return messageClient.login()
	}

	@GetMapping("/callback")
	fun saveCode(@RequestParam code: String, @RequestParam state: String = "") : String {
		messageClient.saveCode(code)
		val code = messageClient.getCode()
		return if (code != "") "Login feito com sucesso! $code" else "Falha ao fazer login"
	}

	@GetMapping("/user-info")
	fun getUserInfo() : String {
		return messageClient.getCurrentUserProfile()
	}

	@GetMapping("/followed-artists")
	fun getFollowedArtists() : String {
		return messageClient.getFollowedArtists()
	}
}