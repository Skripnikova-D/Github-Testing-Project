package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pages.*;

public class PullRequestTests extends BaseTest {

    private static final String REPO_NAME = "NewRepoPublic1";
    private static final String BRANCH_NAME = "NewBranch";
    private static final String PR_TITLE = "New branch";
    private static final String BASE_BRANCH = "main";
    private static final String FILE_NAME = "feature_file"+ RUN_ID+ ".txt" ;
    private static final String FILE_CONTENT = "This is a new feature!";

    @Test
    @Order(1)
    @DisplayName("Создание Pull Request между main и NewBranch")
    public void createPullRequestTest() {
        //loginWithValidUser();
        loginViaCookies();
        MainPage mainPage = new MainPage();
        RepositoryPage repoPage = mainPage.openRepository(REPO_NAME);

        BranchesPage branchesPage = repoPage.clickViewAllBranches();
        RepositoryPage branchRepoPage = branchesPage.clickBranch(BRANCH_NAME);

        CreateFilePage createFilePage = repoPage.clickAddFileButton()
                .selectCreateNewFileOption();
        createFilePage.setFileName(FILE_NAME)
                .setFileContent(FILE_CONTENT);
        CommitModal commitModal = createFilePage.clickCommitChangesButton();
        repoPage = commitModal.confirm();

        // Проверяем, что файл создан
        Assertions.assertTrue(repoPage.isFileExists(FILE_NAME),
                "Файл '" + FILE_NAME + "' должен быть создан в ветке " + BRANCH_NAME);

        // ---- 4. Создаём Pull Request ----
        PullRequestsPage prPage = repoPage.goToPullRequestsTab();

        NewPullRequestPage newPrPage = prPage.clickNewPullRequestButton();

        newPrPage.selectBaseBranch(BASE_BRANCH);
        newPrPage.selectCompareBranch(BRANCH_NAME);

        newPrPage.clickCreatePullRequestButton();

        PullRequestsPage createdPrPage = newPrPage.confirmCreatePullRequest();

        // ---- 5. Проверки ----
        Assertions.assertTrue(createdPrPage.isPullRequestInList(PR_TITLE),
                "PR с названием '" + PR_TITLE + "' должен быть создан");
    }

    @Test
    @Order(2)
    @DisplayName("Закрытие пулреквеста")
    public void closePullRequestTest() {
        //loginWithValidUser();
        loginViaCookies();
        RepositoryPage repoPage = RepositoryPage.openRepository(REPO_NAME);

        // Проверяем, что PR существует перед закрытием
        PullRequestsPage prPage = repoPage.clickPullRequestsTab();
        Assertions.assertTrue(prPage.isPullRequestInList(PR_TITLE),
                "PR с названием '" + PR_TITLE + "' должен существовать");

        prPage.clickPullRequest(PR_TITLE)
              .clickClosePullRequest();

        // Проверка статуса
        Assertions.assertTrue(prPage.isPullRequestClosed(PR_TITLE),
                "Pull request '" + PR_TITLE + "' должен иметь статус Closed");
        Assertions.assertEquals("Closed", prPage.getPullRequestStatus(PR_TITLE),
                "Статус пулреквеста должен быть 'Closed'");
    }
}