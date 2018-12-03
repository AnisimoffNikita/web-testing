package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.TestingSystemApplication
import com.bmstu.testingsystem.domain.UserRole
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import javax.servlet.Filter

@RunWith(SpringRunner::class)
@TestPropertySource(locations=["classpath:test.properties"])
@SpringBootTest(classes = [TestingSystemApplication::class])
@AutoConfigureMockMvc
@WebAppConfiguration
@Transactional
class SignUpTest{


    @Autowired
    private lateinit var context: WebApplicationContext

    @Autowired
    private lateinit var springSecurityFilterChain: Filter

    private lateinit var mvc: MockMvc

    @Before
    fun setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters<DefaultMockMvcBuilder>(springSecurityFilterChain)
                .build()
    }

    @Test
    fun signUpGood() {
        this.mvc.perform(
                MockMvcRequestBuilders.post("/sign_up")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .param("username","u")
                        .param("password","p")
                        .param("email","e")
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
                .andExpect(MockMvcResultMatchers.redirectedUrl("/main_page"))
    }

    @Test
    fun signUpExist() {
        this.mvc.perform(
                MockMvcRequestBuilders.post("/sign_up")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .param("username","admin")
                        .param("password","p")
                        .param("email","e")
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
                .andExpect(MockMvcResultMatchers.redirectedUrl("/sign_up?exist=true"))
    }
}