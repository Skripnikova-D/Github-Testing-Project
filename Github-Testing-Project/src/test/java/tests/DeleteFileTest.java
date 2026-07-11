package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.FilePage;
import pages.RepositoryPage;

public class DeleteFileTest extends BaseTest {

    /**
     * Тест удаления файла.
     * 1. Нажать на удаляемый файл "NewFile.txt"
     * 2. Нажать на кнопку в виде 3 точек
     * 3. Из выпадающего списка выбрать Delete file
     * 4. Нажать кнопку Commit changes
     * 5. Нажать кнопку Commit Changes (еще раз)
     * 6. Проверить, что файла больше нет в репозитории
     * 
     * Ожидаемый результат:
     * - Файл удалён, не отображается в списке
     */
    @Test
    @DisplayName("Удаление файла")
    public void deleteFileTest() {
        String repoName = "NewRepoPrivate1";
        String fileName = "NewFile.txt";

        // Шаг 1: Авторизация
        loginWithValidUser();
        
        // Открыть репозиторий
        RepositoryPage repoPage = new RepositoryPage();
        repoPage.openRepository(repoName);

        // Шаг 1: Нажать на удаляемый файл "NewFile.txt"
        FilePage filePage = repoPage.clickOnFile(fileName);

        // Шаг 2: Нажать на кнопку в виде 3 точек
        filePage.clickThreeDotsMenu();

        // Шаг 3: Из выпадающего списка выбрать Delete file
        filePage.selectDeleteFile();

        // Шаг 4: Нажать кнопку Commit changes
        filePage.clickCommitChanges();

        // Шаг 5: Нажать кнопку Commit Changes (еще раз)
        filePage.confirmCommitChanges();

        // Шаг 6: Проверить, что файла больше нет в репозитории
        Assertions.assertFalse(repoPage.isFileExists(fileName), 
                "Файл " + fileName + " не должен отображаться в репозитории");
    }
}