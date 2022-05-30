package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class FormTests {
    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1028x768";
    }
    @Test
    void formTest() {
        open("/automation-practice-form");

        //скрыть мешающие элементы
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");

        //переменные для теста
        String firstName = "Yuri";
        String lastName = "Kulagin";
        String email = "kulagin1987@gmail.com";
        String gender = "Male";
        String phone = "9636125863";
        String subject = "Maths";
        String hobby = "Music";
        String address = "Muravskaya, 36";
        String state = "NCR";
        String city = "Delhi";
        String stateCity = state + " " + city; //для проверки результата

        //модификация переменной дата рождения
        String birthYear = "1987";
        String birthMonth = "March";
        String birthDay = "08";
        String birthDate = birthDay + " " + birthMonth + "," + birthYear;  //"08 March,1987"

        //файл для формы
        File uploadedFile = new File("src/test/resources/1.png");
        String fileName = uploadedFile.getName();

        //подстановка
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(phone);

        //кликаем по календарю
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(birthMonth);
        $(".react-datepicker__year-select").selectOption(birthYear);
        $(".react-datepicker__day--0" + birthDay).click();

        $("#subjectsInput").sendKeys(subject); //setValue крашит апу
        $("#subjectsInput").pressEnter();

        $("#hobbiesWrapper").$(byText(hobby)).click();
        $("#uploadPicture").uploadFile(uploadedFile);
        $("#currentAddress").setValue(address);
        $("#state").scrollTo().click();
        $("#state").$(byText(state)).click();
        $("#city").click();
        $("#city").$(byText(city)).click();

        //отправляем форму
        $("#submit").scrollTo().click();

        //проверка открытия модального окна
        $(".modal-header").shouldHave(text("Thanks for submitting the form"));

        //проверка введенных данных
        $(".modal-body").shouldHave(
                text(firstName),
                text(lastName),
                text(email),
                text(gender),
                text(phone),
                text(birthDate),
                text(subject),
                text(hobby),
                text(fileName),
                text(stateCity)
        );
    }
}
