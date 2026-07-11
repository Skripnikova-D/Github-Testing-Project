package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.RepositoryPage;
import pages.PullRequestsPage;
import pages.NewPullRequestPage;


public class CreatePRTests extends BaseTest {

    /**
     * Тест создания Pull Request между ветками.

     * Шаги:
     * 1. Авторизоваться под валидным пользователем
     * 2. Перейти в репозиторий "NewRepoPublic1"
     * 3. Перейти во вкладку "Pull requests"
     * 4. Нажать кнопку "New pull request"
     * 5. Выбрать base ветку "main"
     * 6. Выбрать compare ветку "NewBranch"
     * 7. Нажать "Create pull request"
     * 8. Ввести название PR "NewPR"
     * 9. Нажать "Create pull request" (подтверждение)
     * Ожидаемый результат:
     * - PR создан и открыт
     * - PR отображается в списке Pull Requests
     */
    @Test
    @DisplayName("Создание Pull Request между main и NewBranch")
    public void createPullRequestTest() {
        // Шаг 1: Авторизация
        loginWithValidUser();

        // Шаг 2: Перейти в репозиторий
        MainPage mainPage = new MainPage();
        RepositoryPage repoPage = mainPage.openRepository("NewRepoPublic1");

        // Шаг 3: Перейти во вкладку "Pull requests"
        PullRequestsPage prPage = repoPage.goToPullRequestsTab();

        // Шаг 4: Нажать "New pull request"
        NewPullRequestPage newPrPage = prPage.clickNewPullRequestButton();

        // Шаг 5: Выбрать base ветку "main"
        newPrPage.selectBaseBranch("main");

        // Шаг 6: Выбрать compare ветку "NewBranch"
        newPrPage.selectCompareBranch("NewBranch");

        // Шаг 7: Нажать "Create pull request"
        newPrPage.clickCreatePullRequestButton();

        // Шаг 8: Ввести название PR
        newPrPage.setPullRequestTitle("NewPR");

        // Шаг 9: Подтвердить создание PR
        PullRequestsPage createdPrPage = newPrPage.confirmCreatePullRequest();

        // Проверки
        Assertions.assertTrue(createdPrPage.isPullRequestOpen("NewPR"),
                "PR с названием 'NewPR' должен быть открыт");
        Assertions.assertTrue(createdPrPage.isPullRequestInList("NewPR"),
                "PR должен отображаться в списке Pull Requests");
    }
}