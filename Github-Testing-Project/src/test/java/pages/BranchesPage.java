package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
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
    private static final String BRANCH_XPATH = "//h2[contains(text(), 'Active branches')]/following-sibling::div//a[contains(., '%s')]";
    private static final String DELETE_BUTTON_XPATH = "//h2[contains(text(), 'Active branches')]/following-sibling::div//tr[contains(., '%s')]//span[contains(text(), 'Delete branch')]/preceding-sibling::button";

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

    public static void deleteBranch(String tableName, String branchName) {
        logger.info("Удаление ветки из таблицы {}: {}", tableName, branchName);

        BranchTable table = new BranchTable(tableName);
        SelenideElement row = table.getRow(branchName);

        row.hover();
        Selenide.sleep(300);

        SelenideElement deleteButton = row.$x(".//button[.//*[local-name()='svg' and contains(@class, 'octicon-trash')]]");
        deleteButton.shouldBe(Condition.visible, Duration.ofSeconds(5));

        Selenide.actions()
                .moveToElement(deleteButton)
                .click()
                .perform();
        logger.info("Кнопка удаления ветки нажата через actions");

        Selenide.sleep(500);
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