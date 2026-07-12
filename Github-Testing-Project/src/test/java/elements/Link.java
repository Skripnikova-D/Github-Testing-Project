package elements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс для работы со ссылками на странице.
 * Пример: Link link = Link.byHref("/pulls");
 */
public class Link extends BaseElement implements Clickable{
    private static final Logger logger = LoggerFactory.getLogger(Link.class); //Логгер для записи сообщений о работе класса

    // Шаблоны XPath
    private static final String HREF_XPATH = "//a[@href='%s']";
    private static final String CONTAINS_XPATH = "//a[contains(., '%s')]";
    private static final String ARIA_LABEL_XPATH = "//a[@aria-label='%s']";

    private static final String HREF_WITH_LOCATION_XPATH = "//a[@href='%s' and contains(@data-hydro-click, '%s')]";
    private static final String HREF_WITH_CLASS_XPATH = "//a[@href='%s' and contains(@class, '%s')]";
    private static final String BRANCH_XPATH = "//*[contains(@id, '%s')]//a[span='%s']";

    // Конструкторы
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

    // Методы взаимодействия
    /**
     * Кликает по ссылке.
     */
    @Override
    public void click() {
        logger.info("Клик по ссылке: {}", baseElement);
        baseElement.click();
    }

    // Методы поиска

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

    /**
     * Поиск ветки по типу (base/head) и названию.
     * Пример: Link.branch("base", "main")
     * Пример: Link.branch("head", "asdf")
     *
     * @param type       "base" или "head"
     * @param branchName Название ветки
     * @return объект Link
     */
    public static Link branch(String type, String branchName) {
        String xpath = String.format(BRANCH_XPATH, type, branchName);
        return new Link(xpath);
    }

    /**
     * Поиск ссылки по href с уточнением по классу.
     * Пример: Link.byHrefWithClass("/login", "HeaderMenu-link--sign-in")
     *
     * @param href      Значение атрибута href
     * @param className Часть или полное имя класса
     * @return объект Link
     */
    public static Link byHrefWithClass(String href, String className) {
        String xpath = String.format(HREF_WITH_CLASS_XPATH, href, className);
        return new Link(xpath);
    }
}