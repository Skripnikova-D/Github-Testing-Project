package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.NewRepositoryPage;
import pages.RepositoryPage;

public class CreatePrivateRepositoryTest extends BaseTest {

    /**
     * Тест создания приватного репозитория.
     * 1. Нажать на кнопку New на главной странице
     * 2. Заполнить поле Repository name "NewRepoPrivate1"
     * 3. Нажать на кнопку с выбором видимости
     * 4. Нажать на поле Private
     * 5. Нажать на кнопку создать репозиторий
     * 6. Проверить, что репозиторий появился в списке и что на странице написано Private
     * 
     * Ожидаемый результат:
     * - Репозиторий создан (открылась страница репозитория)
     * - Репо отображается как приватный
     */
    @Test
    @DisplayName("Создание приватного репозитория")
    public void createPrivateRepositoryTest() {
        String repoName = "NewRepoPrivate1";

        // Шаг 1: Авторизация
        loginWithValidUser();

        // Шаг 2: Нажать кнопку "New"
        MainPage mainPage = new MainPage();
        NewRepositoryPage newRepoPage = mainPage.clickNewButton();

        // Шаг 3: Ввести имя репозитория
        newRepoPage.setRepositoryName(repoName);

        // Шаг 4: Выбрать Private
        newRepoPage.selectPrivateVisibility();

        // Шаг 5: Нажать Create repository
        RepositoryPage repoPage = newRepoPage.clickCreateRepositoryButton();

        // Проверки
        Assertions.assertTrue(repoPage.isLoaded(), "Страница репозитория должна загрузиться");
        Assertions.assertEquals(repoName, repoPage.getRepositoryName(),
                "Имя репозитория должно совпадать с введённым");
        Assertions.assertTrue(repoPage.isPrivate(), "Репозиторий должен быть приватным");
        Assertions.assertTrue(repoPage.isRepositoryInList(repoName), 
                "Репозиторий должен появиться в списке репозиториев");
    }
}