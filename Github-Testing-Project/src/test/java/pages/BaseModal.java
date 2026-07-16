package pages;

import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Базовый класс для всех модальных окон.
 */
public class BaseModal {

    protected static final Logger logger = LoggerFactory.getLogger(BaseModal.class);
    
    private static final String BODY_XPATH = "//body";

    protected final SelenideElement modalContainer;

    protected BaseModal(String containerXpath) {
        this.modalContainer = $x(containerXpath);
        logger.info("Базовый контейнер модалки инициализирован: {}", containerXpath);
    }

    /**
     * Явно ждёт, пока модальное окно станет видимым.
     * Вызывается в тестах или в методах, которые открывают модалку.
     */
    public void waitForOpen() {
        logger.info("Ожидание открытия модального окна");
        modalContainer.shouldBe(visible, Duration.ofSeconds(5));
    }

    /**
     * Ждёт, пока модальное окно закроется.
     */
    public void waitForClose() {
        logger.info("Ожидание закрытия модального окна");
        modalContainer.shouldBe(hidden, Duration.ofSeconds(5));
    }

}
