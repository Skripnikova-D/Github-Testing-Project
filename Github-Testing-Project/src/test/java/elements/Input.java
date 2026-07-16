package elements;

/**
 * Класс для работы с полями ввода на странице.
 * Пример: Input field = Input.byId("username");
 */
public class Input extends BaseElement{

    private static final String ID_XPATH = "//input[@id='%s']";
    private static final String ARIA_LABEL_XPATH = "//input[@aria-label='%s']";
    private static final String LABEL_XPATH = "//label[contains(., '%s')]/following-sibling::*//input";

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
     * Вводит текст в поле.
     * Пример: input.setValue("test");
     *
     * @param text Текст для ввода
     */
    public void setValue(String text) {
        baseElement.setValue(text);
    }

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