package tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.BranchesPage;
import pages.MainPage;
import pages.RepositoryPage;

public class BranchTests extends BaseTest {
    private static final String REPO_NAME = "NewRepoPublic1";
    private static final String BRANCH_NAME = "NewBranch";

    @Test
    @DisplayName("Создание новой ветки в репозитории")
    public void createNewBranchTest() {
        loginViaCookies();

        MainPage mainPage = new MainPage();
        RepositoryPage repoPage = mainPage.openRepository(REPO_NAME);
        repoPage.goToCodeTab();

        // 1. Переходим на страницу веток
        BranchesPage branchesPage = repoPage.clickViewAllBranches();

        // 2. Создаем ветку. GitHub делает редирект, поэтому метод возвращает НОВЫЙ объект RepositoryPage
        BranchesPage updatedBranchesPage = branchesPage.clickNewBranchButton()
                .setBranchName(BRANCH_NAME)
                .clickCreateNewBranch();

        // 3. ВАЖНО: Используем обновленный объект updatedRepoPage для перехода на страницу веток.
        // Это гарантирует, что мы работаем с актуальным URL и состоянием браузера.
        //BranchesPage updatedBranchesPage = updatedRepoPage.clickViewAllBranches();

        // 4. Проверяем наличие ветки (метод isBranchExists ниже содержит try-catch и ожидание 10 сек)
        Assertions.assertTrue(updatedBranchesPage.isBranchExists(BRANCH_NAME),
                "Ветка '" + BRANCH_NAME + "' должна быть создана и отображаться в списке");
    }

    @Test
    @DisplayName("Удаление ветки")
    public void deleteBranchTest() {
        loginViaCookies();
        RepositoryPage repoPage = RepositoryPage.openRepository(REPO_NAME);

        BranchesPage branchesPage = repoPage.clickViewAllBranches();
        branchesPage.deleteBranch("Active branches", BRANCH_NAME);
        Selenide.refresh();
        Selenide.refresh();


        Assertions.assertFalse(branchesPage.isBranchExists(BRANCH_NAME),
                "Ветка '" + BRANCH_NAME + "' не должна отображаться в списке");
    }
}