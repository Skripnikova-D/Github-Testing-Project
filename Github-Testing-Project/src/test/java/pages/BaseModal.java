package pages.modals;

import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

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
        modalContainer.shouldBe(visible, Duration.ofSeconds(5));
        logger.info("Модальное окно открыто");
    }

    /**
     * Проверяет, открыто ли модальное окно
     */
    public boolean isOpen() {
        return modalContainer.isDisplayed();
    }

    /**
     * Закрывает модальное окно (клик вне)
     */
    public void close() {
        logger.info("Закрытие модального окна");
        $x(BODY_XPATH).click();
    }
}
