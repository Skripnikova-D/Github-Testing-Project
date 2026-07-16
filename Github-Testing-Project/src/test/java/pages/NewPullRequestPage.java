package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import elements.Button;
import elements.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница создания нового Pull Request.
 */
public class NewPullRequestPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(NewPullRequestPage.class);
    private static final int SLEEP_TIME = 100;

    private static final String BASE_BRANCH_SUMMARY_XPATH = "//summary[contains(., 'base:')]";
    private static final String COMPARE_BRANCH_SUMMARY_XPATH = "//summary[contains(., 'compare:')]";
    private static final String BRANCH_ITEM_XPATH_TEMPLATE = "//span[contains(@class, 'css-truncate') and contains(@class, 'css-truncate-overflow') and text()='%s']";
    private static final String COMPARE_BRANCH_ITEM_XPATH_TEMPLATE = "//span[contains(@class, 'css-truncate') and text()='%s']";

    private static final String CREATE_PR_BUTTON_CONTAINS_XPATH = "//button[contains(., 'Create pull request')]";
    private static final String CREATE_PR_BUTTON_VISIBLE_XPATH = "//button[contains(., 'Create pull request') and not(contains(@style, 'display: none')) and not(@hidden)]";

    private static final String PR_TITLE_INPUT_XPATH = "//input[@name='pull_request[title]']";

    private static final String PULL_REQUESTS_LINK_XPATH = "//a[contains(@href, '/pulls') and contains(., 'Pull requests')]";

    private final Button createPullRequestButton = Button.byContainsText("Create pull request");
    private final Input prTitleInput = Input.byAriaLabel("Title");

    /**
     * Выбирает base ветку для Pull Request.
     */
    public NewPullRequestPage selectBaseBranch(String branchName) {
        logger.info("Выбор base ветки: {}", branchName);

        $x(BASE_BRANCH_SUMMARY_XPATH)
                .shouldBe(Condition.clickable, Duration.ofSeconds(10))
                .click();

        Selenide.sleep(5 * SLEEP_TIME);

        $x(String.format(BRANCH_ITEM_XPATH_TEMPLATE, branchName))
                .shouldBe(Condition.visible, Duration.ofSeconds(5))
                .click();

        return this;
    }

    /**
     * Выбирает compare ветку для Pull Request.
     */
    public NewPullRequestPage selectCompareBranch(String branchName) {
        logger.info("Выбор compare ветки: {}", branchName);

        $x(COMPARE_BRANCH_SUMMARY_XPATH)
                .shouldBe(Condition.clickable, Duration.ofSeconds(10))
                .click();

        Selenide.sleep(5 * SLEEP_TIME);

        $x(String.format(COMPARE_BRANCH_ITEM_XPATH_TEMPLATE, branchName))
                .shouldBe(Condition.visible, Duration.ofSeconds(5))
                .click();

        return this;
    }

    /**
     * Нажимает кнопку Create pull request (первый этап создания PR).
     */
    public NewPullRequestPage clickCreatePullRequestButton() {
        logger.info("Нажатие кнопки Create pull request (первый этап)");

        SelenideElement button = $x(CREATE_PR_BUTTON_CONTAINS_XPATH);
        button.shouldBe(Condition.visible, Duration.ofSeconds(10));
        Selenide.executeJavaScript("arguments[0].click();", button);

        Selenide.Wait().withTimeout(Duration.ofSeconds(10))
                .until(driver -> WebDriverRunner.url().contains("/pull/new/") ||
                        WebDriverRunner.url().contains("/compare/"));

        logger.info("Текущий URL после клика: {}", WebDriverRunner.url());
        return this;
    }

    /**
     * Устанавливает заголовок Pull Request.
     */
    public NewPullRequestPage setPullRequestTitle(String title) {
        logger.info("Установка заголовка PR: {}", title);
        $x(PR_TITLE_INPUT_XPATH)
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .setValue(title);
        return this;
    }

    /**
     * Подтверждает создание Pull Request.
     * Возвращает PullRequestsPage, так как после создания открывается страница со списком PR.
     */
    public PullRequestsPage confirmCreatePullRequest() {
        logger.info("Подтверждение создания Pull Request");

        SelenideElement createButton = $x(CREATE_PR_BUTTON_VISIBLE_XPATH);

        if (!createButton.isDisplayed()) {
            var buttons = $$x(CREATE_PR_BUTTON_CONTAINS_XPATH);
            for (SelenideElement btn : buttons) {
                if (btn.isDisplayed()) {
                    createButton = btn;
                    break;
                }
            }
        }

        createButton.scrollIntoView(true);
        Selenide.sleep(3 * SLEEP_TIME);
        createButton.click();

        Selenide.Wait().withTimeout(Duration.ofSeconds(20))
                .until(driver -> {
                    String url = WebDriverRunner.url();
                    return url.contains("/pull/") || url.contains("/pulls");
                });
        Selenide.sleep(20 * SLEEP_TIME);

        $x(PULL_REQUESTS_LINK_XPATH)
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .click();

        Selenide.Wait().withTimeout(Duration.ofSeconds(15))
                .until(driver -> WebDriverRunner.url().contains("/pulls"));
        Selenide.refresh();
        logger.info("Текущий URL после создания PR: {}", WebDriverRunner.url());

        return new PullRequestsPage();
    }
}