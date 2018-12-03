package com.bmstu.testingsystem.selenium;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ViewExamTest {
    private static WebDriver driver;
    private static final String PAGE = "http://localhost:8080/exam_view/446ae2f3-eb60-44cb-b889-22f14ef06d82";
    private static final String PAGE_BANNED = "http://localhost:8080/exam_view/66bcd4a3-a3d5-409e-9a38-e0d7b029a020";

    @BeforeClass
    public static void initDriver() {
        driver = SeleniumConfig.Companion.initDriver();
        SeleniumConfig.Companion.login(driver, "user", "user");
    }

    @Test
    public void viewExam() {
        driver.get(PAGE);
        WebElement name = driver.findElement(By.id("examName"));
        WebElement description = driver.findElement(By.id("examDescription"));

        WebElement questionText = driver.findElement(By.id("question1Text"));

        WebElement variant1Checkbox = driver.findElement(By.id("variant1Checkbox"));
        WebElement variant1Text = driver.findElement(By.id("variant1Text"));

        WebElement variant2Checkbox = driver.findElement(By.id("variant2Checkbox"));
        WebElement variant2Text = driver.findElement(By.id("variant2Text"));

        Assert.assertEquals("тестик", name.getText());
        Assert.assertEquals("много слов", description.getText());

        Assert.assertEquals("1+1", questionText.getText());

        Assert.assertEquals("1", variant1Text.getText());
        Assert.assertEquals("2", variant2Text.getText());

        Assert.assertFalse(variant1Checkbox.isSelected());
        Assert.assertTrue(variant2Checkbox.isSelected());
    }

    @Test
    public void viewExamFail() {
        driver.get(PAGE_BANNED);
        WebElement alert = driver.findElement(By.className("alert"));
        Assert.assertEquals(alert.getText(), "Некорректный запрос");
    }

    @AfterClass
    public static void closeDriver() {
        SeleniumConfig.Companion.logout(driver);
        driver.close();
    }
}
