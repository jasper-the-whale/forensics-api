package com.forensics.api

import com.forensics.api.config.Authentication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(Authentication::class)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
