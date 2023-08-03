import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestovRegimTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }
@Test
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = DataGenerator.getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldHave(text("Личный кабинет"));
    }
    @Test
    void shouldSuccessfulLoginIfNotRegisteredActiveUser(){
       var notRegisteredUser = DataGenerator.getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка!Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldSuccessfulLoginIfBlockerRegisteredActiveUser(){
        var blockerRegisteredUser = DataGenerator.getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockerRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockerRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка!Пользователь заблокирован")).shouldBe(visible);
    }
    @Test
    void shouldSuccessfulLoginIfNoPassword(){
        var registeredUser = DataGenerator.getRegisteredUser("active");
        var wrongPassword = DataGenerator.getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("[data-test-icd='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка!Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldSuccessfulLoginIfNoName(){
        var registeredUser = DataGenerator.getRegisteredUser("active");
        var wrongLogin = DataGenerator.getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка!Неверно указан логин или пароль")).shouldBe(visible);
    }
}
