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

import java.util.List;

@RunWith(SpringRunner.class)
public class ExamResultViewTest {
    private static WebDriver driver;
    private static final String PAGE = "http://localhost:8080/my_exams/results/6c8d5358-4111-4d28-a12e-fecfa3c1ce78";
    private static final String PAGE_BANNED = "http://localhost:8080/my_exams/results/0596c2c0-a70a-47dd-81c8-31411a5b132a";

    @BeforeClass
    public static void initDriver() {
        driver = SeleniumConfig.Companion.initDriver();
        SeleniumConfig.Companion.login(driver, "user2", "user2");
    }

    @Test
    public void viewResults() {
        driver.get(PAGE);
        WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> cells = table.findElements(By.tagName("td"));
        Assert.assertEquals("2007-11-18", cells.get(0).getText());
        Assert.assertEquals("user3", cells.get(1).getText());
        Assert.assertEquals("3/3", cells.get(2).getText());
    }

    @Test
    public void viewResultsFail() {
        driver.get(PAGE_BANNED);
        WebElement alert = driver.findElement(By.className("alert"));
        Assert.assertEquals(alert.getText(), "Вы не можете просматривать эту страницу");
    }

    @AfterClass
    public static void closeDriver() {
        SeleniumConfig.Companion.logout(driver);
        driver.close();
    }
}
