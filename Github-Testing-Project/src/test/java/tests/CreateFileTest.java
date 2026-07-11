package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.NewRepositoryPage;
import pages.RepositoryPage;

/**
 * Тесты для операций с файлами в репозитории GitHub.
 */
public class CreateFileTest extends BaseTest {

    /**
     * Тест создания нового файла в репозитории.
     * Шаги:
     * 1. Авторизоваться под валидным пользователем
     * 2. Перейти в репозиторий
     * 3. Нажать кнопку "Add file"
     * 4. Выбрать "Create new file" из выпадающего списка
     * 5. Ввести имя файла "NewFile.txt"
     * 6. Ввести содержимое файла "Just Description"
     * 7. Нажать кнопку "Commit changes..."
     * 8. Подтвердить коммит (повторно нажать "Commit changes")
     * Ожидаемый результат:
     * - Файл "NewFile.txt" отображается в списке файлов репозитория
     */
    @Test
    @DisplayName("Создание нового файла в репозитории")
    public void createNewFileTest() {
        // Шаг 1: Авторизация
        loginWithValidUser();

        // Шаг 2: Перейти в репозиторий
        MainPage mainPage = new MainPage();
        RepositoryPage repoPage = mainPage.openRepository("NewRepoPublic1");

        // Шаг 3: Нажать "Add file"
        repoPage.clickAddFileButton();

        // Шаг 4: Выбрать "Create new file"
        repoPage.selectCreateNewFileOption();

        // Шаг 5: Ввести имя файла
        repoPage.setFileName("NewFile.txt");

        // Шаг 6: Ввести содержимое файла
        repoPage.setFileContent("Just Description");

        // Шаг 7: Нажать "Commit changes..."
        repoPage.clickCommitChangesButton();

        // Шаг 8: Подтвердить коммит
        repoPage.confirmCommit();

        // Проверка: файл появился в списке
        Assertions.assertTrue(repoPage.isFilePresent("NewFile.txt"),
                "Файл 'NewFile.txt' должен отображаться в репозитории");
    }
}