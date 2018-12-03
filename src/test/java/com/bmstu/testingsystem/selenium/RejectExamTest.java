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
public class RejectExamTest {
    private static WebDriver driver;
    private static final String PAGE_NEW_EXAMS = "http://localhost:8080/admin/new_exams";
    private static final String PAGE_ALL_USER_EXAMS = "http://localhost:8080/admin/user_exams/user3";

    @BeforeClass
    public static void initDriver() {
        driver = SeleniumConfig.Companion.initDriver();
        SeleniumConfig.Companion.login(driver, "admin", "admin");
    }

    @Test
    public void rejectTest() {
        driver.get(PAGE_NEW_EXAMS);
        WebElement examCard = driver.findElement(By.id("4dfe3450-b8eb-4d50-9f0b-01b324a553b1"));
        examCard.click();
        WebElement rejectExam = driver.findElement(By.linkText("Отклонить"));
        rejectExam.click();

        driver.get(PAGE_ALL_USER_EXAMS);
        WebElement status = driver.findElement(By.id("4dfe3450-b8eb-4d50-9f0b-01b324a553b1"))
                                  .findElement(By.className("status"));

        Assert.assertEquals("Отклонен", status.getText());
    }

    @AfterClass
    public static void closeDriver() {
        SeleniumConfig.Companion.logout(driver);
        driver.close();
    }
}