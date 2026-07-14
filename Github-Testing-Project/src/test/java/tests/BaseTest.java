package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    /**
     * Имя пользователя GitHub для тестов.
     * Значение по умолчанию: {@code your_github_username}.
     */
    protected static final String VALID_USERNAME = System.getProperty("github.username", "Test7473");

    /**
     * Пароль пользователя GitHub для тестов.
     * Значение по умолчанию: {@code your_github_password}.
     */
    protected static final String VALID_PASSWORD = System.getProperty("github.password", "Test737475");

    /**
     * Базовый URL тестируемого приложения.
     */
    protected static final String BASE_URL = "https://github.com";

    /**
     * Метод, выполняющийся перед каждым тестом.
     * Настраивает браузер (Chrome), устанавливает размер окна, таймауты
     * и открывает базовый URL приложения.
     */
    @BeforeEach
    public void setUp() {
        logger.info("Настройка браузера перед тестом...");
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        Configuration.headless = false;
        openBaseUrl();
        logger.info("Браузер настроен. BASE_URL: {}", BASE_URL);
    }

    /**
     * Метод, выполняющийся после каждого теста.
     * Закрывает браузер и освобождает ресурсы WebDriver.
     */
    @AfterEach
    public void tearDown() {
        logger.info("Закрытие браузера...");
        Selenide.closeWebDriver();
    }

    /**
     * Открывает базовый URL приложения ({@value #BASE_URL}).
     */
    protected void openBaseUrl() {
        Selenide.open(BASE_URL);
    }

    /**
     * Выполняет аутентификацию с валидными учётными данными
     */
    protected void loginWithValidUser() {
        logger.info("Вход с пользователем: {}", VALID_USERNAME);
        
        // Открываем страницу логина
        Selenide.open(BASE_URL + "/login");
        
        // Используем существующий LoginPage
        LoginPage loginPage = new LoginPage();
        loginPage.login(VALID_USERNAME, VALID_PASSWORD);
        
        // Ждём загрузки главной страницы
        Selenide.sleep(1000);
        logger.info("Авторизация выполнена успешно");
    }

    protected void login(String username, String password) {
        logger.info("Вход с пользователем: {}", username);
        Selenide.open(BASE_URL + "/login");
        LoginPage loginPage = new LoginPage();
        loginPage.login(username, password);
        Selenide.sleep(1000);
    }

    /**
     * Возвращает текущий URL открытой страницы.
     * @return строка с полным URL
     */
    protected String getCurrentUrl() {
        return WebDriverRunner.url();
    }

    /**
     * Возвращает заголовок (title) текущей страницы.
     * @return строка с заголовком страницы
     */
    protected String getPageTitle() {
        return Selenide.title();
    }
}
