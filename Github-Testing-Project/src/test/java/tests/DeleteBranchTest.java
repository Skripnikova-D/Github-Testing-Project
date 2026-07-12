package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.BranchesPage;
import pages.RepositoryPage;

public class DeleteBranchTest extends BaseTest {

    /**
     * Тест удаления ветки.
     * 1. Перейти во вкладку Code, на странице репозитория
     * 2. Нажать на выбор ветки "NewBranch"
     * 3. Нажать View all branches
     * 4. Найти в списке нужную ветку
     * 5. Нажать Delete branch
     * 6. Проверить, что ветки больше нет в списке
     * 
     * Ожидаемый результат:
     * - Ветка удалена и не отображается в списке выбора веток
     */
    @Test
    @DisplayName("Удаление ветки")
    public void deleteBranchTest() {
        String repoName = "NewRepoPrivate1";
        String branchName = "NewBranch";

        // Шаг 1: Авторизация и открытие репозитория
        loginWithValidUser();
        RepositoryPage repoPage = new RepositoryPage();
        repoPage.openRepository(repoName);

        // Шаг 2: Нажать на выбор ветки "NewBranch"
        repoPage.selectBranch(branchName);

        // Шаг 3: Нажать View all branches
        BranchesPage branchesPage = repoPage.clickViewAllBranches();

        // Шаг 4: Найти в списке нужную ветку
        branchesPage.findBranch(branchName);

        // Шаг 5: Нажать Delete branch
        branchesPage.deleteBranch(branchName);

        // Шаг 6: Проверить, что ветки больше нет в списке
        Assertions.assertFalse(branchesPage.isBranchExists(branchName), 
                "Ветка " + branchName + " не должна отображаться в списке");
        
        // Возвращаемся на страницу репозитория и проверяем, что ветки нет в выпадающем списке
        repoPage = new RepositoryPage();
        Assertions.assertFalse(repoPage.isBranchAvailableInDropdown(branchName),
                "Ветка " + branchName + " не должна быть доступна для выбора");
    }
}