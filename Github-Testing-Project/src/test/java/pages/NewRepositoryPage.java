package pages;

import elements.Button;
import elements.DropdownItem;
import elements.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Страница создания нового репозитория.
 */
public class NewRepositoryPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(NewRepositoryPage.class);

    private final Input repoNameInput = Input.byId("repository-name-input");
    private final Button visibilityRepoButton = Button.byId("visibility-anchor-button");
    private final DropdownItem publicRadio = DropdownItem.byMenuAndText("menu", "Public");
    private final DropdownItem privateRadio = DropdownItem.byMenuAndText("menu", "Private");
    private final Button createRepoButton = Button.byContainsText("Create repository");

    /**
     * Устанавливает имя репозитория
     */
    public NewRepositoryPage setRepositoryName(String name) {
        logger.info("Установка имени репозитория: {}", name);
        repoNameInput.setValue(name);
        return this;
    }

    public NewRepositoryPage selectVisibility(){
        visibilityRepoButton.click();
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