package pages;

import elements.Button;
import elements.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница входа в GitHub.
 */
public class LoginPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    // Элементы страницы
    private final Input usernameInput = Input.byId("login_field");
    private final Input passwordInput = Input.byId("password");
    private final Button signInButton = Button.byContainsText("Sign in");

    /**
     * Выполняет вход в систему
     */
    public MainPage login(String username, String password) {
        logger.info("Вход с пользователем: {}", username);
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        signInButton.click();
        return new MainPage();
    }

    /**
     * Проверяет, отображается ли сообщение об ошибке
     */
    public boolean isErrorMessageDisplayed() {
        return $x("//div[@class='flash-error']").isDisplayed();
    }
}