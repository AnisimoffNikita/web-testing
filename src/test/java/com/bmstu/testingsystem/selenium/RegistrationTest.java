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

import java.util.Random;

@RunWith(Enclosed.class)
public class RegistrationTest {
    private static WebDriver driver;
    private static final String PAGE = "http://localhost:8080/sign_up";

    public static class InvalidUser {
        @Before
        public void login() {
            driver = SeleniumConfig.Companion.initDriver();
            driver.get(PAGE);
        }

        @Test
        public void userRegisterFail() {
            WebElement loginField = driver.findElement(By.id("inputUsername"));
            loginField.sendKeys("user");

            WebElement emailField = driver.findElement(By.id("inputEmail"));
            emailField.sendKeys("user@mail.ru");

            WebElement passwordField = driver.findElement(By.id("inputPassword"));
            passwordField.sendKeys("1234");

            WebElement loginButton = driver.findElement(By.id("registerButton"));
            loginButton.click();

            WebElement existingLogin = driver.findElement(By.id("existingLogin"));

            Assert.assertEquals("Это имя уже занято", existingLogin.getText());
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
            driver.get(PAGE);
        }

        @Test
        public void userRegisterSuccess() {
            WebElement loginField = driver.findElement(By.id("inputUsername"));
            loginField.sendKeys(generateRandomUsername());

            WebElement emailField = driver.findElement(By.id("inputEmail"));
            emailField.sendKeys("user@mail.ru");

            WebElement passwordField = driver.findElement(By.id("inputPassword"));
            passwordField.sendKeys("1234");

            WebElement registerButton = driver.findElement(By.id("registerButton"));
            registerButton.click();

            driver.findElement(By.className("navbar"));
        }

        @After
        public void closeDriver() {
            SeleniumConfig.Companion.logout(driver);
            driver.close();
        }

        // временный фикс, чтобы тест был не одноразовым)
        private String generateRandomUsername() {
            char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            StringBuilder sb = new StringBuilder(10);
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                char c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }
            return sb.toString();
        }
    }
}
