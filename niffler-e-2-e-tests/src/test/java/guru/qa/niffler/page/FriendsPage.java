package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactOwnText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class FriendsPage {

    private final SelenideElement friendsTable = $(".table tbody");
    private final SelenideElement noFriendsYetElement = $(byText("There are no friends yet!"));

    @Step("Проверить, что в таблице с друзьями есть текст [You are friends]")
    public FriendsPage checkFriendsTableContainsYouAreFriendText() {
        friendsTable.$(byText("You are friends")).shouldBe(visible);
        return this;
    }

    @Step("Проверить, что в таблице с друзьями есть кнопка [Remove friend]")
    public FriendsPage checkFriendsTableContainsRemoveFriendButton() {
        friendsTable.$("[data-tooltip-id='remove-friend']").shouldBe(visible);
        return this;
    }

    @Step("Проверить, что в таблице с друзьями есть аватар друга")
    public FriendsPage checkFriendsTableContainsFriendsAvatar() {
        friendsTable.$(".people__user-avatar").shouldBe(visible);
        return this;
    }

    @Step("Проверить, что в таблице с друзьями нет друзей")
    public FriendsPage checkFriendsTableHasNoFriends() {
        friendsTable.$$("tr").shouldHave(size(0));
        noFriendsYetElement.shouldBe(visible);
        return this;
    }

    @Step("Проверить, что в таблице с друзьями есть кнопка [Submit invitation]")
    public FriendsPage checkFriendsTableContainsSubmitInvitationButton() {
        friendsTable.$("[data-tooltip-id='submit-invitation']").shouldBe(visible);
        return this;
    }

    @Step("Проверить, что в таблице с друзьями нет друга с именем [{username}]")
    public FriendsPage checkFriendsTableNotContainsFriend(String username) {
        friendsTable.$$("td").findBy(exactOwnText(username)).shouldNotBe(visible);
        return this;
    }
}
