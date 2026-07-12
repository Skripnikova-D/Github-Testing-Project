package elements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс для работы с полями ввода на странице.
 * Пример: Input field = Input.byId("username");
 */
public class Input extends BaseElement{

    private static final Logger logger = LoggerFactory.getLogger(Input.class); //Логгер для записи сообщений о работе класса
    // Шаблоны XPath
    private static final String ID_XPATH = "//input[@id='%s']";
    private static final String ARIA_LABEL_XPATH = "//input[@aria-label='%s']";
    private static final String NAME_XPATH = "//input[@name='%s']";
    private static final String LABEL_XPATH = "//label[contains(., '%s')]/following-sibling::*//input";

    // Конструкторы
    /**
     * Конструктор с подстановкой значения в шаблон XPath.
     * Пример: new Input("//input[@id='%s']", "username")
     *
     * @param xpath          Шаблон XPath с %s
     * @param attributeValue Значение для подстановки вместо %s
     */
    protected Input(String xpath, String attributeValue) {
        super(xpath, attributeValue);
    }

    /**
     * Конструктор для готового XPath (без подстановки).
     * Пример: new Input("//input[@data-testid='custom-input']")
     *
     * @param xpath Полный XPath элемента
     */
    protected Input(String xpath) {
        super(xpath);
    }

    //Методы взаимодействия
    /**
     * Вводит текст в поле.
     * Пример: input.setValue("test");
     *
     * @param text Текст для ввода
     */
    public void setValue(String text) {
        logger.info("Ввод текста в поле: '{}'", text);
        baseElement.setValue(text);
    }

    /**
     * Возвращает текущий текст из поля.
     * Пример: String value = input.getValue();
     *
     * @return Текст из поля
     */
    public String getValue() {
        logger.debug("Получение значения из поля");
        return baseElement.getValue();
    }

    //Методы поиска
    /**
     * Поиск поля ввода по ID.
     * Пример: Input.byId("username")
     *
     * @param id ID поля ввода
     * @return объект Input
     */
    public static Input byId(String id) {
        return new Input(ID_XPATH, id);
    }

    /**
     * Поиск поля ввода по aria-label.
     * Пример: Input.byAriaLabel("File name")
     *
     * @param ariaLabel Значение атрибута aria-label
     * @return объект Input
     */
    public static Input byAriaLabel(String ariaLabel) {
        return new Input(ARIA_LABEL_XPATH, ariaLabel);
    }

    /**
     * Поиск поля ввода по атрибуту name.
     * Пример: Input.byName("repository[name]")
     *
     * @param name Значение атрибута name
     * @return объект Input
     */
    public static Input byName(String name) {
        return new Input(NAME_XPATH, name);
    }

    /**
     * Поиск поля ввода по тексту label.
     * Пример: Input.byLabel("New branch name")
     *
     * @param labelText Текст label
     * @return объект Input
     */
    public static Input byLabel(String labelText) {
        return new Input(LABEL_XPATH, labelText);
    }
}