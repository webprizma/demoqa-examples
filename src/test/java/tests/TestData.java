package tests;

import com.github.javafaker.Faker;

import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;

public class TestData {
    Faker fakerRussian = new Faker(new Locale("ru"));
    Faker fakerEnglish = new Faker(new Locale("en"));


    String firstName = fakerRussian.address().firstName();
    String lastName = fakerRussian.address().lastName();
    String email = fakerEnglish.internet().emailAddress();
    String gender = getGenderFromSite();
    String phone = String.valueOf(fakerRussian.number().randomNumber(10, true));
    String subject = "Maths";
    String hobby = getHobbyFromSite();
    String address = fakerRussian.address().fullAddress();
    String state = "NCR";
    String city = "Delhi";
    String birthYear = String.valueOf(fakerEnglish.number().numberBetween(1900, 2022));
    String birthMonth = "March";
    String birthDay = "08";
    String birthDate = birthDay + " " + birthMonth + "," + birthYear;  //"08 March,1987"
    String fileName = "1.png";

    public String getGenderFromSite() {
        var genders = $("#genterWrapper").$$("input");
        return genders.get(fakerEnglish.number().numberBetween(0, genders.size() - 1)).getValue();
    }

    public String getHobbyFromSite() {
        var hobbies = $("#hobbiesWrapper").$$("label");
        return hobbies.get(fakerEnglish.number().numberBetween(0, hobbies.size() - 1)).getText();
    }
}
