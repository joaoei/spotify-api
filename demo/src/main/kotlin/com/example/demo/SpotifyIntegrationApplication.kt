package com.example.demo

import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = ["com.example"])
class SpotifyIntegrationApplication

fun main(args: Array<String>) {
	runApplication<SpotifyIntegrationApplication>(*args)
}
