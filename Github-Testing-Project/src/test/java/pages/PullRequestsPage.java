package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import elements.Button;
import elements.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница со списком Pull Requests.
 */
public class PullRequestsPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(PullRequestsPage.class);
    private static final int SLEEP_TIME = 2000;

    private static final String PR_OPEN_XPATH_TEMPLATE = "//a[contains(text(), '%s')]/ancestor::div[contains(@class, 'open')]";
    private static final String PR_CLOSED_XPATH = "//span[@data-status='pullClosed']";
    private static final String PR_IN_LIST_XPATH_TEMPLATE = "//a[contains(text(), '%s')]";
    private static final String PR_STATUS_XPATH_TEMPLATE = "//a[contains(text(), '%s')]/ancestor::div[contains(@class, 'Issue')]" +
            "//span[contains(@class, 'State')]";

    private static final String NEW_PULL_REQUEST_HREF = "/Test7473/NewRepoPublic1/compare";
    private static final String CLOSE_PULL_REQUEST_BUTTON_TEXT = "Close pull request";

    private final Link newPullRequestButton = Link.byHref(NEW_PULL_REQUEST_HREF);
    private final Button closePullRequestButton = Button.byContainsText(CLOSE_PULL_REQUEST_BUTTON_TEXT);

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
    public PullRequestPage clickPullRequest(String prName) {
        logger.info("Клик на Pull Request: {}", prName);
        Link.byContainsText(prName).click();
        return new PullRequestPage();
    }


    /**
     * Проверяет, отображается ли Pull Request в списке
     */
    public boolean isPullRequestInList(String title) {
        logger.info("Проверка наличия PR в списке: {}", title);

        Selenide.sleep(SLEEP_TIME);

        String xpath = String.format(PR_IN_LIST_XPATH_TEMPLATE, title);

        try {
            $x(xpath).shouldBe(Condition.visible, Duration.ofSeconds(10));
            logger.info("PR '{}' найден", title);
            return true;
        } catch (Exception e) {
            logger.warn("PR '{}' не найден", title);
            return false;
        }
    }
}