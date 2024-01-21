package com.knarusawa.springandhydra.springandhydra.adapter.controller

import com.knarusawa.springandhydra.springandhydra.adapter.dto.ConsentRequestBody
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import sh.ory.hydra.ApiClient
import sh.ory.hydra.api.OAuth2Api
import sh.ory.hydra.model.AcceptOAuth2ConsentRequest

@Controller
@RequestMapping("/consent")
class ConsentController {
    val oauth2 = OAuth2Api(
            ApiClient().apply {
                this.setBasePath("http://localhost:4445")
            })

    @GetMapping
    fun consentGet(
            @RequestParam("consent_challenge") consentChallenge: String?,
            model: Model
    ): String {
        if (consentChallenge.isNullOrEmpty()) {
            return "error"
        }

        val res = oauth2.getOAuth2ConsentRequest(consentChallenge)
        val scopes = res.client?.scope?.split(" ")

        model.addAttribute("consentRequestBody", ConsentRequestBody(res.challenge, scopes))
        return "consent"
    }

    @PostMapping
    fun consentPost(
            @ModelAttribute consentRequestBody: ConsentRequestBody,
            response: HttpServletResponse?,
    ) {
        println(consentRequestBody)
        if (consentRequestBody.consentChallenge.isNullOrEmpty()) {
            throw IllegalArgumentException("consent_challengeが未設定")
        }

        val res = oauth2.acceptOAuth2ConsentRequest(
                consentRequestBody.consentChallenge,
                AcceptOAuth2ConsentRequest().grantScope(listOf("openid", "offline", "email"))
        )

        response?.sendRedirect(res.redirectTo)
    }

}