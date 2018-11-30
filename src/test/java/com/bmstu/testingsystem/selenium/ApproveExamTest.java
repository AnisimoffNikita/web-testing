package com.bmstu.testingsystem.selenium;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ApproveExamTest {
    private static WebDriver driver;
    private static final String PAGE_NEW_EXAMS = "http://localhost:8080/admin/new_exams";
    private static final String PAGE_ALL_USER_EXAMS = "http://localhost:8080/admin/user_exams/user3";

    @BeforeClass
    public static void initDriver() {
        driver = SeleniumConfig.Companion.initDriver();
        SeleniumConfig.Companion.login(driver, "admin", "admin");
    }

    @Test
    public void approveTest() {
        driver.get(PAGE_NEW_EXAMS);
        WebElement examCard = driver.findElement(By.id("a7f330fb-9a1a-492f-a52c-3a5be37ccff4"));
        examCard.click();
        WebElement approveExam = driver.findElement(By.linkText("Принять"));
        approveExam.click();

        driver.get(PAGE_ALL_USER_EXAMS);
        WebElement status = driver.findElement(By.id("a7f330fb-9a1a-492f-a52c-3a5be37ccff4"))
                                  .findElement(By.className("status"));

        Assert.assertEquals("Добавлен", status.getText());
    }

    @AfterClass
    public static void closeDriver() {
        SeleniumConfig.Companion.logout(driver);
        driver.close();
    }
}