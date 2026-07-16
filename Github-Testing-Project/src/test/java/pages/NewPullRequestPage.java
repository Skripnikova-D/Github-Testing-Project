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

public class NewPullRequestPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(NewPullRequestPage.class);

    private final Button createPullRequestButton = Button.byContainsText("Create pull request");
    private final Input prTitleInput = Input.byAriaLabel("Title");

    public NewPullRequestPage selectBaseBranch(String branchName) {
        logger.info("Выбор base ветки: {}", branchName);

        $x("//summary[contains(., 'base:')]")
                .shouldBe(Condition.clickable, Duration.ofSeconds(10))
                .click();

        Selenide.sleep(500);

        $x("//span[contains(@class, 'css-truncate') and contains(@class, 'css-truncate-overflow') and text()='" + branchName + "']")
                .shouldBe(Condition.visible, Duration.ofSeconds(5))
                .click();

        return this;
    }

    public NewPullRequestPage selectCompareBranch(String branchName) {
        logger.info("Выбор compare ветки: {}", branchName);

        $x("//summary[contains(., 'compare:')]")
                .shouldBe(Condition.clickable, Duration.ofSeconds(10))
                .click();

        Selenide.sleep(500);

        $x("//span[contains(@class, 'css-truncate') and text()='" + branchName + "']")
                .shouldBe(Condition.visible, Duration.ofSeconds(5))
                .click();

        return this;
    }

    public NewPullRequestPage clickCreatePullRequestButton() {
        logger.info("Нажатие кнопки Create pull request (первый этап)");

        SelenideElement button = $x("//button[contains(., 'Create pull request')]");
        button.shouldBe(Condition.visible, Duration.ofSeconds(10));
        Selenide.executeJavaScript("arguments[0].click();", button);

        Selenide.Wait().withTimeout(Duration.ofSeconds(10))
                .until(driver -> WebDriverRunner.url().contains("/pull/new/") ||
                        WebDriverRunner.url().contains("/compare/"));

        logger.info("Текущий URL после клика: {}", WebDriverRunner.url());
        return this;
    }

    public NewPullRequestPage setPullRequestTitle(String title) {
        logger.info("Установка заголовка PR: {}", title);
        $x("//input[@name='pull_request[title]']")
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .setValue(title);
        return this;
    }

    public PullRequestsPage confirmCreatePullRequest() {
        logger.info("Подтверждение создания Pull Request");

        // Ищем ВИДИМУЮ кнопку Create pull request
        SelenideElement createButton = $x("//button[contains(., 'Create pull request') and not(contains(@style, 'display: none')) and not(@hidden)]");

        if (!createButton.isDisplayed()) {
            // Если не нашли - ищем все кнопки и берем видимую
            var buttons = $$x("//button[contains(., 'Create pull request')]");
            for (SelenideElement btn : buttons) {
                if (btn.isDisplayed()) {
                    createButton = btn;
                    break;
                }
            }
        }

        createButton.scrollIntoView(true);
        Selenide.sleep(300);
        createButton.click();

        Selenide.Wait().withTimeout(Duration.ofSeconds(20))
                .until(driver -> {
                    String url = WebDriverRunner.url();
                    return url.contains("/pull/") || url.contains("/pulls");
                });
        Selenide.sleep(2000);

        // Кликаем на вкладку Pull requests через ссылку
        $x("//a[contains(@href, '/pulls') and contains(., 'Pull requests')]")
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .click();

        Selenide.Wait().withTimeout(Duration.ofSeconds(15))
                .until(driver -> WebDriverRunner.url().contains("/pulls"));
        Selenide.refresh();
        logger.info("Текущий URL после создания PR: {}", WebDriverRunner.url());

        return new PullRequestsPage();
    }
}