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
    
    private static final String PR_OPEN_XPATH = "//a[contains(text(), '%s')]/ancestor::div[contains(@class, 'open')]";
    private static final String PR_CLOSED_XPATH = "//span[@data-status='pullClosed']";
    private static final String PR_IN_LIST_XPATH = "//a[contains(text(), '%s')]";
    private static final String PR_STATUS_XPATH = "//a[contains(text(), '%s')]/ancestor::div[contains(@class, 'Issue')]" +
            "//span[contains(@class, 'State')]";
    
    // ЭЛЕМЕНТЫ СТРАНИЦЫ 
    private final Link newPullRequestButton = Link.byHref("/Test7473/NewRepoPublic1/compare");
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

        // Ждем появления PR в списке
        Selenide.sleep(2000);

        // Ищем ссылку с текстом PR
        String xpath = "//a[contains(text(), '" + title + "')]";

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
