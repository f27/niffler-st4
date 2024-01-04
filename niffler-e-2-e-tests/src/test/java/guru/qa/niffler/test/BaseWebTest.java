package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.page.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({BrowserExtension.class})
public abstract class BaseWebTest {
    protected final AllPeoplePage allPeoplePage = new AllPeoplePage();
    protected final FriendsPage friendsPage = new FriendsPage();
    protected final LoginPage loginPage = new LoginPage();
    protected final MainPage mainPage = new MainPage();
    protected final ProfilePage profilePage = new ProfilePage();
    protected final WelcomePage welcomePage = new WelcomePage();

    static {
        Configuration.browserSize = "1980x1024";
    }
}
