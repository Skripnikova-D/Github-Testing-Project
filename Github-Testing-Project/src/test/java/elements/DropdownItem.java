package elements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс для работы с пунктами выпадающих списков.
 * Пример: DropdownItem item = DropdownItem.byRoleAndText("Public");
 */
public class DropdownItem extends BaseElement{
    private static final Logger logger = LoggerFactory.getLogger(DropdownItem.class); //Логгер для записи сообщений о работе класса
    private static final String MENU_AND_TEXT_XPATH = "//ul[@role='%s']//li[contains(., '%s')]";

    /**
     * Конструктор с подстановкой двух параметров.
     *
     * @param xpath Шаблон XPath с %s
     * @param param1 Первый параметр (роль меню)
     * @param param2 Второй параметр (текст пункта)
     */
    private DropdownItem(String xpath, String param1, String param2) {
        super(String.format(xpath, param1, param2), "");
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
     * Поиск пункта меню по роли меню и тексту пункта.
     * Пример: DropdownItem.byMenuAndText("menu", "Public")
     *
     * @param menuRole Роль меню (например, "menu")
     * @param text     Текст пункта меню
     * @return объект DropdownItem
     */
    public static DropdownItem byMenuAndText(String menuRole, String text) {
        return new DropdownItem(MENU_AND_TEXT_XPATH, menuRole, text);
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