package com.bmstu.testingsystem.selenium

import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

import java.io.File
import java.util.concurrent.TimeUnit

class SeleniumConfig {

    companion object {
        private val LOGIN : String = "http://localhost:8080/sign_in"

        init {
            System.setProperty("webdriver.gecko.driver", findFile("geckodriver.exe"))
        }

        fun initDriver() : WebDriver {
            val driver: WebDriver
            val capabilities = FirefoxOptions()
            driver = FirefoxDriver(capabilities)
            driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS)
            return driver
        }

        fun logout(driver: WebDriver) {
            try {
                val profileDropdown = driver.findElement(By.id("dropdown02"))
                profileDropdown.click()
                val exitButton = driver.findElement(By.id("exitButton"))
                exitButton.click()
            } catch (ex: NoSuchElementException) {
                ex.printStackTrace()
            }
        }

        fun login(driver: WebDriver, login: String, password: String) {
            driver.get(LOGIN)

            val loginField = driver.findElement(By.id("inputUsername"))
            loginField.sendKeys(login)

            val passwordField = driver.findElement(By.id("inputPassword"))
            passwordField.sendKeys(password)

            val loginButton = driver.findElement(By.id("loginButton"))
            loginButton.click()

            driver.findElement(By.className("navbar"))
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