package pages;


import elements.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Главная страница GitHub после авторизации.
 */
public class MainPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(MainPage.class);

    // Элементы страницы
    private final Link newButton = Link.byHrefWithLocation("/new","left");

    /**
     * Нажимает кнопку "New" для создания нового репозитория
     */
    public NewRepositoryPage clickNewButton() {
        logger.info("Нажатие кнопки 'New' на главной странице");
        newButton.click();
        return new NewRepositoryPage();
    }

    /**
     * Открывает репозиторий по имени
     */
    public RepositoryPage openRepository(String repoName) {
        logger.info("Открытие репозитория: {}", repoName);
        Link repoLink = Link.byContainsText(repoName);
        repoLink.click();
        return new RepositoryPage();
    }
}