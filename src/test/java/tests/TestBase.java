package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.CredentialsConfig;
import io.qameta.allure.selenide.AllureSelenide;
import jenkins.Attachments;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.AutomationPracticeForm;

public class TestBase {
    static CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);

    AutomationPracticeForm automationPracticeForm = new AutomationPracticeForm();
    TestData testData = new TestData();

    @BeforeAll
    static void beforeAll() {
        String login = config.login();
        String password = config.password();
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.remote = "https://" + login + ":" + password + "@selenoid.autotests.cloud/wd/hub";
        System.getProperty("browser", "chrome");
        System.getProperty("version", "101");
        System.getProperty("windowSize", "1920x1080");
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
