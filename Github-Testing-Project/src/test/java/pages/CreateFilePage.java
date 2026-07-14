package pages;

import elements.Button;
import elements.CodeMirrorInput;
import elements.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pages.modals.CommitModal;

/**
 * Страница создания нового файла.
 */
public class CreateFilePage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(CreateFilePage.class);

    // Элементы страницы
    private final Input fileNameInput = Input.byAriaLabel("File name");
    private final CodeMirrorInput fileContent = CodeMirrorInput.byClass("cm-editor");
    private final Button commitChangesButton = Button.byContainsText("Commit changes...");

    /**
     * Устанавливает имя файла
     */
    public CreateFilePage setFileName(String fileName) {
        logger.info("Установка имени файла: {}", fileName);
        fileNameInput.setValue(fileName);
        return this;
    }

    /**
     * Устанавливает содержимое файла
     */
    public CreateFilePage setFileContent(String content) {
        logger.info("Установка содержимого файла");
        fileContent.setValue(content);
        return this;
    }

    /**
     * Нажимает кнопку "Commit changes..."
     * Открывается модальное окно коммита
     */
    public CommitModal clickCommitChangesButton() {
        logger.info("Нажатие кнопки Commit changes...");
        commitChangesButton.click();

        // Возвращаем модальное окно
        return new CommitModal();
    }
}
