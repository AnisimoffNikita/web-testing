package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.TestingSystemApplication
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.domain.UserRole
import com.bmstu.testingsystem.security.AppUserPrincipal
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import javax.servlet.Filter

@RunWith(SpringRunner::class)
@TestPropertySource(locations=["classpath:test.properties"])
@SpringBootTest(classes = [TestingSystemApplication::class])
@AutoConfigureMockMvc
@WebAppConfiguration
@Transactional
class CreateExamTest {


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
    fun postCreateTestGood() {
        this.mvc.perform(
                MockMvcRequestBuilders.post("/create_exam")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .password("admin")
                                .roles("ADMIN")
                                .authorities(UserRole.ADMIN))
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .param("name", "test")
                        .param("description","test")
                        .param("questions[0].questionText","123")
                        .param("questions[0].type","NO_ANSWER")
                        .param("questions[0].correctInputAnswer","123")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("/exam_view/**"))
    }

    @Test
    fun postCreateTestEmpty() {
        this.mvc.perform(
                MockMvcRequestBuilders.post("/create_exam")
                        .with(SecurityMockMvcRequestPostProcessors.user(AppUserPrincipal(User("admin", "admin", "admin")))).with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Некорректный запрос")))
    }
}