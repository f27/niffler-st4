package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static guru.qa.niffler.jupiter.annotation.User.UserType.INVITATION_SENT;
import static guru.qa.niffler.jupiter.annotation.User.UserType.WITH_FRIENDS;

public class UserWithFriendsTests extends BaseWebTest {

    private final WelcomePage welcomePage = new WelcomePage();
    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final FriendsPage friendsPage = new FriendsPage();

    @BeforeEach
    void doLoginAndOpenFriendsPage(@User(WITH_FRIENDS) UserJson user) {
        Selenide.open("http://127.0.0.1:3000");
        welcomePage
                .clickLoginButton();
        loginPage
                .setUsername(user.username())
                .setPassword(user.testData().password())
                .clickSignInButton();
        mainPage
                .clickFriendsButton();
    }

    @Test
    @DisplayName("У пользователя с друзьями в таблице с друзьями должен быть текст [You are friends]")
    void userWithFriendsShouldHaveYouAreFriendsTextInFriendsTable() {
        friendsPage.checkFriendsTableContainsYouAreFriendText();
    }

    @Test
    @DisplayName("У пользователя с друзьями в таблице с друзьями должна быть кнопка [Remove friend]")
    void userWithFriendsShouldHaveRemoveFriendButtonInFriendsTable() {
        friendsPage.checkFriendsTableContainsRemoveFriendButton();
    }

    @Test
    @DisplayName("У пользователя с друзьями в таблице с друзьями должен быть аватар друга")
    void userWithFriendsShouldHaveFriendsAvatarInFriendsTable() {
        friendsPage.checkFriendsTableContainsFriendsAvatar();
    }


    @Test
    @DisplayName("Пользователь с друзьями не должен видеть пользователя отправившего приглашение на странице с друзьми")
    void userWithFriendsShouldNotSeeInvitationSentUserInFriendsTable(@User(WITH_FRIENDS) UserJson userWithFriends,
                                                                     @User(INVITATION_SENT) UserJson userInvitationSent) {
        Assertions.assertNotEquals(null, userInvitationSent);
        Assertions.assertNotEquals(null, userWithFriends);
        Assertions.assertNotEquals(userWithFriends.username(), userInvitationSent.username());
        friendsPage.checkFriendsTableNotContainsFriend(userInvitationSent.username());
    }
}
