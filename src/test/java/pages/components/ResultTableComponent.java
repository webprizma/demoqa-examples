package pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class ResultTableComponent {
    SelenideElement resultTable = $(".table-responsive"),
            resultTableHeader = $(".modal-header");

    public ResultTableComponent checkResult(String key, String value) {
        //resultTable.$(byText(key)).parent().shouldHave(text(value));
        resultTable.$(byText(key)).sibling(0).shouldHave(text(value));

        return this;
    }

    public ResultTableComponent checkResultTableHeader() {
        resultTableHeader.shouldHave(text("Thanks for submitting the form"));

        return this;
    }


}