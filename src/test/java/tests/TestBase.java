package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import pages.AutomationPracticeForm;

public class TestBase {
    AutomationPracticeForm automationPracticeForm = new AutomationPracticeForm();
    TestData testData = new TestData();

    @BeforeAll
    public static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1028x768";
        //Configuration.headless = true;
        System.out.println("Тестирование запущено");
    }

    @BeforeEach
    public void beforeEach() {
        TestData testData = new TestData();
        automationPracticeForm.openPage();
        System.out.println("Тест запущен");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("Тест завершен");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("Тестирование завершено");
    }
}
