package com.knarusawa.springandhydra.springandhydra.adapter.middleware

import com.knarusawa.springandhydra.springandhydra.domain.user.LoginUserDetails
import com.knarusawa.springandhydra.springandhydra.util.logger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class AuthenticationSuccessHandler() : SimpleUrlAuthenticationSuccessHandler() {
    private val log = logger()

    override fun onAuthenticationSuccess(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authentication: Authentication?
    ) {
        val user = authentication?.principal as LoginUserDetails

        log.info("ログイン成功 username: [${user.username}]")
        val loginChallenge = request?.getAttribute("login_challenge")
        request?.removeAttribute("login_challenge")

        log.info("login_challenge: $loginChallenge")

        val session = request?.session
        session?.setAttribute("username", user.username)
        response?.sendRedirect("/userinfo")
    }
}