package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.NewRepositoryPage;
import pages.RepositoryPage;

public class RepoTest extends BaseTest {

    /**
     * Создаёт репозиторий и возвращает страницу репозитория.
     */
    private RepositoryPage createRepository(String repoName, boolean isPrivate) {
        MainPage mainPage = new MainPage();
        NewRepositoryPage newRepoPage = mainPage.clickNewButton();

        newRepoPage.setRepositoryName(repoName);
        newRepoPage.selectVisibility();
        if (isPrivate) {
            newRepoPage.selectPrivateVisibility();
        } else {
            newRepoPage.selectPublicVisibility();
        }

        RepositoryPage repoPage = newRepoPage.clickCreateRepositoryButton();

        Assertions.assertTrue(repoPage.isLoaded(), "Страница репозитория должна загрузиться");
        Assertions.assertEquals(repoName, repoPage.getRepositoryName(),
                "Имя репозитория должно совпадать с введённым");

        return repoPage;
    }

    @Test
    @DisplayName("Создание публичного репозитория")
    public void createPublicRepositoryTest() {
        createRepository("NewRepoPublic-" + RUN_ID, false);
    }

    @Test
    @DisplayName("Создание приватного репозитория")
    public void createPrivateRepositoryTest() {
        String repoName = "NewRepoPrivate-" + RUN_ID;

        RepositoryPage repoPage = createRepository(repoName, true);

        Assertions.assertTrue(repoPage.isPrivate(), "Репозиторий должен быть приватным");
        Assertions.assertTrue(repoPage.isRepositoryInList(repoName),
                "Репозиторий должен появиться в списке репозиториев");
    }
}