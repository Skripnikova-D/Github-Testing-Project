package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.NewPullRequestPage;
import pages.PullRequestsPage;
import pages.RepositoryPage;

public class PullRequestTests extends BaseTest {

    private static final String REPO_NAME = "NewRepoPublic1";
    private static final String BRANCH_NAME = "NewBranch";
    private static final String PR_TITLE = "NewPR";
    private static final String BASE_BRANCH = "main";

    @Test
    @DisplayName("Создание Pull Request между main и NewBranch")
    public void createPullRequestTest() {
        loginWithValidUser();

        MainPage mainPage = new MainPage();
        RepositoryPage repoPage = mainPage.openRepository(REPO_NAME);

        PullRequestsPage prPage = repoPage.goToPullRequestsTab();

        NewPullRequestPage newPrPage = prPage.clickNewPullRequestButton();

        newPrPage.selectBaseBranch(BASE_BRANCH)
                 .selectCompareBranch(BRANCH_NAME);

        newPrPage.clickCreatePullRequestButton();

        newPrPage.setPullRequestTitle(PR_TITLE);

        PullRequestsPage createdPrPage = newPrPage.confirmCreatePullRequest();

        // Проверка статуса
        Assertions.assertTrue(createdPrPage.isPullRequestOpen(PR_TITLE),
                "PR с названием '" + PR_TITLE + "' должен быть открыт");
        Assertions.assertTrue(createdPrPage.isPullRequestInList(PR_TITLE),
                "PR должен отображаться в списке Pull Requests");
    }

    @Test
    @DisplayName("Закрытие пулреквеста")
    public void closePullRequestTest() {
        loginWithValidUser();
        RepositoryPage repoPage = new RepositoryPage();
        repoPage.openRepository(REPO_NAME);

        PullRequestsPage prPage = repoPage.clickPullRequestsTab();

        prPage.clickPullRequest(PR_TITLE)
              .clickClosePullRequest();

        // Проверка статуса
        Assertions.assertTrue(prPage.isPullRequestClosed(PR_TITLE),
                "Pull request '" + PR_TITLE + "' должен иметь статус Closed");
        Assertions.assertEquals("Closed", prPage.getPullRequestStatus(PR_TITLE),
                "Статус пулреквеста должен быть 'Closed'");
    }
}