package pages;

import com.codeborne.selenide.Selenide;
import elements.Button;
import elements.Input;
import elements.BranchTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class BranchesPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(BranchesPage.class);
    private static final String BRANCH_XPATH = "//h2[contains(text(), 'Active branches')]/following-sibling::div//a[contains(., '%s')]";

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

    public BranchesPage findBranch(String branchName) {
        logger.info("Поиск ветки: {}", branchName);
        $x(String.format(BRANCH_XPATH, branchName)).shouldBe(visible, Duration.ofSeconds(5));
        return this;
    }

    public BranchesPage deleteBranch(String branchName) {
        logger.info("Удаление ветки: {}", branchName);
        BranchTable.deleteBranch("Active branches", branchName);
        logger.info("Обновление страницы для отображения актуального списка веток");
        Selenide.refresh();
        return this;
    }

    public boolean isBranchExists(String branchName) {
        logger.info("Проверка существования ветки: {}", branchName);
        try {
            // Ждем до 10 секунд. Если ветка есть - вернет true.
            $x(String.format(BRANCH_XPATH, branchName)).shouldBe(visible, Duration.ofSeconds(10));
            return true;
        } catch (Exception e) {
            // Если ветки нет, ловим исключение и возвращаем false (это ожидаемое поведение для теста удаления)
            logger.info("Ветка '{}' не найдена (это нормально для теста удаления)", branchName);
            return false;
        }
    }
}