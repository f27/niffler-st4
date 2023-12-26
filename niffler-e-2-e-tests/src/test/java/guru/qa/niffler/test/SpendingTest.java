package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpendingTest {

    static {
        Configuration.browserSize = "1980x1024";
    }

    @GenerateCategory(
            username = "duck",
            category = "Обучение"
    )
    @GenerateSpend(
            description = "QA.GURU Advanced 4",
            amount = 72500.00,
            currency = CurrencyValues.RUB
    )
    @Test
    @DisplayName("Выбранная трата должна быть удалена при нажатии кнопки [Delete selected]")
    void spendingShouldBeDeletedByButtonDeleteSpending(SpendJson spend) {
        //arrange
        Selenide.open("http://127.0.0.1:3000", WelcomePage.class)
                .clickLoginButton()
                .setUsername("duck")
                .setPassword("12345")
                .clickSignInButton(new MainPage())
                .selectSpendingByDescription(spend.description())
                //act
                .clickDeleteSelectedButton()
                //assert
                .checkSpendingsTableIsEmpty();
    }
}
