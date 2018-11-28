package com.bmstu.testingsystem.selenium

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class StartHerePage(private val config: SeleniumConfig) {

    @FindBy(css = ".page-title")
    private val title: WebElement? = null

    val pageTitle: String
        get() = title!!.text
}