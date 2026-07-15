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

    protected static final String VALID_USERNAME = System.getProperty("github.username", "Test7473");
    //protected static final String VALID_PASSWORD = System.getProperty("github.password", "Test737475");

    protected static final String USER_SESSION="fjv2Xx1bvlsC96e_sKONqxczHzYDiEed6NJvbJzcZkNdpRUK";
    protected static final String HOST_SESSION="fjv2Xx1bvlsC96e_sKONqxczHzYDiEed6NJvbJzcZkNdpRUK";
    protected static final String GH_SESS = "aZOeKT2Quc80CmZO2TSAQNC1n2ZhqQv9uYuoKfyRAJDTXJU%2BDn2t4rhCKhhKvbKtvkM6pS" +
            "6jSmG%2FoLLHO16aFovgdBJUJRR0J9PdUYD6nD2A0Jy3%2FmrAyofPonDGHT17pW97fheiDvFAMBQaOHfF4itK1IuWOzRiP70To%2B14Gu" +
            "ZWNCGdAOTs3HqpkhePx1krxjgNNekCRqQduZ95PIndPazf%2Bm27cPkTgzhCMHEkKkNsL1HwDz6CHp4kjpb6wwN%2FdyqlaCl1MUNiVG71" +
            "GQah9YaBL7BlUvOr62voTHPxsEHwaU97BSq4GLM86k8rNKC1uG0T9rTNqfBZ5mmzPFXSCxSYKmJCoz6aSBIMNDLJDZVHyGZDP8NG61mJRn" +
            "DaJYXR%2BZGYvdwsz%2BZ02I3xVFqzZcGZfAN1rgUZBOYihNmrU4WseuLFX8Lz9sgWxPI6%2FYCY42AULUVHOFrWYpIFRvLapoVlEe6X7f" +
            "KxRZPX3PbYtJYslxtci8FEOXSKbRAzoBuuQwSefhQ99hKlgBYwSF3t%2FC5s%2BbC5D7JdLQoJgXuw%2FN4coAKQbTtoY0p77LOsUKt5X8g" +
            "QJ6%2Fjvn5Th1F3gDGAgDzJqCEPqDd6GXyfmCgEmulJGTVrm%2BpSngknT6V4tKHpAgEdsI6Agl3YNjvVJErh8Pp15SE6Ol%2BIvX%2BQzM" +
            "VatnzD1%2B9uevw%2FyQ8aHLNZtoB0nHo%2BeNFRlUmG1Ejuj7fDXmp69VDd2jf8oZElNTo2aH5bjDoqe9wzNwxrv4r1%2BS08L5S0G0bSS" +
            "zmw388kWH0DShpIH11D40KmvGksQlqTOMfiWrpQtTf%2BtolcICRlKQOFmldth0Go32Z%2Fs7awwdRBBLkcrydmlLVv--F6H8fD66wTTWCo" +
            "ip--CB4A3%2B%2FhWWPlaWLDjkaY2A%3D%3D";
    protected static final String SAVED_SESSION = "305031555%3Afjv2Xx1bvlsC96e_sKONqxczHzYDiEed6NJvbJzcZkNdpRUK";

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