package com.bmstu.testingsystem.selenium;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class DeleteExamByUserTest {
    private static WebDriver driver;
    private static final String PAGE = "http://localhost:8080/my_exams";

    @BeforeClass
    public static void initDriver() {
        driver = SeleniumConfig.Companion.initDriver();
        SeleniumConfig.Companion.login(driver, "user3", "user3");
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteTest() {
        driver.get(PAGE);
        WebElement examCard = driver.findElement(By.id("e17b57b3-232f-4405-82e7-f21f9ef93c1a"));
        examCard.click();
        WebElement deleteExam = driver.findElement(By.linkText("Удалить"));
        deleteExam.click();

        driver.findElement(By.id("e17b57b3-232f-4405-82e7-f21f9ef93c1a"));
    }

    @AfterClass
    public static void closeDriver() {
        SeleniumConfig.Companion.logout(driver);
        driver.close();
    }
}