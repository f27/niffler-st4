package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class AllPeoplePage {

    private final SelenideElement allPeopleTable = $(".table tbody");

    @Step("Проверить, что в таблице со всеми пользователями есть текстом [Pending invitation]")
    public AllPeoplePage checkAllPeopleTableContainsPendingInvitationText() {
        allPeopleTable.$(byText("Pending invitation")).shouldBe(visible);
        return this;
    }

    @Step("Проверить, что в таблице со всеми пользователями есть аватар")
    public AllPeoplePage checkAllPeopleTableContainsAnotherUserAvatar() {
        allPeopleTable.$(".people__user-avatar").shouldBe(visible);
        return this;
    }

    @Step("Проверить, что в таблице со всеми пользователями есть кнопка [Submit invitation]")
    public AllPeoplePage checkAllPeopleTableContainsSubmitInvitationButton() {
        allPeopleTable.$("[data-tooltip-id='submit-invitation']").shouldBe(visible);
        return this;
    }
}
