package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.components.CalendarComponent;
import pages.components.ResultTableComponent;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AutomationPracticeForm {
    public CalendarComponent calendarComponent = new CalendarComponent();
    public ResultTableComponent resultTableComponent = new ResultTableComponent();
    SelenideElement practiceFormWrapper = $(".practice-form-wrapper"),
            firstNameInput = $("#firstName"),
            lastNameInput = $("#lastName"),
            emailInput = $("#userEmail"),
            phoneNumberInput = $("#userNumber"),
            subjectsInput = $("#subjectsInput"),
            currentAddressInput = $("#currentAddress"),
            stateDropDown = $("#state"),
            cityDropDown = $("#city"),
            uploadPictureButton = $("#uploadPicture"),
            hobbiesWrapper = $("#hobbiesWrapper"),
            dateOfBirthField = $("#dateOfBirthInput"),
            genderWrapper = $("#genterWrapper"),
            submitButton = $("#submit");

    @Step("Открываем страницу https://demoqa.com/automation-practice-form")
    public AutomationPracticeForm openPage() {
        open("/automation-practice-form");
        practiceFormWrapper.shouldHave(text("Student Registration Form"));
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");

        return this;
    }
    @Step("Вводим имя")
    public AutomationPracticeForm setFirstName(String value) {
        firstNameInput.setValue(value);

        return this;
    }
    @Step("Вводим фамилию")
    public AutomationPracticeForm setLastName(String value) {
        lastNameInput.setValue(value);

        return this;
    }
    @Step("Вводим имя и фамилию")
    public AutomationPracticeForm setFullName(String firstName, String lastName) {
        firstNameInput.setValue(firstName);
        lastNameInput.setValue(lastName);

        return this;
    }
    @Step("Вводим email")
    public AutomationPracticeForm setEmail(String value) {
        emailInput.setValue(value);

        return this;
    }
    @Step("Выбираем пол")
    public AutomationPracticeForm setGender(String value) {
        genderWrapper.$(byText(value)).click();
        //genderWrapper.$(byText(value)).parent().click();

        return this;
    }
    @Step("Вводим номер телефона")
    public AutomationPracticeForm setPhoneNumber(String value) {
        phoneNumberInput.setValue(value);

        return this;
    }
    @Step("Указываем дату рождения")
    public AutomationPracticeForm setDateOfBirth(String month, String year, String day) {
        dateOfBirthField.click();
        calendarComponent.setDate(month, year, day);

        return this;
    }
    @Step("Указываем дату рождения вводом значения")
    public AutomationPracticeForm setDateOfBirthWithKeys(String month, String year, String day) {
        dateOfBirthField.clear();
        dateOfBirthField.sendKeys(day + " " + month + " " + year);

        return this;
    }
    @Step("Выбираем предметы")
    public AutomationPracticeForm setSubjects(String value) {
        subjectsInput.sendKeys(value);
        subjectsInput.pressEnter();

        return this;
    }
    @Step("Выбираем хобби")
    public AutomationPracticeForm setHobby(String value) {
        hobbiesWrapper.$(byText(value)).click();

        return this;
    }
    @Step("Загружаем файл")
    public AutomationPracticeForm uploadFile(String value) {
        //uploadPictureButton.uploadFile(value);
        uploadPictureButton.uploadFromClasspath(value);

        return this;
    }
    @Step("Вводим адрес")
    public AutomationPracticeForm setAddress(String value) {
        currentAddressInput.setValue(value);

        return this;
    }
    @Step("Выбираем штат")
    public AutomationPracticeForm setState(String value) {
        stateDropDown.scrollTo().click();
        stateDropDown.$(byText(value)).click();

        return this;
    }
    @Step("Выбираем город")
    public AutomationPracticeForm setCity(String value) {
        cityDropDown.click();
        cityDropDown.$(byText(value)).click();

        return this;
    }
    @Step("Отправляем форму")
    public AutomationPracticeForm sendForm() {
        submitButton.scrollTo().click();

        return this;
    }
    @Step("Проверяем, что форма открылась")
    public AutomationPracticeForm checkFormOpened() {
        resultTableComponent.checkResultTableHeader();

        return this;
    }
    @Step("Проверяем результаты")
    public AutomationPracticeForm checkResult(String key, String value) {
        resultTableComponent.checkResult(key, value);

        return this;
    }

}
