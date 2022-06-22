package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import jenkins.Attachments;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.AutomationPracticeForm;

import java.nio.charset.StandardCharsets;

public class TestBase {
    AutomationPracticeForm automationPracticeForm = new AutomationPracticeForm();
    TestData testData = new TestData();

    @BeforeAll
    public static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
//        Configuration.browserSize = "1028x768";
//        Configuration.headless = true;
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void beforeEach() {
        TestData testData = new TestData();
        automationPracticeForm.openPage();
    }

    @AfterEach
    public void afterEach() {
        Attachments.addPageSource();
        Attachments.addVideo();
        Attachments.addScreenshot();
        Attachments.browserConsoleLogs();
    }
}
