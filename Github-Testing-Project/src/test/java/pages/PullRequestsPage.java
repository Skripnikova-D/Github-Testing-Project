package pages;

import elements.Button;
import elements.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница со списком Pull Requests.
 */
public class PullRequestsPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(PullRequestsPage.class);

    // Элементы страницы
    private final Button newPullRequestButton = Button.byContainsText("New pull request");
    private final Button closePullRequestButton = Button.byContainsText("Close pull request");

    /**
     * Нажимает кнопку "New pull request"
     */
    public NewPullRequestPage clickNewPullRequestButton() {
        logger.info("Нажатие кнопки New pull request");
        newPullRequestButton.click();
        return new NewPullRequestPage();
    }

    /**
     * Кликает на Pull Request по имени
     */
    public PullRequestsPage clickPullRequest(String prName) {
        logger.info("Клик на Pull Request: {}", prName);
        Link.byContainsText(prName).click();
        return this;
    }

    /**
     * Нажимает кнопку "Close pull request"
     */
    public PullRequestsPage clickClosePullRequest() {
        logger.info("Нажатие кнопки Close pull request");
        closePullRequestButton.click();
        return this;
    }

    /**
     * Проверяет, открыт ли Pull Request
     */
    public boolean isPullRequestOpen(String prName) {
        logger.info("Проверка, что Pull Request {} открыт", prName);
        return $x("//a[contains(text(), '" + prName + "')]/ancestor::div[contains(@class, 'open')]").isDisplayed();
    }

    /**
     * Проверяет, закрыт ли Pull Request
     */
    public boolean isPullRequestClosed(String prName) {
        logger.info("Проверка, что Pull Request {} закрыт", prName);
        return $x("//a[contains(text(), '" + prName + "')]/ancestor::div[contains(@class, 'closed')]").isDisplayed();
    }

    /**
     * Проверяет, отображается ли Pull Request в списке
     */
    public boolean isPullRequestInList(String prName) {
        logger.info("Проверка, что Pull Request {} отображается в списке", prName);
        return $x("//a[contains(text(), '" + prName + "')]").isDisplayed();
    }

    /**
     * Получает статус Pull Request
     */
    public String getPullRequestStatus(String prName) {
        logger.info("Получение статуса Pull Request: {}", prName);
        return $x("//a[contains(text(), '" + prName + "')]/ancestor::div[contains(@class, 'Issue')]//span[contains(@class, 'State')]")
                .getText();
    }
}