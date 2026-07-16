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
    private final Button commitButton = Button.byContainsText("Commit changes");

    public CommitModal() {
        super(MODAL_XPATH);
        waitForOpen();
        logger.info("Модальное окно коммита открыто");
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
}