package tests;

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
        loginWithValidUser();

        MainPage mainPage = new MainPage();
        RepositoryPage repoPage = mainPage.openRepository(REPO_NAME);

        repoPage.goToCodeTab();

        BranchesPage branchesPage = repoPage.clickViewAllBranches();

        branchesPage.clickNewBranchButton()
                    .setBranchName(BRANCH_NAME)
                    .clickCreateNewBranch();

        // Проверка: ветка должна существовать в списке
        // Возвращаемся на страницу веток для проверки
        branchesPage = repoPage.clickViewAllBranches();
        Assertions.assertTrue(branchesPage.isBranchExists(BRANCH_NAME),
                "Ветка '" + BRANCH_NAME + "' должна быть создана и отображаться в списке");
    }

    @Test
    @DisplayName("Удаление ветки")
    public void deleteBranchTest() {
        loginWithValidUser();
        RepositoryPage repoPage = new RepositoryPage();
        repoPage.openRepository(REPO_NAME);

        BranchesPage branchesPage = repoPage.clickViewAllBranches();

        branchesPage.findBranch(BRANCH_NAME)
                    .deleteBranch(BRANCH_NAME);

        Assertions.assertFalse(branchesPage.isBranchExists(BRANCH_NAME),
                "Ветка '" + BRANCH_NAME + "' не должна отображаться в списке");

        // Дополнительная проверка: ветка не должна быть доступна в выпадающем списке на странице репозитория
        repoPage = new RepositoryPage();
        repoPage.openRepository(REPO_NAME);
        Assertions.assertFalse(repoPage.isBranchAvailableInDropdown(BRANCH_NAME),
                "Ветка '" + BRANCH_NAME + "' не должна быть доступна для выбора в выпадающем списке");
    }
}