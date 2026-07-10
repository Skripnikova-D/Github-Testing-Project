package elements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс для работы с пунктами выпадающих списков.
 * Пример: DropdownItem item = DropdownItem.byRoleAndText("Public");
 */
public class DropdownItem extends BaseElement{
    private static final Logger logger = LoggerFactory.getLogger(DropdownItem.class); //Логгер для записи сообщений о работе класса
    private static final String CONTAINS_XPATH = "//*[contains(@role, 'menuitem') and contains(., '%s')]";

    /**
     * Конструктор с подстановкой значения в шаблон XPath.
     * Пример: new DropdownItem("//*[contains(@role, 'menuitem') and contains(., '%s')]", "Public")
     *
     * @param xpath          Шаблон XPath с %s
     * @param attributeValue Значение для подстановки вместо %s
     */
    protected DropdownItem(String xpath, String attributeValue) {
        super(xpath, attributeValue);
    }

    /**
     * Конструктор для готового XPath (без подстановки).
     * Пример: new DropdownItem("//ul[@role='menu']//li[contains(., 'Public')]")
     *
     * @param xpath Полный XPath элемента
     */
    protected DropdownItem(String xpath) {
        super(xpath);
    }

    //Методы поиска
    /**
     * Поиск пункта меню по роли и тексту.
     * Пример: DropdownItem.byRoleAndText("Public")
     */
    public static DropdownItem byRoleAndText(String text) {
        return new DropdownItem(CONTAINS_XPATH, text);
    }

    /**
     * Поиск пункта меню по готовому XPath.
     * Пример: DropdownItem.byXpath("//ul[@role='menu']//li[contains(., 'Public')]")
     */
    public static DropdownItem byXpath(String xpath) {
        return new DropdownItem(xpath);
    }

    //Метод взаимодействия
    /**
     * Выбирает пункт меню (кликает по нему).
     */
    public void click() {
        logger.info("Клик по пункту меню: {}", baseElement);
        baseElement.click();
    }
}