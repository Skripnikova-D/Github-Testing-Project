package tests;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import pages.LoginPage;

/**
 * Базовый класс для всех тестовых классов проекта.
 * Содержит общую логику: настройку и закрытие браузера, методы аутентификации,
 * а также вспомогательные методы для получения информации о текущей странице.
 */
public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    protected static final String VALID_USERNAME = System.getProperty("github.username", "Test7473");
    protected static final String VALID_PASSWORD = System.getProperty("github.password", "Test737475");
    protected static final String BASE_URL = "https://github.com";

    @BeforeEach
    public void setUp() {
        logger.info("Настройка браузера перед тестом...");
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        Configuration.headless = false;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        Configuration.browserCapabilities = options;

        openBaseUrl();
        logger.info("Браузер настроен. BASE_URL: {}", BASE_URL);
    }

    @AfterEach
    public void tearDown() {
        logger.info("Закрытие браузера...");
        Selenide.closeWebDriver();
    }

    protected void openBaseUrl() {
        Selenide.open(BASE_URL);
    }

    /**
     * Выполняет вход в систему.
     * Из-за WebAuthn-диалога Windows Security, который блокирует автоматический ввод,
     * логин/пароль вводятся ВРУЧНУЮ пользователем — тест ждёт, пока URL страницы
     * перестанет содержать "/login" (то есть пока не произойдёт успешный вход).
     */
    protected void loginWithValidUser() {
        logger.info("Открываю страницу логина...");
        Selenide.open(BASE_URL + "/login");

        logger.info("Ожидание РУЧНОГО входа пользователем (введите логин/пароль и нажмите Sign in)...");

        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(60));
        wait.until(driver -> !driver.getCurrentUrl().contains("/login"));

        logger.info("Вход выполнен, продолжаем тест...");
    }

    protected String getCurrentUrl() {
        return WebDriverRunner.url();
    }

    protected String getPageTitle() {
        return Selenide.title();
    }
}