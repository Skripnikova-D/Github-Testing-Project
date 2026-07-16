package elements;

import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Составной элемент для работы с таблицами на странице.
 * Наследуется от BaseElement.
 * Пример: Table table = new Table("Active branches");
 */
public class BranchTable extends BaseElement {

    private static final String TABLE_BY_HEADER = "//h2[contains(text(), '%s')]/following-sibling::div";
    private static final String ROW_BY_TEXT_XPATH = ".//tr[contains(., '%s')]";
    private static final String DELETE_BUTTON_IN_ROW_XPATH = ".//span[text()='Delete branch']/preceding-sibling::button";
    private static final String BRANCH_LINK_IN_ROW_XPATH = ".//a[span='%s']";
    private static final String FILE_LINK_IN_ROW_XPATH = ".//td[@class='%s']//a[@title='%s']";

    /**
     * Конструктор таблицы по заголовку.
     * Пример: new Table("Active branches")
     *
     * @param headerText Текст заголовка таблицы
     */
    public BranchTable(String headerText) {
        super(TABLE_BY_HEADER, headerText);
    }

    /**
     * Находит строку таблицы по тексту.
     *
     * @param rowText Текст в строке (например, название ветки или файла)
     * @return SelenideElement строка таблицы
     */
    private SelenideElement getRow(String rowText) {
        String xpath = String.format(ROW_BY_TEXT_XPATH, rowText);
        return baseElement.$x(xpath);
    }

    /**
     * Удаляет ветку по названию таблицы и названию ветки.
     * Пример: Table.deleteBranch("Active branches", "asdf")
     *
     * @param tableName  Название таблицы (заголовок)
     * @param branchName Название ветки, которую нужно удалить
     */
    public static void deleteBranch(String tableName, String branchName) {
        BranchTable table = new BranchTable(tableName);
        SelenideElement row = table.getRow(branchName);
        row.$x(DELETE_BUTTON_IN_ROW_XPATH).click();
    }

    /**
     * Кликает по ссылке на файл в таблице по классу td и title.
     * Пример: Table.clickFileLink("react-directory-row-name-cell-large-screen", "NewFile.txt")
     *
     * @param tdClass  Класс ячейки td
     * @param fileName Название файла (значение атрибута title)
     */
    public static void clickFileLink(String tdClass, String fileName) {
        String xpath = String.format(FILE_LINK_IN_ROW_XPATH, tdClass, fileName);
        $x(xpath).click();
    }
}