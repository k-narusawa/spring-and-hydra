package com.knarusawa.springandhydra.springandhydra.application

import com.knarusawa.springandhydra.springandhydra.domain.user.LoginUserDetails
import com.knarusawa.springandhydra.springandhydra.domain.user.UserRepository
import com.knarusawa.springandhydra.springandhydra.domain.user.Username
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class LoginUserDetailsService(
        private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username = Username.of(username))
                ?: throw UsernameNotFoundException("対象のユーザーが見つかりませんでした。")

        return LoginUserDetails(user)
    }
}