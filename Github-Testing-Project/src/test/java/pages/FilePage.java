package pages;

import elements.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilePage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(FilePage.class);

    // Элементы
    private final Button moreOptions = Button.byAriaLabel("More options");
    private final Button deleteFileOption = Button.byContainsText("Delete file");

    /**
     * Открывает меню трех точек
     */
    public FilePage clickMoreOptions() {
        logger.info("Нажатие кнопки More options");
        moreOptions.click();
        return this;
    }

    /**
     * Выбирает опцию Delete file из меню
     * После выбора - переход на страницу коммита
     */
    public CommitPage selectDeleteFile() {
        logger.info("Выбор опции Delete file");
        deleteFileOption.click();
        return new CommitPage();
    }
}