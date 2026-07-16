package elements;

/**
 * Класс для работы со ссылками на странице.
 * Пример: Link link = Link.byHref("/pulls");
 */
public class Link extends BaseElement implements Clickable{

    private static final String HREF_XPATH = "//a[@href='%s']";
    private static final String CONTAINS_XPATH = "//a[contains(., '%s')]";
    private static final String ARIA_LABEL_XPATH = "//a[@aria-label='%s']";
    private static final String DATA_TAB_ITEM_XPATH = "//a[@data-tab-item='%s']";

    private static final String HREF_WITH_LOCATION_XPATH = "//a[@href='%s' and contains(@data-hydro-click, '%s')]";

    /**
     * Конструктор с подстановкой значения в шаблон XPath.
     * Пример: new Link("//a[@href='%s']", "/pulls")
     *
     * @param xpath          Шаблон XPath с %s
     * @param attributeValue Значение для подстановки вместо %s
     */
    protected Link(String xpath, String attributeValue) {
        super(xpath, attributeValue);
    }

    /**
     * Конструктор для готового XPath (без подстановки).
     * Пример: new Link("//a[@href='/pulls']")
     *
     * @param xpath Полный XPath элемента
     */
    protected Link(String xpath) {
        super(xpath);
    }

    /**
     * Кликает по ссылке.
     */
    @Override
    public void click() {
        baseElement.click();
    }

    /**
     * Поиск ссылки по атрибуту href.
     * Пример: Link.byHref("/pulls")
     *
     * @param href Значение атрибута href
     * @return объект Link
     */
    public static Link byHref(String href) {
        return new Link(HREF_XPATH, href);
    }

    /**
     * Поиск ссылки по части текста.
     * Пример: Link.byContainsText("View all branches")
     *
     * @param text Текст ссылки
     * @return объект Link
     */
    public static Link byContainsText(String text) {
        return new Link(CONTAINS_XPATH, text);
    }

    /**
     * Поиск ссылки по aria-label.
     * Пример: Link.byAriaLabel("Delete this file")
     *
     * @param ariaLabel Значение атрибута aria-label
     * @return объект Link
     */
    public static Link byAriaLabel(String ariaLabel) {
        return new Link(ARIA_LABEL_XPATH, ariaLabel);
    }

    /**
     * Поиск ссылки по href с уточнением по data-hydro-click (для кнопки New).
     * Пример: Link.byHrefWithLocation("/new", "location=left")
     *
     * @param href     Значение атрибута href
     * @param location Значение для поиска в data-hydro-click
     * @return объект Link
     */
    public static Link byHrefWithLocation(String href, String location) {
        String xpath = String.format(HREF_WITH_LOCATION_XPATH, href, location);
        return new Link(xpath);
    }

    public static Link byDataTabItem(String tabItem) {
        return new Link(DATA_TAB_ITEM_XPATH, tabItem);
    }
}