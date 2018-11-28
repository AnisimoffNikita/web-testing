package com.bmstu.testingsystem.selenium

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class LoginPage {

    private val config: SeleniumConfig? = null

    @FindBy(id = ".header--menu > a")
    private val title: WebElement? = null

    @FindBy(css = ".menu-start-here > a")
    private val startHere: WebElement? = null

    val pageTitle: String
        get() = title!!.getAttribute("title")

    fun navigate() {
        this.config!!.navigateTo("http://localhost:8080/sign_in")
    }

    fun clickOnStartHere(): StartHerePage {
        config!!.clickElement(startHere!!)

        val startHerePage = StartHerePage(config)
        PageFactory.initElements(config.driver, startHerePage)

        return startHerePage
    }
}
