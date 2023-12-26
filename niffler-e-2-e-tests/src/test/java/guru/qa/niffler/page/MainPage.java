package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    private final SelenideElement spendingsTable = $(".spendings-table tbody");
    private final SelenideElement deleteSelectedButton = $(byText("Delete selected"));

    @Step("Выбрать трату с описанием [{description}]")
    public MainPage selectSpendingByDescription(String description) {
        spendingsTable
                .$$("tr")
                .find(text(description))
                .$$("td")
                .first()
                .scrollIntoView(true)
                .click();
        return this;
    }

    @Step("Нажать кнопку [Delete selected]")
    public MainPage clickDeleteSelectedButton() {
        deleteSelectedButton.click();
        return this;
    }

    @Step("Проверить, что таблица с тратами пуста")
    public void checkSpendingsTableIsEmpty() {
        spendingsTable
                .$$("tr")
                .shouldHave(size(0));
    }
}
