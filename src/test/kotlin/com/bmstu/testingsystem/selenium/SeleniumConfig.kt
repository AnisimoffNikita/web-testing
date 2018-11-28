package com.bmstu.testingsystem.selenium

import org.openqa.selenium.Capabilities
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.DesiredCapabilities

import java.io.File
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern

class SeleniumConfig {

    val driver: WebDriver

    init {
        val capabilities = FirefoxOptions()
        driver = FirefoxDriver(capabilities)
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS)
    }

    fun close() {
        driver.close()
    }

    fun navigateTo(url: String) {
        driver.navigate().to(url)
    }

    fun clickElement(element: WebElement) {
        element.click()
    }

    companion object {

        init {
            System.setProperty("webdriver.gecko.driver", findFile("geckodriver"))
        }

        private fun findFile(filename: String): String {
            val paths = arrayOf("", "bin/", "target/classes") // if you have chromedriver somewhere else on the path, then put it here.
            for (path in paths) {
                if (File(path + filename).exists())
                    return path + filename
            }
            return ""
        }
    }
}