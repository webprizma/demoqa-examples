package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import pages.AutomationPracticeForm;

public class TestBase {
    AutomationPracticeForm automationPracticeForm = new AutomationPracticeForm();
    TestData testData = new TestData();

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1028x768";

    }

    @BeforeEach
    public void beforeEach() {
        TestData testData = new TestData();
        automationPracticeForm.openPage();
    }
}
