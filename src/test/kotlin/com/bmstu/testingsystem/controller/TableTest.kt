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
class TableTest{


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
    fun getMyPassedExams() {
        this.mvc.perform(
                MockMvcRequestBuilders.get("/my_passed_exams")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .password("admin")
                                .roles("ADMIN")
                                .authorities(UserRole.ADMIN))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("0596c2c0-a70a-47dd-81c8-31411a5b132a")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("66bcd4a3-a3d5-409e-9a38-e0d7b029a020"))))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("446ae2f3-eb60-44cb-b889-22f14ef06d82"))))
    }

    @Test
    fun getUserPassedExams() {
        this.mvc.perform(
                MockMvcRequestBuilders.get("/admin/user_passed_exams/admin2")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .password("admin")
                                .roles("ADMIN")
                                .authorities(UserRole.ADMIN))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("0596c2c0-a70a-47dd-81c8-31411a5b132a"))))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("66bcd4a3-a3d5-409e-9a38-e0d7b029a020")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString("446ae2f3-eb60-44cb-b889-22f14ef06d82"))))
    }

    @Test
    fun getUserPassedExamsBad() {
        this.mvc.perform(
                MockMvcRequestBuilders.get("/admin/user_passed_exams/admin299")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .password("admin")
                                .roles("ADMIN")
                                .authorities(UserRole.ADMIN))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Некорректный запрос")))
    }

    @Test
    fun getStatistic() {
        this.mvc.perform(
                MockMvcRequestBuilders.get("/my_exams/results/0596c2c0-a70a-47dd-81c8-31411a5b132a")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .password("admin")
                                .roles("ADMIN")
                                .authorities(UserRole.ADMIN))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun getStatisticBad() {
        this.mvc.perform(
                MockMvcRequestBuilders.get("/my_exams/results/34543534")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .password("admin")
                                .roles("ADMIN")
                                .authorities(UserRole.ADMIN))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Некорректный запрос")))
    }

}