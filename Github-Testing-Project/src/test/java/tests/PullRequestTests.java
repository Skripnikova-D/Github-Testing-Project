package tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import pages.*;

import static com.codeborne.selenide.Selenide.sleep;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

        PullRequestsPage prPage = repoPage.goToPullRequestsTab();

        NewPullRequestPage newPrPage = prPage.clickNewPullRequestButton();

        newPrPage.selectBaseBranch(BASE_BRANCH);
        newPrPage.selectCompareBranch(BRANCH_NAME);

        newPrPage.clickCreatePullRequestButton();

        PullRequestsPage createdPrPage = newPrPage.confirmCreatePullRequest();

        Assertions.assertTrue(createdPrPage.isPullRequestInList(PR_TITLE),
                "PR с названием '" + PR_TITLE + "' должен быть создан");
    }
    @Test
    @Order(2)
    @DisplayName("Закрытие пулреквеста")
    public void closePullRequestTest() {
        loginViaCookies();
        RepositoryPage repoPage = RepositoryPage.openRepository(REPO_NAME);
        PullRequestsPage prPage = repoPage.goToPullRequestsTab();

        // Проверяем, что PR существует перед закрытием
        Assertions.assertTrue(prPage.isPullRequestInList(PR_TITLE),
                "PR с названием '" + PR_TITLE + "' должен существовать");

        PullRequestPage prDetailPage = prPage.clickPullRequest(PR_TITLE);

        prDetailPage=prDetailPage.clickClosePullRequest();
        sleep(10000);

        Assertions.assertTrue(prDetailPage.isPullRequestClosed(PR_TITLE),
                "Pull request '" + PR_TITLE + "' должен иметь статус Closed");
    }
}