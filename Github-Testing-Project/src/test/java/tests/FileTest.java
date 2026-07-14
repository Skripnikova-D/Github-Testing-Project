package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CreateFilePage;
import pages.RepositoryPage;
import pages.modals.CommitModal;

public class FileTest extends BaseTest {

    @Test
    @DisplayName("Создание файла в репозитории")
    public void createFileTest() {
        String repoName = "NewRepoPublic1";
        String fileName = "NewFile.txt";
        String fileContent = "Hello, World!";

        loginWithValidUser();

        RepositoryPage repoPage = new RepositoryPage();
        repoPage.openRepository(repoName);

        CreateFilePage createFilePage = repoPage.clickAddFileButton()
                .selectCreateNewFileOption();

        createFilePage.setFileName(fileName)
                .setFileContent(fileContent);

        CommitModal commitModal = createFilePage.clickCommitChangesButton();
        RepositoryPage updatedRepoPage = commitModal.confirm();

        Assertions.assertTrue(updatedRepoPage.isFileExists(fileName),
                "Файл " + fileName + " должен существовать в репозитории");
    }
}