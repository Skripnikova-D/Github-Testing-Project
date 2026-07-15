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

    protected static final String USER_SESSION="c_gUpg7z_VtsHfLVKMiPvynx6KYrgdR-qVTQOGF8pFpVcOsS";
    protected static final String HOST_SESSION="c_gUpg7z_VtsHfLVKMiPvynx6KYrgdR-qVTQOGF8pFpVcOsS";
    protected static final String GH_SESS = "fO0nYjQR95rBfrLWujv5gnx%2FySl4wu6utxnfieU8fiSl1MBBuripeks7S3f0pdXscIyJtNq1" +
            "TQbqEbr6FBC%2Fn4d15j8ZVHbOD8TPRUD6cViBQ8gMNqsMPnHeRabTWuv94shXcgy3Ko1XDOKkP8xgVAC1KSm9FkUeFdFb0glmYdHf%2F0" +
            "VC%2FVLrRsxJXpuMXSZ8nj%2B0yuTnezh1OXouIYwlbKgqegChAk6k4yWkpmaRxWE9GvuQ2pZOdQNUZ7a3ON26FtCsBAQXeet8O19ftrUb" +
            "7UArHAJMCIhlLTn%2FaYaAZxqX3UZHoE8KFoCzgisbTxJor2nVzy1KLnllSW5vPmIPPuU9g8zB8%2FzYo%2BpMVfg%2Fh5pUyhJKOHBFMx" +
            "vcqskERr3QNnDBvL2pxeAfDLu8KmZHxfmRwEv7yfHXM%2FSw7Pnw5gDCyn2XxM8ifFMfdz2esNmyfH7A6r5i%2B5bp3%2BqGnO%2F%2FPTXA" +
            "82hGp2iAoiy4d4tm3RYUnwEsH5A40S4DBIjTAVsnPXUqRSmUkWO6Lcer4V2Yk6sHJraq34wuo%2FFs8e%2Bbg1tmABwAd9SKfby%2FadOEK84" +
            "tObVlmbC2mVU2JQt0aBkF3ZIsJva6XNz1NDzihLnGTvM6i2lcm9FfiwmRaacng2E1%2BZb%2FYX564S9iOi6fYiZoEBknPicti2m0Wncvsr9" +
            "dQK5d1huGgHZoNn5M482cwgQnBURsuFW1q755KpnKWD1ISp8SHCH1cQ%2FaUNX8wjDZuVar%2BItQW0sKhzCbDo7VQb8XJ50TzJ3e5uPqrgR" +
            "JtgbEenVo995uucWBARiB5qH0DENC1DlqQQbiqEP3jFFUF1ivNjkzABsi6wff9uhnanJlTALkcetzEkmb%2FTa2s%2BINZ0g6rqAKOyKtPQ%" +
            "3D%3D--KCZCBWSHqFio4qtE--D%2BmHD%2B2ttDLGLFON1DUolQ%3D%3D";
    protected static final String SAVED_SESSION = "305031555%3Ac_gUpg7z_VtsHfLVKMiPvynx6KYrgdR-qVTQOGF8pFpVcOsS";

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