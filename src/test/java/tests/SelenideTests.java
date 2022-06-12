package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SelenideTests {

    SelenideElement softAssertionsPage = $("div#wiki-pages-box").$$("a").findBy(text("SoftAssertions"));

    @Test
    public void softAssertionsCheck() {
//      - Откройте страницу Selenide в Github
        Selenide.open("https://github.com/selenide/selenide");
//      - Перейдите в раздел Wiki проекта
        $("a#wiki-tab").click();
//      - Убедитесь, что в списке страниц (Pages) есть страница SoftAssertions
        $("li.wiki-more-pages-link").$("button").scrollTo().click();
        softAssertionsPage.should(Condition.visible);
//      - Откройте страницу SoftAssertions, проверьте что внутри есть пример кода для JUnit5
        softAssertionsPage.click();
        $$("h4").findBy(text("JUnit5")).sibling(0).innerHtml().contains("<pre>");
    }

    SelenideElement columnA = $("#column-a");
    SelenideElement columnB = $("#column-b");

    @Test
    public void dragAndDrop() {
//        - Откройте https://the-internet.herokuapp.com/drag_and_drop
        Selenide.open("https://the-internet.herokuapp.com/drag_and_drop");
//        - Перенесите прямоугольник А на место В
        columnA.dragAndDropTo(columnB);
//        - Проверьте, что прямоугольники действительно поменялись
//        $("#columns").$$(".column").first().should(text("B"));
        $("#column-a").shouldHave(text("B"));
        $("#column-b").shouldHave(text("A"));
    }

    @DisplayName("Тест не работает с actions()")
    @Test
    public void dragAndDropAlternate() {
//        - Откройте https://the-internet.herokuapp.com/drag_and_drop
        Selenide.open("https://the-internet.herokuapp.com/drag_and_drop");
//        - Перенесите прямоугольник А на место В
        Selenide.actions().moveToElement(columnA).clickAndHold().moveByOffset(300, 200).release().perform();
//        - Проверьте, что прямоугольники действительно поменялись
//        $("#columns").$$(".column").first().should(text("B"));
        $("#column-a").shouldHave(text("B"));
        $("#column-b").shouldHave(text("A"));
    }
}