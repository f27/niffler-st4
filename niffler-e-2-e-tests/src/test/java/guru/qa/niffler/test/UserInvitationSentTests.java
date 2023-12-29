package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static guru.qa.niffler.jupiter.annotation.User.UserType.INVITATION_SENT;

public class UserInvitationSentTests extends BaseWebTest {

    private final WelcomePage welcomePage = new WelcomePage();
    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final AllPeoplePage allPeoplePage = new AllPeoplePage();
    private final FriendsPage friendsPage = new FriendsPage();

    @BeforeEach
    void openWelcomePage() {
        Selenide.open("http://127.0.0.1:3000");
    }

    @Test
    @DisplayName("У пользователя с отправленным приглашением в таблице со всеми пользователями должен быть текст [Pending invitation]")
    void userShouldHavePendingInvitationTextInAllPeopleTable(@User(INVITATION_SENT) UserJson user) {
        welcomePage
                .clickLoginButton();
        loginPage
                .setUsername(user.username())
                .setPassword(user.testData().password())
                .clickSignInButton();
        mainPage
                .clickAllPeopleButton();
        allPeoplePage
                .checkAllPeopleTableContainsPendingInvitationText();
    }

    @Test
    @DisplayName("У пользователя с отправленным приглашением в таблице со всеми пользователями должен быть виден аватар другого пользователя")
    void userShouldHaveAnotherUserAvatarInAllPeopleTable(@User(INVITATION_SENT) UserJson user) {
        welcomePage
                .clickLoginButton();
        loginPage
                .setUsername(user.username())
                .setPassword(user.testData().password())
                .clickSignInButton();
        mainPage
                .clickAllPeopleButton();
        allPeoplePage
                .checkAllPeopleTableContainsAnotherUserAvatar();
    }

    @Test
    @DisplayName("У пользователя с отправленным приглашением в таблице с друзьями не должно быть друзей")
    void userShouldNotHaveFriendsInFriendsTable(@User(INVITATION_SENT) UserJson user) {
        welcomePage
                .clickLoginButton();
        loginPage
                .setUsername(user.username())
                .setPassword(user.testData().password())
                .clickSignInButton();
        mainPage
                .clickFriendsButton();
        friendsPage
                .checkFriendsTableHasNoFriends();
    }
}
