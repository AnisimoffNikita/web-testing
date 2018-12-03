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
public class UserResultViewTest {
    private static WebDriver driver;
    private static final String PAGE = "http://localhost:8080/my_passed_exams";

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
        Assert.assertEquals("тестик", cells.get(1).getText());
        Assert.assertEquals("user", cells.get(2).getText());
        Assert.assertEquals("1/3", cells.get(3).getText());
    }

    @AfterClass
    public static void closeDriver() {
        SeleniumConfig.Companion.logout(driver);
        driver.close();
    }
}