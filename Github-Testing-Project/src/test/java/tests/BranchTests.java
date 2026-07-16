package tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pages.BranchesPage;
import pages.MainPage;
import pages.RepositoryPage;

public class BranchTests extends BaseTest {
    private static final String REPO_NAME = "NewRepoPublic1";
    private static final String BRANCH_NAME = "NewBranch" + RUN_ID;

    @Test
    @Order(1)
    @DisplayName("Создание новой ветки в репозитории")
    public void createNewBranchTest() {
        loginViaCookies();

        MainPage mainPage = new MainPage();
        RepositoryPage repoPage = mainPage.openRepository(REPO_NAME);
        repoPage.goToCodeTab();

        BranchesPage branchesPage = repoPage.clickViewAllBranches();

        BranchesPage updatedBranchesPage = branchesPage.clickNewBranchButton()
                .setBranchName(BRANCH_NAME)
                .clickCreateNewBranch();
        Assertions.assertTrue(updatedBranchesPage.isBranchExists(BRANCH_NAME),
                "Ветка '" + BRANCH_NAME + "' должна быть создана и отображаться в списке");
    }

    @Test
    @Order(2)
    @DisplayName("Удаление ветки")
    public void deleteBranchTest() {
        loginViaCookies();
        RepositoryPage repoPage = RepositoryPage.openRepository(REPO_NAME);

        BranchesPage branchesPage = repoPage.clickViewAllBranches();
        branchesPage.deleteBranch(BRANCH_NAME);
        Selenide.refresh();
        Assertions.assertFalse(branchesPage.isBranchExists(BRANCH_NAME),
                "Ветка '" + BRANCH_NAME + "' не должна отображаться в списке");
    }
}