package com.bmstu.testingsystem.selenium;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class CreateExamTest {
    private static WebDriver driver;
    private static final String PAGE = "http://localhost:8080/create_exam";

    @BeforeClass
    public static void initDriver() {
        driver = SeleniumConfig.Companion.initDriver();
        SeleniumConfig.Companion.login(driver, "user", "user");
    }

    @Before
    public void getPage() {
        driver.get(PAGE);
    }

    @Test
    public void createTestWithoutQuestions() {
        fillNameAndDescription();
        deleteQuestion();
        saveExam();

        WebElement errorMessage = driver.findElement(By.className("error-message"));
        Assert.assertEquals("Добавьте хотя бы один вопрос", errorMessage.getText());
    }

    @Test
    public void createTestWithoutAnswers() {
        fillNameAndDescription();
        fillQuestionText();
        saveExam();

        WebElement errorMessage = driver.findElement(By.className("error-message"));
        Assert.assertEquals("Добавьте хотя бы один вариант ответа", errorMessage.getText());
    }

    @Test
    public void createTestWithoutRightAnswers() {
        fillNameAndDescription();
        fillQuestionText();
        fillVariantText();
        addVariant();
        saveExam();

        WebElement errorMessage = driver.findElement(By.className("error-message"));
        Assert.assertEquals("Отметьте правильные варианты ответа", errorMessage.getText());
    }

    @Test
    public void createTestSuccessWithSingleAnswer() {
        fillNameAndDescription();
        fillQuestionText();
        fillVariantText();
        addVariant();
        checkVariant();
        saveExam();

        driver.findElement(By.id("examView"));
    }

    @Test
    public void createTestSuccessWithMultipleAnswer() {
        fillNameAndDescription();
        fillQuestionText();
        selectType("MULTIPLE_ANSWER");
        fillVariantText();
        addVariant();
        checkVariant();
        saveExam();

        driver.findElement(By.id("examView"));
    }

    @Test
    public void createTestSuccessWithoutAnswer() {
        fillNameAndDescription();
        fillQuestionText();
        selectType("NO_ANSWER");
        fillRigthAnswer();
        saveExam();

        driver.findElement(By.id("examView"));
    }

    @AfterClass
    public static void closeDriver() {
        SeleniumConfig.Companion.logout(driver);
        driver.close();
    }

    private void deleteQuestion() {
        WebElement deleteQuestionButton = driver.findElement(By.className("del-answer-block")).findElement(By.tagName("button"));
        deleteQuestionButton.click();
    }

    private void fillNameAndDescription() {
        WebElement examName = driver.findElement(By.id("testName"));
        examName.sendKeys("название экзамена");

        WebElement examDescription = driver.findElement(By.id("text"));
        examDescription.sendKeys("описание экзамена");
    }

    private void fillQuestionText() {
        WebElement questionsText = driver.findElement(By.className("questionText"));
        questionsText.sendKeys("текст вопроса 1");
    }

    private void fillVariantText() {
        WebElement answerText = driver.findElement(By.className("answer-input"));
        answerText.sendKeys("текст ответа 1");
    }

    private void addVariant() {
        WebElement addAnswerButton = driver.findElement(By.className("add-button")).findElement(By.tagName("button"));
        addAnswerButton.click();
    }

    private void saveExam() {
        WebElement saveExam = driver.findElement(By.id("saveExam"));
        saveExam.click();
    }

    private void checkVariant() {
        WebElement answerCheckBox = driver.findElement(By.className("ans-list")).findElement(By.tagName("input"));
        answerCheckBox.click();
    }

    private void selectType(String type) {
        Select selectType = new Select(driver.findElement(By.tagName("select")));
        selectType.selectByValue(type);
    }

    private void fillRigthAnswer() {
        WebElement answerText = driver.findElement(By.className("right-answer-input"));
        answerText.sendKeys("текст ответа 1");
    }
}
