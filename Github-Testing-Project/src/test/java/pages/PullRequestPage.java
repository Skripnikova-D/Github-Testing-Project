package pages;

import elements.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница конкретного Pull Request.
 */
public class PullRequestPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(PullRequestPage.class);

    // XPath для бейджа статуса (Open / Closed / Merged);
    private static final String PR_CLOSED_XPATH = "//span[@data-status='pullClosed']";
    private final Button closePullRequestButton = Button.byContainsText("Close pull request");

    public PullRequestPage clickClosePullRequest() {
        logger.info("Нажатие кнопки Close pull request");
        closePullRequestButton.click();
        return this;
    }

    /**
     * Проверяет, закрыт ли Pull Request
     */
    public boolean isPullRequestClosed(String prName) {
        logger.info("Проверка, что Pull Request {} закрыт", prName);
        return $x(String.format(PR_CLOSED_XPATH)).isDisplayed();
    }
}