package elements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс для работы с редактором кода CodeMirror (поле содержимого файла).
 * Отличается от обычного Input тем, что требует клика для активации перед вводом.
 * Пример: CodeMirrorInput editor = CodeMirrorInput.byClass();
 */
public class CodeMirrorInput extends BaseElement{
    private static final Logger logger = LoggerFactory.getLogger(CodeMirrorInput.class); //Логгер для записи сообщений о работе класса
    //Шаблоны XPath
    private static final String CLASS_XPATH = "//div[contains(@class, '%s')]";

    //Конструкторы
    /**
     * Конструктор с подстановкой значения в шаблон XPath.
     * Пример: new CodeMirrorInput("//div[contains(@class, '%s')]", "cm-editor")
     *
     * @param xpath          Шаблон XPath с %s
     * @param attributeValue Значение для подстановки вместо %s
     */
    protected CodeMirrorInput(String xpath, String attributeValue) {
        super(xpath, attributeValue);
    }

    /**
     * Конструктор для готового XPath (без подстановки).
     * Пример: new CodeMirrorInput("//div[contains(@class, 'cm-editor')]")
     *
     * @param xpath Полный XPath элемента
     */
    protected CodeMirrorInput(String xpath) {
        super(xpath);
    }

    //Методы взаимодействия
    /**
     * Вводит текст в редактор.
     * Сначала кликает по полю (чтобы активировать редактор), затем вводит текст.
     * Пример: editor.setValue("Just Description");
     *
     * @param text Текст для ввода
     */
    public void setValue(String text) {
        logger.info("Ввод текста в CodeMirror редактор: '{}'", text);
        baseElement.click();
        baseElement.setValue(text);
    }

    /**
     * Очищает содержимое редактора.
     * Пример: editor.clear();
     */
    public void clear() {
        logger.info("Очистка CodeMirror редактора");
        baseElement.click();
        baseElement.clear();
    }

    //Методы поиска
    /**
     * Поиск редактора CodeMirror по классу.
     * Пример: CodeMirrorInput.byClass("cm-editor")
     *
     * @param className Имя класса редактора
     * @return объект CodeMirrorInput
     */
    public static CodeMirrorInput byClass(String className) {
        return new CodeMirrorInput(CLASS_XPATH, className);
    }

    /**
     * Поиск редактора CodeMirror по готовому XPath.
     * Пример: CodeMirrorInput.byXpath("//div[contains(@class, 'cm-editor')]")
     *
     * @param xpath Полный XPath элемента
     * @return объект CodeMirrorInput
     */
    public static CodeMirrorInput byXpath(String xpath) {
        return new CodeMirrorInput(xpath);
    }
}