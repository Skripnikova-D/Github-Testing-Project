package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ex.ElementNotFound;
import elements.Button;
import elements.Input;
import elements.BranchTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class BranchesPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(BranchesPage.class);
    private static final String BRANCH_XPATH = "//h2[contains(text(), 'Active branches')]/following-sibling::div" +
            "//a[contains(., '%s')]";
    private static final String DELETE_BUTTON_XPATH = "//h2[contains(text(), 'Active branches')]/following-sibling::div" +
            "//tr[contains(., '%s')]//span[contains(text(), 'Delete branch')]/preceding-sibling::button";

    private final Button newBranchButton = Button.byContainsText("New branch");
    private final Input branchNameInput = Input.byLabel("New branch name");
    private final Button createBranchButton = Button.byContainsText("Create new branch");

    public BranchesPage clickNewBranchButton() {
        logger.info("Нажатие кнопки New branch");
        newBranchButton.click();
        return this;
    }

    public BranchesPage setBranchName(String branchName) {
        logger.info("Установка имени ветки: {}", branchName);
        branchNameInput.setValue(branchName);
        return this;
    }

    public BranchesPage clickCreateNewBranch() {
        logger.info("Нажатие кнопки Create new branch");
        createBranchButton.click();
        return this;
    }


    public BranchesPage deleteBranch(String branchName) {
        logger.info("Удаление ветки: {}", branchName);
        BranchTable.deleteBranch("Active branches", branchName);
        String deleteButtonXpath = String.format(DELETE_BUTTON_XPATH, branchName);
        $x(deleteButtonXpath).shouldNotBe(visible, Duration.ofSeconds(10));

        return this;
    }

    /**
     * Кликает на конкретную ветку в списке.
     * Возвращает RepositoryPage, так как клик по имени ветки открывает код этой ветки.
     */
    public RepositoryPage clickBranch(String branchName) {
        logger.info("Клик на ветку в списке: {}", branchName);
        $x(String.format(BRANCH_XPATH, branchName)).click();
        //BranchTable.clickBranchLink("Active branches", branchName);
        return new RepositoryPage();
    }


    public boolean isBranchExists(String branchName) {
        logger.info("Проверка существования ветки: {}", branchName);
        try {
            // Ждем до 10 секунд. Если ветка есть - вернет true.
            $x(String.format(BRANCH_XPATH, branchName)).shouldBe(visible, Duration.ofSeconds(10));
            return true;
        } catch (Throwable e) {
            // Если ветки нет, ловим исключение и возвращаем false (это ожидаемое поведение для теста удаления)
            logger.info("Ветка '{}' не найдена (это нормально для теста удаления)", branchName);
            return false;
        }
    }
}