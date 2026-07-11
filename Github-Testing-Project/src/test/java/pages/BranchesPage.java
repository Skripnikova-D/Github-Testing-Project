package pages;

import elements.Button;
import elements.Input;
import elements.Link;
import elements.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница со списком веток репозитория.
 */
public class BranchesPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(BranchesPage.class);

    // Элементы страницы
    private final Button newBranchButton = Button.byContainsText("New branch");
    private final Input branchNameInput = Input.byAriaLabel("Branch name");
    private final Button createBranchButton = Button.byContainsText("Create new branch");

    /**
     * Нажимает кнопку "New branch"
     */
    public BranchesPage clickNewBranchButton() {
        logger.info("Нажатие кнопки New branch");
        newBranchButton.click();
        return this;
    }

    /**
     * Устанавливает имя ветки
     */
    public BranchesPage setBranchName(String branchName) {
        logger.info("Установка имени ветки: {}", branchName);
        branchNameInput.setValue(branchName);
        return this;
    }

    /**
     * Нажимает кнопку "Create new branch"
     */
    public RepositoryPage clickCreateNewBranch() {
        logger.info("Нажатие кнопки Create new branch");
        createBranchButton.click();
        return new RepositoryPage();
    }

    /**
     * Ищет ветку в списке
     */
    public BranchesPage findBranch(String branchName) {
        logger.info("Поиск ветки: {}", branchName);
        $x("//a[contains(text(), '" + branchName + "')]").isDisplayed();
        return this;
    }

    /**
     * Удаляет ветку
     */
    public BranchesPage deleteBranch(String branchName) {
        logger.info("Удаление ветки: {}", branchName);
        Table.deleteBranch("Active branches", branchName);
        return this;
    }

    /**
     * Проверяет, существует ли ветка в списке
     */
    public boolean isBranchExists(String branchName) {
        logger.info("Проверка существования ветки: {}", branchName);
        return $x("//a[contains(text(), '" + branchName + "')]").isDisplayed();
    }
}