package pages;

import elements.Button;
import elements.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Страница создания нового репозитория.
 */
public class NewRepositoryPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(NewRepositoryPage.class);

    // Элементы страницы
    private final Input repoNameInput = Input.byName("repository[name]");
    private final Button publicRadio = Button.byId("repository_visibility_public");
    private final Button privateRadio = Button.byId("repository_visibility_private");
    private final Button createRepoButton = Button.byContainsText("Create repository");

    /**
     * Устанавливает имя репозитория
     */
    public NewRepositoryPage setRepositoryName(String name) {
        logger.info("Установка имени репозитория: {}", name);
        repoNameInput.setValue(name);
        return this;
    }

    /**
     * Выбирает публичную видимость
     */
    public NewRepositoryPage selectPublicVisibility() {
        logger.info("Выбор публичной видимости");
        publicRadio.click();
        return this;
    }

    /**
     * Выбирает приватную видимость
     */
    public NewRepositoryPage selectPrivateVisibility() {
        logger.info("Выбор приватной видимости");
        privateRadio.click();
        return this;
    }

    /**
     * Нажимает кнопку создания репозитория
     */
    public RepositoryPage clickCreateRepositoryButton() {
        logger.info("Нажатие кнопки создания репозитория");
        createRepoButton.click();
        return new RepositoryPage();
    }
}