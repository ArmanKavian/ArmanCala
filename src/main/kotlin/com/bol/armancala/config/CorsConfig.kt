package com.bol.armancala.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {
    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        // Allow only specific origins
        config.addAllowedOrigin("http://localhost:4200")
        config.allowCredentials = true

        // Allow specific headers
        config.addAllowedHeader("Origin")
        config.addAllowedHeader("Authorization")
        config.addAllowedHeader("Content-Type")
        config.addAllowedHeader("Accept")

        // Allow specific HTTP methods
        config.addAllowedMethod("GET")
        config.addAllowedMethod("POST")

        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}
