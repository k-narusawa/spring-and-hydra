package com.knarusawa.springandhydra.springandhydra.adapter.controller

import com.knarusawa.springandhydra.springandhydra.domain.user.LoginUserDetails
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Controller
@RequestMapping("/userinfo")
class UserInfoController {
    @GetMapping
    fun userinfoGet(
            principal: Principal,
            authentication: Authentication,
            model: Model,
    ): String {
        val user = authentication.principal as? LoginUserDetails
                ?: throw RuntimeException("OidcUserではありません")

        model.addAttribute("user", user)
        return "userinfo"
    }
}