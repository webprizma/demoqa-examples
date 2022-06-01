package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.selector.ByText;
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

        //файл для формы .uploadFile
        File uploadedFile = new File("src/test/resources/1.png");
        String fileName = uploadedFile.getName();

        //файл для формы .uploadFromClasspath
        String fileNameFromClasspath = "1.png";

        //подстановка
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $("#genterWrapper").$(byText(gender)).click();
        //$("#genterWrapper").$(byText(gender)).parent().click(); // родитель

        $("#userNumber").setValue(phone);

        //кликаем по календарю
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(birthMonth);
        $(".react-datepicker__year-select").selectOption(birthYear);
        $(".react-datepicker__day--0" + birthDay + ":not(react-datepicker__day--outside-month").click();  //css :not() ^_^

        $("#subjectsInput").sendKeys(subject); //setValue крашит апу
        $("#subjectsInput").pressEnter();

        $("#hobbiesWrapper").$(byText(hobby)).click();

        //$("#uploadPicture").uploadFile(uploadedFile);
        $("#uploadPicture").uploadFromClasspath(fileNameFromClasspath);

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
        //$(".modal-body").shouldHave(
        //        text(firstName),
        //        text(lastName),
        //        text(email),
        //        text(gender),
        //        text(phone),
        //        text(birthDate),
        //        text(subject),
        //        text(hobby),
        //        text(fileName),
        //        text(address),
        //        text(stateCity)
        //); //проверка наличия данных в форме без учета ячеек

        //$(".table-responsive").$(byText("Date of Birth")).sibling(0).shouldHave(text(birthDate)); //проверка с учетом ячейки, поиск соседа (пример - дата рождения), описан в методе checkTable
        checkTable("Student Name", firstName);
        checkTable("Student Name", lastName);
        checkTable("Student Email", email);
        checkTable("Gender", gender);
        checkTable("Mobile", phone);
        checkTable("Date of Birth", birthDate);
        checkTable("Subjects", subject);
        checkTable("Hobbies", hobby);
        checkTable("Picture", fileName);
        checkTable("Address", address);
        checkTable("State and City", stateCity);
    }

    void checkTable(String key, String value) {
        $(".table-responsive").$(byText(key)).sibling(0).shouldHave(text(value));
    }
}
