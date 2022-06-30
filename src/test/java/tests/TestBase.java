package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.selenide.webdriver.WebDriverFactory;
import config.CredentialsConfig;
import io.qameta.allure.selenide.AllureSelenide;
import jenkins.Attachments;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.AutomationPracticeForm;

public class TestBase {
    public static CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);

    AutomationPracticeForm automationPracticeForm = new AutomationPracticeForm();
    TestData testData = new TestData();

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.remote = String.format("https://%s:%s@%s", config.login(), config.password(), System.getProperty("remote"));
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("version", "100");
        Configuration.browserSize = System.getProperty("windowSize", "1920x1080");
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
        if (!System.getProperty("browser").equals("firefox")) {
            Attachments.browserConsoleLogs();
        }
    }
}
