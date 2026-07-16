package elements;


/**
 * Класс для работы с элементами <summary> (раскрывающиеся списки).
 * Пример: Summary summary = Summary.byContainsText("base:");
 */
public  class Summary extends BaseElement{

    private static final String CONTAINS_XPATH = "//summary[contains(., '%s')]";

    /**
     * Конструктор с подстановкой значения в шаблон XPath.
     * Пример: new Summary("//summary[contains(., '%s')]", "base:")
     *
     * @param xpath          Шаблон XPath с %s
     * @param attributeValue Значение для подстановки вместо %s
     */
    protected Summary(String xpath, String attributeValue) {
        super(xpath, attributeValue);
    }

    /**
     * Кликает по элементу <summary>, чтобы раскрыть список.
     */
    public void click() {
        baseElement.click();
    }

    /**
     * Поиск элемента <summary> по части текста.
     * Пример: Summary.byContainsText("base:")
     *
     * @param text Текст внутри элемента
     * @return объект Summary
     */
    public static Summary byContainsText(String text) {
        return new Summary(CONTAINS_XPATH, text);
    }

    public String getText() {
        return baseElement.getText();
    }
}