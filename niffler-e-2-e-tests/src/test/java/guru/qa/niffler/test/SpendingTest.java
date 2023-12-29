package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpendingTest extends BaseWebTest {

    private final WelcomePage welcomePage = new WelcomePage();
    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();

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
        Selenide.open("http://127.0.0.1:3000");
        welcomePage
                .clickLoginButton();
        loginPage
                .setUsername("duck")
                .setPassword("12345")
                .clickSignInButton();
        mainPage
                .checkSpendingsTableRowsHasSize(1)
                .selectSpendingByDescription(spend.description())
                .clickDeleteSelectedButton()
                .checkSpendingsTableRowsHasSize(0);
    }
}
