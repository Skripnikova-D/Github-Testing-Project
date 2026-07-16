package elements;


/**
 * Класс для работы с пунктами выпадающих списков.
 * Пример: DropdownItem item = DropdownItem.byRoleAndText("Public");
 */
public class DropdownItem extends BaseElement implements Clickable{
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
     * Выбирает пункт меню (кликает по нему).
     */
    @Override
    public void click() {
        baseElement.click();
    }

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
}