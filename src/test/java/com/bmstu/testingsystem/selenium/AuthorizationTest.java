package com.bmstu.testingsystem.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RunWith(Enclosed.class)
public class AuthorizationTest {
    private static WebDriver driver;
    private static final String LOGIN = "http://localhost:8080/sign_in";

    public static class InvalidUser {
        @Before
        public void login() {
            driver = SeleniumConfig.Companion.initDriver();
            driver.get(LOGIN);
        }

        @Test
        public void userLoginInvalid() {
            WebElement loginField = driver.findElement(By.id("inputUsername"));
            loginField.sendKeys("none");

            WebElement passwordField = driver.findElement(By.id("inputPassword"));
            passwordField.sendKeys("none");

            WebElement loginButton = driver.findElement(By.id("loginButton"));
            loginButton.click();

            WebElement invalidUser = driver.findElement(By.id("invalidUser"));

            Assert.assertEquals("Неверные логин и пароль", invalidUser.getText());
        }

        @After
        public void closeDriver() {
            driver.close();
        }
    }

    public static class ValidUser {
        @Before
        public void login() {
            driver = SeleniumConfig.Companion.initDriver();
            driver.get(LOGIN);
        }

        @Test
        public void userLoginValid() {
            SeleniumConfig.Companion.login(driver, "admin", "admin");
        }

        @After
        public void closeDriver() {
            SeleniumConfig.Companion.logout(driver);
            driver.close();
        }
    }
}
