package tests;

import com.github.javafaker.Faker;

import java.util.Locale;

public class TestData {

    Faker fakerRussian = new Faker(new Locale("ru"));
    Faker fakerEnglish = new Faker(new Locale("en"));


    String firstName = fakerRussian.address().firstName();
    String lastName = fakerRussian.address().lastName();
    String email = fakerEnglish.internet().emailAddress();
    String gender = GetTestData.getGenderFromSite();
    String phone = String.valueOf(fakerRussian.number().randomNumber(10, true));
    String subject = "Maths";
    String hobby = GetTestData.getHobbyFromSite();
    String address = fakerRussian.address().fullAddress();
    String state = "NCR";
    String city = "Delhi";
    String birthYear = String.valueOf(fakerEnglish.number().numberBetween(1900, 2022));
    String birthMonth = "March";
    String birthDay = "08";
    String birthDate = birthDay + " " + birthMonth + "," + birthYear;  //"08 March,1987"
    String fileName = "1.png";


}
