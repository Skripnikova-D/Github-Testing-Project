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
    
    private static final String PR_OPEN_XPATH = "//a[contains(text(), '%s')]/ancestor::div[contains(@class, 'open')]";
    private static final String PR_CLOSED_XPATH = "//a[contains(text(), '%s')]/ancestor::div[contains(@class, 'closed')]";
    private static final String PR_IN_LIST_XPATH = "//a[contains(text(), '%s')]";
    private static final String PR_STATUS_XPATH = "//a[contains(text(), '%s')]/ancestor::div[contains(@class, 'Issue')]//span[contains(@class, 'State')]";
    
    // ЭЛЕМЕНТЫ СТРАНИЦЫ 
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
        return $x(String.format(PR_OPEN_XPATH, prName)).isDisplayed();
    }

    /**
     * Проверяет, закрыт ли Pull Request
     */
    public boolean isPullRequestClosed(String prName) {
        logger.info("Проверка, что Pull Request {} закрыт", prName);
        return $x(String.format(PR_CLOSED_XPATH, prName)).isDisplayed();
    }

    /**
     * Проверяет, отображается ли Pull Request в списке
     */
    public boolean isPullRequestInList(String prName) {
        logger.info("Проверка, что Pull Request {} отображается в списке", prName);
        return $x(String.format(PR_IN_LIST_XPATH, prName)).isDisplayed();
    }

    /**
     * Получает статус Pull Request
     */
    public String getPullRequestStatus(String prName) {
        logger.info("Получение статуса Pull Request: {}", prName);
        return $x(String.format(PR_STATUS_XPATH, prName)).getText();
    }
}
