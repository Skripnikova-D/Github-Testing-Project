package elements;

/**
 * Класс для работы с кнопками на странице.
 * Пример: Button button = Button.byContainsText("Submit");
 */
public class Button extends BaseElement implements Clickable{


    private static final String ID_XPATH = "//button[@id='%s']";
    private static final String CONTAINS_XPATH = "//button[contains(., '%s')]";
    private static final String ARIA_LABEL_XPATH = "//button[@aria-label='%s']";
    private static final String DATA_TESTID_XPATH = "//button[@data-testid='%s']";
    private static final String CLASS_XPATH = "//button[@class='%s']";

    /**
     * Конструктор базового элемента.
     * Находит элемент на странице по шаблону XPath и подставленному значению.
     *
     * @param xpath
     * @param attributeValue
     */
    protected Button(String xpath, String attributeValue) {
        super(xpath, attributeValue);
    }

    /**
     * Конструктор для готового XPath (без подстановки).
     * Используется для сложных случаев
     *
     * @param xpath Полный XPath элемента
     */
    protected Button(String xpath) {
        super(xpath);
    }

    /**
     * Кликает по кнопке.
     */
    @Override
    public void click() {
        baseElement.click();
    }

    public String getText() {
        return baseElement.getText();
    }

    /**
     * Поиск кнопки по ID.
     * Пример: Button.byId("visibility-anchor-button")
     *
     * @param id ID кнопки
     * @return объект Button
     */
    public static Button byId(String id) {
        return new Button(ID_XPATH, id);
    }

    /**
     * Поиск кнопки по части текста (включая вложенные элементы).
     * Пример: Button.byContainsText("Create repository")
     *
     * @param text Текст кнопки
     * @return объект Button
     */
    public static Button byContainsText(String text) {
        return new Button(CONTAINS_XPATH, text);
    }

    /**
     * Поиск кнопки по aria-label.
     * Пример: Button.byAriaLabel("Add file")
     *
     * @param ariaLabel Значение атрибута aria-label
     * @return объект Button
     */
    public static Button byAriaLabel(String ariaLabel) {
        return new Button(ARIA_LABEL_XPATH, ariaLabel);
    }

    /**
     * Поиск кнопки по data-testid.
     * Пример: Button.byDataTestId("more-file-actions-button-nav-menu-wide")
     *
     * @param testId Значение атрибута data-testid
     * @return объект Button
     */
    public static Button byDataTestId(String testId) {
        return new Button(DATA_TESTID_XPATH, testId);
    }

}