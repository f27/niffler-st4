package guru.qa.niffler.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement signInButton = $("button[type='submit']");

    @Step("Заполнить поле username [{username}]")
    public LoginPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    @Step("Заполнить поле password [{password}]")
    public LoginPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Нажать кнопку [Sign in]")
    public <PageObjectClass> PageObjectClass clickSignInButton(Class<PageObjectClass> pageObjectClassClass) {
        signInButton.click();
        return Selenide.page(pageObjectClassClass);
    }
}
