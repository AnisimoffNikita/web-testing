package com.bmstu.testingsystem.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DeleteExamByAdminTest {
    private static WebDriver driver;
    private static final String PAGE = "http://localhost:8080/admin/user_exams/user3";

    @BeforeClass
    public static void initDriver() {
        driver = SeleniumConfig.Companion.initDriver();
        SeleniumConfig.Companion.login(driver, "admin", "admin");
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteTest() {
        driver.get(PAGE);
        WebElement examCard = driver.findElement(By.id("6d94c110-43f1-433f-87fa-9ce0fb81edcc"));
        examCard.click();
        WebElement deleteExam = driver.findElement(By.linkText("Удалить"));
        deleteExam.click();

        driver.findElement(By.id("6d94c110-43f1-433f-87fa-9ce0fb81edcc"));
    }

    @AfterClass
    public static void closeDriver() {
        SeleniumConfig.Companion.logout(driver);
        driver.close();
    }
}