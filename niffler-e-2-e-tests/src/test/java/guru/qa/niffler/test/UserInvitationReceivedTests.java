package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static guru.qa.niffler.jupiter.annotation.User.UserType.INVITATION_RECEIVED;

public class UserInvitationReceivedTests extends BaseWebTest {

    private final WelcomePage welcomePage = new WelcomePage();
    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final AllPeoplePage allPeoplePage = new AllPeoplePage();
    private final FriendsPage friendsPage = new FriendsPage();
    private final ProfilePage profilePage = new ProfilePage();

    @BeforeEach
    void doLogin(@User(INVITATION_RECEIVED) UserJson user) {
        Selenide.open("http://127.0.0.1:3000");
        welcomePage
                .clickLoginButton();
        loginPage
                .setUsername(user.username())
                .setPassword(user.testData().password())
                .clickSignInButton();
    }

    @Test
    @DisplayName("У пользователя с полученным приглашением в таблице со всеми пользователями должна быть кнопка [Submit invitation]")
    void userShouldHaveSubmitInvitationButtonInAllPeopleTable() {
        mainPage
                .clickAllPeopleButton();
        allPeoplePage
                .checkAllPeopleTableContainsSubmitInvitationButton();
    }

    @Test
    @DisplayName("У пользователя с полученным приглашением в таблице с друзьями должна быть кнопка [Submit invitation]")
    void userShouldHaveSubmitInvitationButtonInFriendsTable() {
        mainPage
                .clickAllPeopleButton();
        friendsPage
                .checkAllPeopleTableContainsSubmitInvitationButton();
    }

    @Test
    @DisplayName("У пользователя с полученным приглашением на странице профиля должен быть его юзернейм")
    void userShouldHaveHisUsernameInProfile(@User(INVITATION_RECEIVED) UserJson user) {
        mainPage
                .clickProfileButton();
        profilePage
                .checkUsername(user.username());
    }
}
