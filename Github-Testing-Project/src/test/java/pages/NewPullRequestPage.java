package pages;

import elements.Button;
import elements.Input;
import elements.Link;
import elements.SelectMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Страница создания нового Pull Request.
 */
public class NewPullRequestPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(NewPullRequestPage.class);

    // Элементы страницы
    private final Button createPullRequestButton = Button.byContainsText("Create pull request");
    private final Input prTitleInput = Input.byAriaLabel("Title");
    private final SelectMenu baseMenu = new SelectMenu("base");
    private final SelectMenu headMenu = new SelectMenu("head");

    /**
     * Выбирает base ветку
     */
    public NewPullRequestPage selectBaseBranch(String branchName) {
        logger.info("Выбор base ветки: {}", branchName);
        // Нажимаем на селектор base ветки
        Link.byContainsText("base:").click();
        baseMenu.selectBranch(branchName);
        return this;
    }

    /**
     * Выбирает compare ветку
     */
    public NewPullRequestPage selectCompareBranch(String branchName) {
        logger.info("Выбор compare ветки: {}", branchName);
        // Нажимаем на селектор compare ветки
        Link.byContainsText("compare:").click();
        headMenu.selectBranch(branchName);
        return this;
    }

    /**
     * Нажимает кнопку "Create pull request" (первый этап)
     */
    public NewPullRequestPage clickCreatePullRequestButton() {
        logger.info("Нажатие кнопки Create pull request (первый этап)");
        createPullRequestButton.click();
        return this;
    }

    /**
     * Устанавливает заголовок Pull Request
     */
    public NewPullRequestPage setPullRequestTitle(String title) {
        logger.info("Установка заголовка PR: {}", title);
        prTitleInput.setValue(title);
        return this;
    }

    /**
     * Подтверждает создание Pull Request
     */
    public PullRequestsPage confirmCreatePullRequest() {
        logger.info("Подтверждение создания Pull Request");
        createPullRequestButton.click();
        return new PullRequestsPage();
    }
}