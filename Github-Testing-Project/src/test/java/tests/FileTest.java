package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.RepositoryPage;
import pages.CreateFilePage;
import pages.FilePage;
import pages.CommitPage;

public class FileTest extends BaseTest {

    private static final String REPO_NAME = "NewRepoPublic1";
    private static final String FILE_NAME = "NewFile.txt";

    /**
     * Тест создания и удаления файла.
     */
    @Test
    @DisplayName("Создание и удаление файла в репозитории")
    public void createAndDeleteFileTest() {
        loginWithValidUser();

        // --- Создание файла ---
        MainPage mainPage = new MainPage();
        RepositoryPage repoPage = mainPage.openRepository(REPO_NAME);
        repoPage.clickAddFileButton();

        CreateFilePage createFilePage = repoPage.selectCreateNewFileOption();
        createFilePage.setFileName(FILE_NAME);
        createFilePage.setFileContent("Just Description");
        createFilePage.clickCommitChangesButton();

        RepositoryPage updatedRepoPage = createFilePage.confirmCommit();

        Assertions.assertTrue(updatedRepoPage.isFileExists(FILE_NAME),
                "Файл '" + FILE_NAME + "' должен отображаться в репозитории");

        // --- Удаление файла ---
        FilePage filePage = updatedRepoPage.clickOnFile(FILE_NAME);
        filePage.clickMoreOptions();

        CommitPage commitPage = filePage.selectDeleteFile();
        RepositoryPage finalRepoPage = commitPage.confirm();

        Assertions.assertFalse(finalRepoPage.isFileExists(FILE_NAME),
                "Файл '" + FILE_NAME + "' не должен отображаться в репозитории");
    }
}