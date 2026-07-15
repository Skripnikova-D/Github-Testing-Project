package pages;

import elements.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommitPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(CommitPage.class);

    private final Button commitButton = Button.byContainsText("Commit changes");
    private final Button cancelButton = Button.byContainsText("Cancel");

    public CommitModal confirm() {
        logger.info("Подтверждение коммита");
        commitButton.click();
        return new CommitModal();
    }

}