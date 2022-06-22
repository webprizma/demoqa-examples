//Заголовок: Полная проверка работы формы на сайте demoqa.com
//
//Предусловия: открыть браузер со страницей demoqa.com/automation-practice-form
//
//Шаги:
//- Ввести имя
//- Ввести фамилию
//- Ввести адрес электронной почты
//- Выбрать пол из списка
//- Ввести номер телефона (не более 10 символов)
//- При помощи календаря выбрать дату рождения
//  - выбрать месяц
//  - выбрать год
//  - выбрать день
//- Ввести название предмета и подтвердить выбор из предложенных значений
//- Выбрать хобби из списка
//- Загрузить файл
//- Ввести адрес
//- Выбрать штат из выпадающего списка
//- Выбрать город из выпадающего списка
//- Отправить форму
//
//Ожидаемый результат:
//- Открылось модальное окно с результатами, содержащая ВСЕ введеные данные


package tests;

import enums.Gender;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

@Owner("kulaginyv")
@Feature("Form filling tests")
public class FormTests extends TestBase {
    @Test
    @DisplayName("Заполнение ВСЕХ полей и проверка наличия ВСЕХ введенных значений в модальном окне")
    //@Disabled("Not needed right now")
    @Severity(SeverityLevel.NORMAL)
    @Story("Заполнение ВСЕХ полей и проверка наличия ВСЕХ введенных значений в модальном окне")
    @Description("Функциональный тест")
    @Link(name = "demoqa.com", url = "https://demoqa.com/automation-practice-form")
    public void fullTest() {
        automationPracticeForm.setFullName(testData.firstName, testData.lastName)
                .setEmail(testData.email)
                .setGender(testData.gender)
                .setPhoneNumber(testData.phone)
                .setDateOfBirth(testData.birthMonth,
                        testData.birthYear,
                        testData.birthDay)
                .setSubjects(testData.subject)
                .setHobby(testData.hobby)
                .uploadFile(testData.fileName)
                .setAddress(testData.address)
                .setState(testData.state)
                .setCity(testData.city)
                .sendForm();
        automationPracticeForm
                .checkFormOpened()
                .checkResult("Student Name", testData.firstName + " " + testData.lastName)
                .checkResult("Student Email", testData.email)
                .checkResult("Gender", testData.gender)
                .checkResult("Mobile", testData.phone)
                .checkResult("Date of Birth", testData.birthDate)
                .checkResult("Subjects", testData.subject)
                .checkResult("Hobbies", testData.hobby)
                .checkResult("Picture", testData.fileName)
                .checkResult("Address", testData.address)
                .checkResult("State and City", testData.state + " " + testData.city);
    }

    @Test
    @DisplayName("Заполнение только необходимых для отправки формы полей и проверка, что эти данные попали в результирующую форму")
    //@Disabled("Not needed right now")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Заполнение только необходимых для отправки формы полей и проверка, что эти данные попали в результирующую форму")
    @Description("Функциональный тест")
    @Link(name = "demoqa.com", url = "https://demoqa.com/automation-practice-form")
    public void onlyRequiredFieldsTest() {
        automationPracticeForm
                .setFullName(testData.firstName, testData.lastName)
                .setGender(testData.gender)
                .setPhoneNumber(testData.phone)
                .sendForm();
        automationPracticeForm
                .checkFormOpened()
                .checkResult("Student Name", testData.firstName + " " + testData.lastName)
                .checkResult("Gender", testData.gender)
                .checkResult("Mobile", testData.phone);
    }

    @ParameterizedTest(name = "При вводе номера телефона {0} в результатах (форме) должен быть номер телефона {0}")
    @ValueSource(strings = {
            "9991234567",
            "9091234567",
            "9631234567"
    })
    @DisplayName("Parameterized Test With Value Source")
    @Severity(SeverityLevel.MINOR)
    @Story("Parameterized Test With Value Source")
    @Description("Функциональный тест")
    @Link(name = "demoqa.com", url = "https://demoqa.com/automation-practice-form")
    public void parameterizedTestWithValueSource(String valueSource) {
        automationPracticeForm
                .setFullName(testData.firstName, testData.lastName)
                .setGender(testData.gender)
                .setPhoneNumber(valueSource)
                .sendForm();
        automationPracticeForm
                .checkFormOpened()
                .checkResult("Student Name", testData.firstName + " " + testData.lastName)
                .checkResult("Gender", testData.gender)
                .checkResult("Mobile", valueSource);
    }

    @ParameterizedTest(name = "При вводе имени {0}, фамилии {1}, пола {2} и телефона {3} в результатах (форме) должны быть имя {0}, фамилия {1}, пол {2} и телефон {3}")
    @CsvSource(value = {
            "Ivan, Ivanov, Male, 9991234567",
            "Petr, Petrov, Other, 9993214567",
            "Oksana, Brilski, Female, 9991112233"
    })
    @DisplayName("Parameterized Test With CSV Source")
    @Severity(SeverityLevel.MINOR)
    @Story("Parameterized Test With CSV Source")
    @Description("Функциональный тест")
    @Link(name = "demoqa.com", url = "https://demoqa.com/automation-practice-form")
    public void parameterizedTestWithCSVSource(String firstName, String lastName, String gender, String phone) {
        automationPracticeForm
                .setFullName(firstName, lastName)
                .setGender(gender)
                .setPhoneNumber(phone)
                .sendForm();
        automationPracticeForm
                .checkFormOpened()
                .checkResult("Student Name", firstName + " " + lastName)
                .checkResult("Gender", gender)
                .checkResult("Mobile", phone);
    }


    @ParameterizedTest(name = "При выборе пола {0} в результатах (форме) пол должен быть {0}")
    @EnumSource(Gender.class)
    @DisplayName("Parameterized Test With Enum Source")
    @Severity(SeverityLevel.MINOR)
    @Story("Parameterized Test With Enum Source")
    @Description("Функциональный тест")
    @Link(name = "demoqa.com", url = "https://demoqa.com/automation-practice-form")
    public void parameterizedTestWithEnumSource(Gender gender) {
        automationPracticeForm
                .setFullName(testData.firstName, testData.lastName)
                .setGender(gender.desc)
                .setPhoneNumber(testData.phone)
                .sendForm();
        automationPracticeForm
                .checkFormOpened()
                .checkResult("Student Name", testData.firstName + " " + testData.lastName)
                .checkResult("Gender", gender.desc)
                .checkResult("Mobile", testData.phone);
    }

    public static Stream<Arguments> methodSource() {
        return Stream.of(
                Arguments.of("Male", "Male"),
                Arguments.of("Female", "Female"),
                Arguments.of("Other", "Other")
        );
    }


    @ParameterizedTest(name = "При выборе пола {0} в результатах (форме) пол должен быть {1}")
    @MethodSource(value = "methodSource")
    @DisplayName("Parameterized Test With Method Source")
    @Severity(SeverityLevel.MINOR)
    @Story("Parameterized Test With Method Source")
    @Description("Функциональный тест")
    @Link(name = "demoqa.com", url = "https://demoqa.com/automation-practice-form")
    public void parameterizedTestWithMethodSource(String searchData, String expectedResult) {
        automationPracticeForm
                .setFullName(testData.firstName, testData.lastName)
                .setGender(searchData)
                .setPhoneNumber(testData.phone)
                .sendForm();
        automationPracticeForm
                .checkFormOpened()
                .checkResult("Student Name", testData.firstName + " " + testData.lastName)
                .checkResult("Gender", expectedResult)
                .checkResult("Mobile", testData.phone);
    }
}