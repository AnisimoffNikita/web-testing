package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.Test
import com.bmstu.testingsystem.repositiry.TestRepository
import com.bmstu.testingsystem.repositiry.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.util.*
import kotlin.collections.ArrayList

@Controller
class MainPage {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var testRepository: TestRepository

    @GetMapping("/mainpage")
    fun getMainPage(model: Model, authentication: Authentication?): String {
        val username = authentication?.name;
        val user = userRepository.findByUsername(username!!);

        // заменить на получение данных из бд
        val listTest: ArrayList<Test> = arrayListOf()
        listTest.add(Test(user!!,"test1", "decsr1", Date(), arrayListOf()))
        listTest.add(Test(user,"test2", "decsr2", Date(), arrayListOf()))
        listTest.add(Test(user, "test3", "decsr3", Date(), arrayListOf()))
        listTest.add(Test(user,"test4", "decsr4", Date(), arrayListOf()))
        listTest.add(Test(user, "test5", "decsr5", Date(), arrayListOf()))

        model.addAttribute("tests", listTest)
        return "mainpage"
    }
}