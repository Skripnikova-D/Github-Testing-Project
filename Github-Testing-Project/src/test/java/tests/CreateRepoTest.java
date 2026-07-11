package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.NewRepositoryPage;
import pages.RepositoryPage;


public class CreateRepoTest extends BaseTest {

    /**
     * Тест создания публичного репозитория.
     * Шаги:
     * 1. Авторизоваться под валидным пользователем
     * 2. Нажать кнопку "New" для создания нового репозитория
     * 3. Ввести имя репозитория "test-public-repo"
     * 4. Выбрать радиокнопку "Public"
     * 5. Нажать кнопку "Create repository"
     * 
     * Ожидаемый результат:
     * - Репозиторий создан (открылась страница репозитория)
     * - Репо отображается как публичный
     * 
     * ПОМЕТКА: временные классы Pages и их методы
     */
    @Test
    @DisplayName("Создание публичного репозитория")
    public void createPublicRepositoryTest() {
        // Шаг 1: Авторизация
        loginWithValidUser();

        // Шаг 2: Нажать кнопку "New"
        MainPage mainPage = new MainPage();
        NewRepositoryPage newRepoPage = mainPage.clickNewButton();

        // Шаг 3: Ввести имя репозитория
        newRepoPage.setRepositoryName("NewRepoPublic1");

        // Шаг 4: Выбрать Public
        newRepoPage.selectPublicVisibility();

        // Шаг 5: Нажать Create repository
        RepositoryPage repoPage = newRepoPage.clickCreateRepositoryButton();

        // Проверки
        Assertions.assertTrue(repoPage.isLoaded(), "Страница репозитория должна загрузиться");
        Assertions.assertEquals("test-public-repo", repoPage.getRepositoryName(),
                "Имя репозитория должно совпадать с введённым");
    }
}