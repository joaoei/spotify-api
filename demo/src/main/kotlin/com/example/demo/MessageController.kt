package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
@RestController
class MessageController {
	@GetMapping("/")
	fun index() : String {
		val client = MessageClient()

		return client.getGenres()
	}
}