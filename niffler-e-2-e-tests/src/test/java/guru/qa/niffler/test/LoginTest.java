package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.db.model.*;
import guru.qa.niffler.db.repository.UserRepository;
import guru.qa.niffler.jupiter.annotation.DbUser;
import guru.qa.niffler.jupiter.extension.UserRepositoryExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@ExtendWith(UserRepositoryExtension.class)
public class LoginTest extends BaseWebTest {

  private UserRepository userRepository;

  private UserAuthEntity userAuth;
  private UserEntity user;


  @BeforeEach
  void createUser() {
    userAuth = new UserAuthEntity();
    userAuth.setUsername("valentin_4");
    userAuth.setPassword("12345");
    userAuth.setEnabled(true);
    userAuth.setAccountNonExpired(true);
    userAuth.setAccountNonLocked(true);
    userAuth.setCredentialsNonExpired(true);
    userAuth.setAuthorities(Arrays.stream(Authority.values())
        .map(e -> {
          AuthorityEntity ae = new AuthorityEntity();
          ae.setAuthority(e);
          return ae;
        }).toList()
    );

    user = new UserEntity();
    user.setUsername("valentin_4");
    user.setCurrency(CurrencyValues.RUB);
    userRepository.createInAuth(userAuth);
    userRepository.createInUserdata(user);
  }

  @AfterEach
  void removeUser() {
    userRepository.deleteInAuthById(userAuth.getId());
    userRepository.deleteInUserdataById(user.getId());
  }

  @DbUser()
  @Test
  void statisticShouldBeVisibleAfterLogin() {
    Selenide.open("http://127.0.0.1:3000/main");
    $("a[href*='redirect']").click();
    $("input[name='username']").setValue(userAuth.getUsername());
    $("input[name='password']").setValue(userAuth.getPassword());
    $("button[type='submit']").click();
    $(".main-content__section-stats").should(visible);
  }
}
