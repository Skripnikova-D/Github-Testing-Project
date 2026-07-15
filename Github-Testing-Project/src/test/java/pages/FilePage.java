package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class FilePage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(FilePage.class);

    /**
     * Открывает меню трех точек
     */
    public FilePage clickMoreOptions() {
        logger.info("Нажатие кнопки More options");

        SelenideElement button = $x("//button[.//*[local-name()='svg' and contains(@class, 'octicon-kebab-horizontal')]]");
        button.shouldBe(Condition.clickable, Duration.ofSeconds(10)).click();

        return this;
    }

    /**
     * Выбирает опцию Delete file из меню
     */
    public CommitPage selectDeleteFile() {
        logger.info("Выбор опции Delete file");

        Selenide.sleep(500);

        SelenideElement deleteBtn = $x("//span[text()='Delete file']/parent::*");
        deleteBtn.shouldBe(Condition.clickable, Duration.ofSeconds(10)).click();

        return new CommitPage();
    }
}