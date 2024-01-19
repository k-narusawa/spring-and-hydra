package com.knarusawa.springandhydra.springandhydra.adapter.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/login")
class LoginController {
    @GetMapping
    fun loginGet(): String {
        return "login"
    }
}