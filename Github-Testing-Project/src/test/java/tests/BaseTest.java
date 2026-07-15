package tests;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.TimeoutException;
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

    protected static final String VALID_USERNAME = System.getProperty("github.username", "NikLun4");
    //protected static final String VALID_PASSWORD = System.getProperty("github.password", "xxVVii333!!!");

    protected static final String USER_SESSION="FfmVz90yVHuhYWcq4SYyTzHGMrA1-igs6QT14bmX0KYUit9p";
    protected static final String HOST_SESSION="FfmVz90yVHuhYWcq4SYyTzHGMrA1-igs6QT14bmX0KYUit9p";
    protected static final String GH_SESS = "Huf6KaAJJ15VeTy4GZrVHW3T4ofMrA96Q8YeBLiTAxAZYPJfLPHtjII53Ap85X5NAes2zInYurfvjjcKe6wwR1gAFUwlzWAotBHRNfTiscTgYLEVXDV1OqIxlDR1FR1S1CBLlLLVzRRg6EMqTWqkMbdebngYqmXeOppm8BY1fw%2BsAMFhNFestgDRdjeF0qyvB9Tl3sPGzucpGhw871HMxj%2FRbkEZfKMiRYHxNEEUPOiqml%2Ft41NV20JUn8c15TLz0mVriEU8GOdrxoGtku9MIx8oXeFZ95OTHXQEpbrw4okfGMqXu41MhmDPh%2BlK0nZ6me06pm3XPnLJ9Ghqv6XQ1a%2Bjs%2F%2BfB%2FASLajHh4hNVfkHrui6uULznTLEAUipo3NDvLfunRC%2FiVP%2FmTD6le3o7Cd0QrBAHuxEAtsvKzDfT4Sqn0%2FEs9f%2BtfkhhseOX9OyKgKuJHNJZICbEHm9LpRdihD7REYaAqb6l%2BQVyJMFedSXQhHdUtE2yXvye4ypAh4DkrzCfXtfuQq%2FXiPTPsm9DUWqORlJ3gGH%2BPIqQoDynV3ZCpzbmOHOskIM4D8%3D--7wJHK1wEhfRwrtzJ--sqLa%2Bq1lAoe9g%2F260aQIog%3D%3D";
    protected static final String SAVED_SESSION = "69304500%3AFfmVz90yVHuhYWcq4SYyTzHGMrA1-igs6QT14bmX0KYUit9p";

    protected static final String BASE_URL = "https://github.com";
    protected static final String RUN_ID = String.valueOf(System.currentTimeMillis());

    @BeforeEach
    public void setUp() {
        logger.info("Настройка браузера перед тестом...");
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 15000;
        Configuration.headless = false;
        // Увеличиваем таймаут загрузки страницы, так как GitHub в WSL2 может грузиться медленно
        Configuration.pageLoadTimeout = 60000;

        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion("128");

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage"); // Решает проблему нехватки памяти и зависаний
        options.addArguments("--disable-gpu"); // Помогает при проблемах с рендерером в WSL

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        Configuration.browserCapabilities = options;

        openBaseUrl();
        loginViaCookies();
        logger.info("Браузер настроен. BASE_URL: {}", BASE_URL);
    }

    @AfterEach
    public void tearDown() {
        logger.info("Закрытие браузера...");
        Selenide.closeWebDriver();
    }

    protected void openBaseUrl() {
        openWithRetry(BASE_URL);
    }

    /**
     * Открывает URL с одной повторной попыткой при таймауте рендерера.
     * В WSL2 первая команда к свежесозданному Chrome иногда не укладывается
     * в дефолтный внутренний таймаут (~30 сек) — вторая попытка почти всегда проходит.
     */
    private void openWithRetry(String url) {
        try {
            Selenide.open(url);
        } catch (TimeoutException e) {
            logger.warn("Таймаут при открытии {}, повторная попытка...", url);
            Selenide.open(url);
        }
    }

    /**
     * Обновляет страницу с одной повторной попыткой при таймауте рендерера.
     */
    private void refreshWithRetry() {
        try {
            Selenide.refresh();
        } catch (TimeoutException e) {
            logger.warn("Таймаут при refresh, повторная попытка...");
            Selenide.refresh();
        }
    }

    /**
     * Выполняет вход в систему.
     * Из-за WebAuthn-диалога Windows Security, который блокирует автоматический ввод,
     * логин/пароль вводятся ВРУЧНУЮ пользователем — тест ждёт, пока URL страницы
     * перестанет содержать "/login" (то есть пока не произойдёт успешный вход).
     */
    protected void loginWithValidUser() {
        logger.info("Открываю страницу логина...");
        openWithRetry(BASE_URL + "/login");

        logger.info("Ожидание РУЧНОГО входа пользователем (введите логин/пароль и нажмите Sign in)...");

        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(60));
        wait.until(driver -> !driver.getCurrentUrl().contains("/login"));

        logger.info("Вход выполнен, продолжаем тест...");
    }

    protected void loginViaCookies() {
        logger.info("Авторизация через cookies...");
        openWithRetry(BASE_URL);

        addSessionCookie("user_session", System.getProperty("gh.cookie.user_session", USER_SESSION));
        addSessionCookie("__Host-user_session_same_site", System.getProperty("gh.cookie.host_session", HOST_SESSION));
        addSessionCookie("logged_in", "yes");
        addSessionCookie("dotcom_user", VALID_USERNAME);
        addSessionCookie("_gh_sess", System.getProperty("gh.cookie.gh_sess", GH_SESS));
        addSessionCookie("saved_user_sessions", System.getProperty("gh.cookie.saved_sessions", SAVED_SESSION));

        refreshWithRetry();
        logger.info("Cookies установлены, страница обновлена");

        if (getCurrentUrl().contains("/login")) {
            throw new IllegalStateException("Авторизация через cookies не сработала — сессия истекла или значения неверны");
        }
        logger.info("Авторизация через cookies прошла успешно");
    }

    private void addSessionCookie(String name, String value) {
        if (value == null || value.isEmpty()) {
            logger.warn("Пропущено значение для cookie: {}", name);
            return;
        }
        WebDriverRunner.getWebDriver().manage().addCookie(
                new org.openqa.selenium.Cookie(name, value)
        );
    }

    protected String getCurrentUrl() {
        return WebDriverRunner.url();
    }

    protected String getPageTitle() {
        return Selenide.title();
    }
}