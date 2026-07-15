package pages;

import elements.Button;
import elements.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import pages.RepositoryPage;
import pages.CreateFilePage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Модальное окно коммита изменений.
 */
public class CommitModal extends BaseModal {

    private static final Logger logger = LoggerFactory.getLogger(CommitModal.class);

    private static final String MODAL_XPATH = "//div[@role='dialog' and contains(., 'Commit changes')]";

    // Элементы модального окна
    private final Input commitMessageInput = Input.byAriaLabel("Commit message");
    private final Button commitButton = Button.byContainsText("Commit changes");
    private final Button cancelButton = Button.byContainsText("Cancel");

    public CommitModal() {
        super(MODAL_XPATH);
        waitForOpen();
        logger.info("Модальное окно коммита открыто");
    }

    /**
     * Устанавливает сообщение коммита
     */
    public CommitModal setCommitMessage(String message) {
        logger.info("Установка сообщения коммита: {}", message);
        commitMessageInput.setValue(message);
        return this;
    }

    /**
     * Подтверждает коммит
     * После подтверждения - переход на страницу репозитория
     */
    public RepositoryPage confirm() {
        logger.info("Подтверждение коммита");
        commitButton.click();

        // Ожидание закрытия модального окна
        waitForClose();
        logger.info("Модальное окно закрыто, возврат на страницу репозитория");

        return new RepositoryPage();
    }

    /**
     * Отменяет коммит
     * Возврат на страницу создания файла
     */
    public CreateFilePage cancel() {
        logger.info("Отмена коммита");
        cancelButton.click();
        $x(MODAL_XPATH).shouldBe(hidden, Duration.ofSeconds(3));
        return new CreateFilePage();
    }
}