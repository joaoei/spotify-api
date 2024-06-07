package com.example.demo.infra.configuration

import feign.codec.ErrorDecoder
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.example.demo.infra"])
@ImportAutoConfiguration(value = [FeignAutoConfiguration::class])
class FeignConfiguration 