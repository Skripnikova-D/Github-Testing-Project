package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.RepositoryPage;
import pages.BranchesPage;


public class CreateBranchTests extends BaseTest {

    /**
     * Тест создания новой ветки в репозитории.
     * Шаги:
     * 1. Авторизоваться под валидным пользователем
     * 2. Перейти в репозиторий "test-public-repo"
     * 3. Перейти на вкладку "Code" (если не активна по умолчанию)
     * 4. Нажать на переключатель веток
     * 5. Выбрать "View all branches"
     * 6. Нажать кнопку "New branch"
     * 7. Ввести название новой ветки "NewBranch"
     * 8. Нажать "Create new branch"
     * Ожидаемый результат:
     * - Ветка "NewBranch" создана
     * - Ветка "NewBranch" отображается в списке веток
     */
    @Test
    @DisplayName("Создание новой ветки в репозитории")
    public void createNewBranchTest() {
        // Шаг 1: Авторизация
        loginWithValidUser();

        // Шаг 2: Перейти в репозиторий
        MainPage mainPage = new MainPage();
        RepositoryPage repoPage = mainPage.openRepository("NewRepoPublic1");

        // Шаг 3: Перейти на вкладку "Code"
        repoPage.goToCodeTab();

        // Шаг 4: Нажать на переключатель веток
        repoPage.clickBranchSelector();

        // Шаг 5: Выбрать "View all branches"
        BranchesPage branchesPage = repoPage.clickViewAllBranches();

        // Шаг 6: Нажать "New branch"
        branchesPage.clickNewBranchButton();

        // Шаг 7: Ввести название ветки
        branchesPage.setBranchName("NewBranch");

        // Шаг 8: Нажать "Create new branch"
        repoPage = branchesPage.clickCreateNewBranch();

        // Проверки
        Assertions.assertTrue(repoPage.isBranchPresent("NewBranch"),
                "Ветка 'NewBranch' должна быть создана");
        Assertions.assertEquals("NewBranch", repoPage.getCurrentBranchName(),
                "Текущая активная ветка должна быть 'NewBranch'");
    }
}