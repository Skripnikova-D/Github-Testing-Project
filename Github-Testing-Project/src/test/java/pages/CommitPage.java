package pages;

import elements.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Страница коммита.
 */
public class CommitPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(CommitPage.class);

    private final Button commitButton = Button.byContainsText("Commit changes");

    /**
     * Подтверждает коммит.
     * Возвращает RepositoryPage, так как после коммита открывается страница репозитория.
     */
    public RepositoryPage confirm() {
        logger.info("Подтверждение коммита");
        commitButton.click();
        return new RepositoryPage();
    }

}