package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController {
	//@GetMapping("/")
	//fun index(@RequestParam("name") name: String) = "Hello, $name"
	@GetMapping("/")
	fun index2(@RequestParam("name") name: String) : String {
		return "Hello, $name"
	}
}