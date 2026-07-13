package elements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс для работы с элементами <summary> (раскрывающиеся списки).
 * Пример: Summary summary = Summary.byContainsText("base:");
 */
public  class Summary extends BaseElement{
    private static final Logger logger = LoggerFactory.getLogger(Summary.class);   //Логгер для записи сообщений о работе класса
    // Шаблоны XPath
    private static final String CONTAINS_XPATH = "//summary[contains(., '%s')]";
    private static final String ARIA_LABEL_XPATH = "//summary[@aria-label='%s']";

    // Конструкторы

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
     * Конструктор для готового XPath (без подстановки).
     * Пример: new Summary("//summary[@aria-label='Select branch']")
     *
     * @param xpath Полный XPath элемента
     */
    protected Summary(String xpath) {
        super(xpath);
    }

    // Методы взаимодействия
    /**
     * Кликает по элементу <summary>, чтобы раскрыть список.
     */
    public void click() {
        //logger.info("Клик по элементу Summary: {}", baseElement);
        baseElement.click();
    }

    // Методы поиска
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

    /**
     * Поиск элемента <summary> по aria-label.
     * Пример: Summary.byAriaLabel("Select base branch")
     *
     * @param ariaLabel Значение атрибута aria-label
     * @return объект Summary
     */
    public static Summary byAriaLabel(String ariaLabel) {
        return new Summary(ARIA_LABEL_XPATH, ariaLabel);
    }

    /**
     * Поиск элемента <summary> по готовому XPath.
     * Пример: Summary.byXpath("//summary[@aria-label='Select branch']")
     *
     * @param xpath Полный XPath элемента
     * @return объект Summary
     */
    public static Summary byXpath(String xpath) {
        return new Summary(xpath);
    }
}