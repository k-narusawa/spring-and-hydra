package com.knarusawa.springandhydra.springandhydra.config

import com.knarusawa.springandhydra.springandhydra.adapter.middleware.AuthenticationSuccessHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
class SecurityConfig {
    @Autowired
    private lateinit var authenticationSuccessHandler: AuthenticationSuccessHandler
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors {
            it.disable()
        }
        http.csrf {
            it.disable()
        }
        http.authorizeHttpRequests {
            it.requestMatchers("/login").permitAll()
            it.anyRequest().authenticated()
        }
        http.formLogin {
            it.loginPage("/login")
            it.successHandler(authenticationSuccessHandler)
        }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
}