package elements;

/**
 * Компонент для работы с выпадающим меню SelectMenu (выбор веток в PR).
 * Пример: SelectMenu baseMenu = new SelectMenu("base");
 *          baseMenu.selectBranch("main");
 */
public class SelectMenu extends BaseElement {

    private static final String MENU_BY_TITLE = "//span[contains(text(), 'Choose a %s ref')]/" +
            "ancestor::div[@class='SelectMenu-modal']";
    private static final String ITEM_BY_NAME = ".//a[span='%s']";

    /**
     * Конструктор меню выбора веток в PR.
     * Пример: new SelectMenu("base")
     *
     * @param type "base" или "head"
     */
    public SelectMenu(String type) {
        super(String.format(MENU_BY_TITLE, type), "");
    }

    /**
     * Выбирает ветку в меню по названию.
     * Пример: baseMenu.selectBranch("main")
     *
     * @param branchName Название ветки
     */
    public void select(String branchName) {
        String itemXpath = String.format(ITEM_BY_NAME, branchName);
        baseElement.$x(itemXpath).click();
    }
}