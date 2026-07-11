package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.PullRequestsPage;
import pages.RepositoryPage;

public class ClosePullRequestTest extends BaseTest {

    /**
     * Тест закрытия пулреквеста.
     * 1. Перейти во вкладку Pull requests, на странице репозитория
     * 2. Нажать на нужный pull request "NewPR"
     * 3. Нажать Close pull request
     * 4. Проверить статус
     * 
     * Ожидаемый результат:
     * - pull request приобрел статус Closed
     */
    @Test
    @DisplayName("Закрытие пулреквеста")
    public void closePullRequestTest() {
        String repoName = "NewRepoPrivate1";
        String prName = "NewPR";

        // Шаг 1: Авторизация и открытие репозитория
        loginWithValidUser();
        RepositoryPage repoPage = new RepositoryPage();
        repoPage.openRepository(repoName);

        // Шаг 1: Перейти во вкладку Pull requests
        PullRequestsPage prPage = repoPage.clickPullRequestsTab();

        // Шаг 2: Нажать на нужный pull request "NewPR"
        prPage.clickPullRequest(prName);

        // Шаг 3: Нажать Close pull request
        prPage.clickClosePullRequest();

        // Шаг 4: Проверить статус
        Assertions.assertTrue(prPage.isPullRequestClosed(prName), 
                "Pull request " + prName + " должен иметь статус Closed");
        
        // Дополнительная проверка: статус должен быть виден на странице
        Assertions.assertEquals("Closed", prPage.getPullRequestStatus(prName),
                "Статус пулреквеста должен быть 'Closed'");
    }
}