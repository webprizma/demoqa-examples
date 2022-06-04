package pages;

import com.codeborne.selenide.SelenideElement;
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

    public AutomationPracticeForm openPage() {
        open("/automation-practice-form");
        practiceFormWrapper.shouldHave(text("Student Registration Form"));
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");

        return this;
    }

    public AutomationPracticeForm setFirstName(String value) {
        firstNameInput.setValue(value);

        return this;
    }

    public AutomationPracticeForm setLastName(String value) {
        lastNameInput.setValue(value);

        return this;
    }

    public AutomationPracticeForm setFullName(String firstName, String lastName) {
        firstNameInput.setValue(firstName);
        lastNameInput.setValue(lastName);

        return this;
    }

    public AutomationPracticeForm setEmail(String value) {
        emailInput.setValue(value);

        return this;
    }

    public AutomationPracticeForm setGender(String value) {
        genderWrapper.$(byText(value)).click();
        //genderWrapper.$(byText(value)).parent().click();

        return this;
    }

    public AutomationPracticeForm setPhoneNumber(String value) {
        phoneNumberInput.setValue(value);

        return this;
    }

    public AutomationPracticeForm setDateOfBirth(String month, String year, String day) {
        dateOfBirthField.click();
        calendarComponent.setDate(month, year, day);

        return this;
    }

    public AutomationPracticeForm setDateOfBirthWithKeys(String month, String year, String day) {
        dateOfBirthField.clear();
        dateOfBirthField.sendKeys(day + " " + month + " " + year);

        return this;
    }

    public AutomationPracticeForm setSubjects(String value) {
        subjectsInput.sendKeys(value);
        subjectsInput.pressEnter();

        return this;
    }

    public AutomationPracticeForm setHobby(String value) {
        hobbiesWrapper.$(byText(value)).click();

        return this;
    }

    public AutomationPracticeForm uploadFile(String value) {
        //uploadPictureButton.uploadFile(value);
        uploadPictureButton.uploadFromClasspath(value);

        return this;
    }

    public AutomationPracticeForm setAddress(String value) {
        currentAddressInput.setValue(value);

        return this;
    }

    public AutomationPracticeForm setState(String value) {
        stateDropDown.scrollTo().click();
        stateDropDown.$(byText(value)).click();

        return this;
    }

    public AutomationPracticeForm setCity(String value) {
        cityDropDown.click();
        cityDropDown.$(byText(value)).click();

        return this;
    }

    public AutomationPracticeForm sendForm() {
        submitButton.scrollTo().click();

        return this;
    }
    public AutomationPracticeForm checkFormOpened() {
        resultTableComponent.checkResultTableHeader();

        return this;
    }
    public AutomationPracticeForm checkResult(String key, String value) {
        resultTableComponent.checkResult(key, value);

        return this;
    }

}
