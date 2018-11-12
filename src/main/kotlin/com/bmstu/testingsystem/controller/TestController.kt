package com.bmstu.testingsystem.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class TestController {

    @RequestMapping(path = arrayOf("/java"), method = arrayOf(RequestMethod.GET))
    fun greeting(@RequestParam(name = "name", required = false, defaultValue = "World") name: String, model: Model): String {
        var values1 = Values(arrayOfNulls<Item>(3).toMutableList())
        model.addAttribute("values", values1)
        return "testtest"
    }

    @RequestMapping(path = arrayOf("/java"), method = arrayOf(RequestMethod.POST))
    fun greetingPost(@ModelAttribute values: Values): String {
        return "testtest"
    }


    class Values (
            var items: MutableList<Item?>? = null
    )

    class Item (
            var id: Int? = null,
            var text: String? = null
    )
}