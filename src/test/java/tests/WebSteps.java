package tests;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.open;

public class WebSteps {
    @Step("Открываем страницу https://the-internet.herokuapp.com/drag_and_drop")
    public void openMainPage() {
        open("https://the-internet.herokuapp.com/drag_and_drop");
    }
}
