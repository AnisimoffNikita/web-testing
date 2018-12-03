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
import org.springframework.mock.web.MockMultipartFile
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
class ExamPageTest{


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
    fun getExamPageGood() {
        this.mvc.perform(
                MockMvcRequestBuilders.get("/exam_page/0596c2c0-a70a-47dd-81c8-31411a5b132a")
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
    fun postExamPageGood() {
        this.mvc.perform(
                MockMvcRequestBuilders.post("/exam_page/446ae2f3-eb60-44cb-b889-22f14ef06d82")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .password("admin")
                                .roles("ADMIN")
                                .authorities(UserRole.ADMIN))
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .param("list[0].questionId", "0")
                        .param("list[0].checkedVariants","1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1/1")))
    }
}