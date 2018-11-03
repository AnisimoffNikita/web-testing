package com.bmstu.testingsystem.controller

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class Index {

    @GetMapping("/")
    fun index(authentication: Authentication?): String {
        return "index"
    }

}