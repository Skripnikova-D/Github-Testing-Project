package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CreateFilePage;
import pages.RepositoryPage;
import pages.FilePage;
import pages.CommitPage;
import pages.modals.CommitModal;

public class FileTest extends BaseTest {

    private static final String REPO_NAME = "NewRepoPublic1";
    private static final String FILE_NAME = "NewFile.txt";
    private static final String FILE_CONTENT = "Hello, World!";

    @Test
    @DisplayName("Создание файла в репозитории")
    public void createFileTest() {
        loginWithValidUser();

        RepositoryPage repoPage = new RepositoryPage();
        repoPage.openRepository(REPO_NAME);

        CreateFilePage createFilePage = repoPage.clickAddFileButton()
                .selectCreateNewFileOption();

        createFilePage.setFileName(FILE_NAME)
                .setFileContent(FILE_CONTENT);

        CommitModal commitModal = createFilePage.clickCommitChangesButton();
        RepositoryPage updatedRepoPage = commitModal.confirm();

        Assertions.assertTrue(updatedRepoPage.isFileExists(FILE_NAME),
                "Файл '" + FILE_NAME + "' должен быть создан");
    }

    @Test
    @DisplayName("Удаление файла из репозитория")
    public void deleteFileTest() {
        loginWithValidUser();

        RepositoryPage repoPage = new RepositoryPage();
        repoPage.openRepository(REPO_NAME);

        // Проверяем, что файл существует перед удалением
        Assertions.assertTrue(repoPage.isFileExists(FILE_NAME),
                "Файл '" + FILE_NAME + "' должен существовать перед удалением");

        FilePage filePage = repoPage.clickOnFile(FILE_NAME);
        
        CommitPage commitPage = filePage.clickMoreOptions()
                                        .selectDeleteFile();
        
        RepositoryPage updatedRepoPage = commitPage.confirm();

        Assertions.assertFalse(updatedRepoPage.isFileExists(FILE_NAME),
                "Файл '" + FILE_NAME + "' должен быть удалён");
    }
}