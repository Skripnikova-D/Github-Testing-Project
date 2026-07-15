package pages;

import elements.Button;
import elements.DropdownItem;
import elements.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница создания нового репозитория.
 */
public class NewRepositoryPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(NewRepositoryPage.class);

    // Элементы страницы
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
        logger.info("Нажимаем кнопку создания репозитория");

        // XPath из Excel: //button[contains(., 'Create repository')]
        SelenideElement button = $x("//button[contains(., 'Create repository')]")
                .shouldBe(Condition.visible, Condition.enabled);

        button.scrollIntoView(true);
        Selenide.sleep(500);
        button.click();
        logger.info("Клик по кнопке выполнен");

        // Ждем перехода - используем WebDriverRunner.url()
        logger.info("Ожидаем перехода на страницу репозитория...");
        Selenide.Wait().withTimeout(Duration.ofSeconds(30))
                .until(driver -> !WebDriverRunner.url().contains("/new"));

        logger.info("Переход выполнен. Текущий URL: {}", WebDriverRunner.url());

        return new RepositoryPage();
    }
}