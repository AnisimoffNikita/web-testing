package com.bmstu.testingsystem.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

import java.security.Principal

@Controller
class Index {

    @GetMapping("/")
    fun index(principal: Principal?): String {
        print(principal)
        return "index"
    }

}