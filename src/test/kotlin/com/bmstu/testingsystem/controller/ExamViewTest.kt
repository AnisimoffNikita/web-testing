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
import org.springframework.http.MediaType
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
class ExamViewTest{


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
    fun getExamViewGood() {
        this.mvc.perform(
                MockMvcRequestBuilders.get("/exam_view/0596c2c0-a70a-47dd-81c8-31411a5b132a")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .password("admin")
                                .roles("ADMIN")
                                .authorities(UserRole.ADMIN))
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("тест главный")))
    }

    @Test
    fun getAdminExamViewGood() {
        this.mvc.perform(
                MockMvcRequestBuilders.get("/admin/exam_view/0596c2c0-a70a-47dd-81c8-31411a5b132a")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .password("admin")
                                .roles("ADMIN")
                                .authorities(UserRole.ADMIN))
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("ууу0596c2c0-a70a-47dd-81c8-31411a5b132a")))
    }

    @Test
    fun getBadExam() {
        this.mvc.perform(
                MockMvcRequestBuilders.get("/exam_view/34534534")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .password("admin")
                                .roles("ADMIN")
                                .authorities(UserRole.ADMIN))
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Некорректный запрос")))
    }
}