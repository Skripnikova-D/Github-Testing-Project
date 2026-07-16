package tests;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;


/**
 * Базовый класс для всех тестовых классов проекта.
 * Содержит общую логику: настройку и закрытие браузера, методы аутентификации,
 * а также вспомогательные методы для получения информации о текущей странице.
 */
public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    protected static final String VALID_USERNAME = System.getProperty("github.username", "Test7473");

    protected static final String USER_SESSION="sfArI_67467KovXNACD_t_tyVgWLwj1SjhyY1ZSRcdH1iof_";
    protected static final String HOST_SESSION="sfArI_67467KovXNACD_t_tyVgWLwj1SjhyY1ZSRcdH1iof_";
    protected static final String GH_SESS = "RDTz2pUviAnssEaqGAgGkLc4yezLimcaDpGWTuQMF2UqGvynZpCsZFzQB1R2HOta3R%2FkOIj" +
            "W%2FfWeSiv3NG%2FScDr9zx1TG%2Bi4bY%2Fx8z2vo8%2FdCZY%2BIfEuRPRpqQ7vvurV6%2FHYuVJ%2Bj91jUroBPLwmacM%2FImE00%2B" +
            "neB0m0gAkzMrYdCOjm1mj3zG1ADwSZZleaFk56u89VEdvPA3t5ZHFCI2SXkx0PAGTvAuLSziUgtBo%2BCTr9ApiD6aAs3e2ruukMlgBe3DOh" +
            "oKLb2O9w9iq327ata2htjoWWUPr789lWPN8ogykVLv0HOktTPrqYv1oveXbvIkzwtdneKTeABeq1zC7TCkCrN1Jk289Mt75dxC6sA%2BfC8u" +
            "2IS2O8Lb%2BepHEQDzuXlKCoczHVAPSXNMHo8W1wN2i3P1fNo%2FaUa%2FaoS1f05YxQRbHsHDUaJa7LuB72FxRuludXpyaLQnN4A9UrXFqQ" +
            "Ni%2BY0vyWu5Xqd%2FnaJRtKAOmO73btS0FDP%2BzlzflQtNz6RP6mBS7Wengv%2BTLoA5bgdYxik6l2sdHNeCgYCOkA6daeUrjU2WP0ufXVD" +
            "%2BzLBFgEyt9h1m6V5Gvj5Mq%2BNenNW4qSUMnfbbuDvHD%2BuksvDphA%2BQw3OQwfrqdi56fduI3EbjdyuF%2FT1aoRN0YbMEePoXEiYIAgT" +
            "gMPumLcLHJCtu3qRPp2HDeLz3QqMErfACjbQYze1OGM1P8vAx6gYF0o66oacOAJY3V9uH5RReRukycWpvgnfl7uO3NIGrDf3%2BHStE7Pew6RM" +
            "jnnY0rGf2glFZXJWTnSlc9xoTWX5MaB6dH%2BtiRSC77qbOEol3tD30B3VjTg452ih4UTkvC%2Fqq2nS%2FXsUPWQ--chaulUOJgsYQ%2B0YD-" +
            "-n5M4i8EktQlPfYcOj5X1ow%3D%3D";
    protected static final String SAVED_SESSION = "sfArI_67467KovXNACD_t_tyVgWLwj1SjhyY1ZSRcdH1iof_";

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

}