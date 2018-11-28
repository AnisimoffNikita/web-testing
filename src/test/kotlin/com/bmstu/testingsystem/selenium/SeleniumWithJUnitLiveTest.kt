package com.bmstu.testingsystem.selenium


import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue

class SeleniumWithJUnitLiveTest {
    private val expectedTitle = "Baeldung | Java, Spring and Web Development tutorials"

    @Test
    fun whenAboutBaeldungIsLoaded_thenAboutEugenIsMentionedOnPage() {
        seleniumExample!!.getAboutBaeldungPage()
        val actualTitle = seleniumExample!!.title

        assertNotNull(actualTitle)
        assertEquals(expectedTitle, actualTitle)
        assertTrue(seleniumExample!!.isAuthorInformationAvailable)
    }

    companion object {

        private var seleniumExample: SeleniumExample? = null

        @BeforeClass
        fun setUp() {
            seleniumExample = SeleniumExample()
        }


        @AfterClass
        fun tearDown() {
            seleniumExample!!.closeWindow()
        }
    }

}