package com.knarusawa.springandhydra.springandhydra.adapter.middleware

import com.knarusawa.springandhydra.springandhydra.domain.user.LoginUserDetails
import com.knarusawa.springandhydra.springandhydra.util.logger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import sh.ory.hydra.ApiClient
import sh.ory.hydra.api.OAuth2Api
import sh.ory.hydra.model.AcceptOAuth2LoginRequest

@Component
class AuthenticationSuccessHandler() : SimpleUrlAuthenticationSuccessHandler() {
    private val log = logger()

    override fun onAuthenticationSuccess(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authentication: Authentication?
    ) {
        val user = authentication?.principal as LoginUserDetails
        val session = request?.session
        session?.setAttribute("username", user.username)

        log.info("ログイン成功 username: [${user.username}]")
        val loginChallenge = request?.getAttribute("login_challenge") as? String
        request?.removeAttribute("login_challenge")

        log.info("login_challenge: $loginChallenge")

        if (!loginChallenge.isNullOrEmpty()) {
            val apiClient = ApiClient().apply {
                this.setBasePath("http://localhost:4445")
            }
            val oauth2 = OAuth2Api(apiClient)
            val request = AcceptOAuth2LoginRequest().subject(user.username)
            val res = oauth2.acceptOAuth2LoginRequest(loginChallenge, request)
            println(res)
            log.info(res.redirectTo)
            response?.sendRedirect(res.redirectTo)
            return
        }

        response?.sendRedirect("/userinfo")
    }
}