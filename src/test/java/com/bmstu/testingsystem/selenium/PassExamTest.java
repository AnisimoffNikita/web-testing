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
public class PassExamTest {
    private static WebDriver driver;
    private static final String PAGE = "http://localhost:8080/exam_page/446ae2f3-eb60-44cb-b889-22f14ef06d82";

    @BeforeClass
    public static void initDriver() {
        driver = SeleniumConfig.Companion.initDriver();
        SeleniumConfig.Companion.login(driver, "user", "user");
    }

    @Test
    public void passTest() {
        driver.get(PAGE);
        WebElement checkBox = driver.findElement(By.id("list0.checkedVariants2"));
        checkBox.click();
        WebElement submitExam = driver.findElement(By.id("submitExam"));
        submitExam.click();
        WebElement result = driver.findElement(By.id("result"));

        Assert.assertEquals("Ваш результат: 1/1", result.getText());
    }

    @AfterClass
    public static void closeDriver() {
        SeleniumConfig.Companion.logout(driver);
        driver.close();
    }
}
