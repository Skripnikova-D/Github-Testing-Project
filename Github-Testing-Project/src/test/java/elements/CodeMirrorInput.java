package elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс для работы с редактором кода CodeMirror (поле содержимого файла).
 * Адаптирован под CodeMirror 6, который использует GitHub.
 */
public class CodeMirrorInput extends BaseElement {
    private static final Logger logger = LoggerFactory.getLogger(CodeMirrorInput.class);
    private static final String CLASS_XPATH = "//div[contains(@class, '%s')]";

    protected CodeMirrorInput(String xpath, String attributeValue) {
        super(xpath, attributeValue);
    }

    protected CodeMirrorInput(String xpath) {
        super(xpath);
    }

    /**
     * Вводит текст в редактор.
     */
    public void setValue(String text) {
        logger.info("Ввод текста в CodeMirror редактор");

        // В CodeMirror 6 (GitHub) редактируемая область - это div с классом cm-content и contenteditable="true"
        SelenideElement contentEditable = baseElement.$(".cm-content");

        if (contentEditable.exists()) {
            logger.info("Найден редактируемый блок .cm-content, используем его для ввода");
            contentEditable.click();
            // Эмулируем выделение всего текста (Ctrl+A) и удаление
            contentEditable.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
            // Вводим новый текст
            contentEditable.sendKeys(text);
        } else {
            // Fallback 1: пробуем найти textarea (на случай других форм или старых версий)
            SelenideElement textArea = baseElement.$("textarea");
            if (textArea.exists()) {
                logger.info("Найдена textarea, используем её");
                textArea.setValue(text);
            } else {
                // Fallback 2: самый крайний случай (может вызвать element not interactable на GitHub)
                logger.warn("Не удалось найти .cm-content или textarea. Пробуем прямой ввод в baseElement");
                baseElement.click();
                baseElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
                baseElement.sendKeys(text);
            }
        }
    }

    /**
     * Очищает содержимое редактора.
     */
    public void clear() {
        SelenideElement contentEditable = baseElement.$(".cm-content");
        if (contentEditable.exists()) {
            contentEditable.click();
            contentEditable.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        } else {
            SelenideElement textArea = baseElement.$("textarea");
            if (textArea.exists()) {
                textArea.clear();
            } else {
                baseElement.click();
                baseElement.clear();
            }
        }
    }

    public static CodeMirrorInput byClass(String className) {
        return new CodeMirrorInput(CLASS_XPATH, className);
    }

    public static CodeMirrorInput byXpath(String xpath) {
        return new CodeMirrorInput(xpath);
    }
}