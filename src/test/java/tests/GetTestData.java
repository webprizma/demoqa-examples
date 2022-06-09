package tests;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;

public class GetTestData {
    static Faker fakerEnglish = new Faker(new Locale("en"));

    public static String getGenderFromSite() {
        Selenide.open("https://demoqa.com/automation-practice-form");
        var genders = $("#genterWrapper").$$("input");
        return genders.get(fakerEnglish.number().numberBetween(0, genders.size() - 1)).getValue();
    }

    public static String getHobbyFromSite() {
        Selenide.open("https://demoqa.com/automation-practice-form");
        var hobbies = $("#hobbiesWrapper").$$("label");
        return hobbies.get(fakerEnglish.number().numberBetween(0, hobbies.size() - 1)).getText();
    }
}
