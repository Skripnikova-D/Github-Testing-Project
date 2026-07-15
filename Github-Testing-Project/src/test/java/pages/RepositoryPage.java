package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import elements.Button;
import elements.Link;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Страница репозитория.
 */
public class RepositoryPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryPage.class);
    private static final String REPO_NAME_XPATH = "//a[contains(@class, 'overflow-x-hidden') and contains(@class, 'color-fg-default')]";
    private static final String PRIVATE_BADGE_XPATH = "//span[contains(text(), 'Private')]";
    private static final String REPO_IN_LIST_XPATH = "//a[contains(text(), '%s')]";
    private static final String FILE_EXISTS_XPATH = "//a[contains(@title, '%s')]";

    // Элементы страницы
    private final Button addFileButton = Button.byAriaLabel("Add file");
    private final Link codeTab = Link.byDataTabItem("code");
    private final Button pullRequestsTab = Button.byContainsText("Pull requests");
    private final Button branchSelector = Button.byAriaLabel("main branch");
    private final Link viewAllBranches = Link.byContainsText("View all branches");

    /**
     * Переходит на вкладку Code
     */
    public RepositoryPage goToCodeTab() {
        logger.info("Переход на вкладку Code");
        codeTab.click();
        return this;
    }

    /**
     * Переходит на вкладку Pull requests
     */
    public PullRequestsPage goToPullRequestsTab() {
        logger.info("Переход на вкладку Pull requests");
        pullRequestsTab.click();
        return new PullRequestsPage();
    }

    /**
     * Нажимает кнопку "Add file"
     */
    public RepositoryPage clickAddFileButton() {
        logger.info("Нажатие кнопки Add file");
        addFileButton.click();
        return this;
    }

    /**
     * Выбирает опцию "Create new file" из выпадающего списка
     */
    public CreateFilePage selectCreateNewFileOption() {
        logger.info("Выбор опции Create new file");
        Link.byContainsText("Create new file").click();
        return new CreateFilePage();
    }

    /**
     * Кликает по селектору веток
     */
    public RepositoryPage clickBranchSelector() {
        logger.info("Клик по селектору веток");
        branchSelector.click();
        return this;
    }

    /**
     * Нажимает "View all branches"
     */
    public BranchesPage clickViewAllBranches() {
        logger.info("Нажатие View all branches");
        branchSelector.click();
        viewAllBranches.click();
        return new BranchesPage();
    }

    /**
     * Выбирает ветку
     */
    public RepositoryPage selectBranch(String branchName) {
        logger.info("Выбор ветки: {}", branchName);
        branchSelector.click();
        Link.byContainsText(branchName).click();
        return this;
    }

    /**
     * Проверяет, существует ли ветка
     */
   /* public boolean isBranchPresent(String branchName) {
        logger.info("Проверка наличия ветки: {}", branchName);
        branchSelector.click();
        boolean exists = Link.byContainsText(branchName).isDisplayed();
        branchSelector.click(); // закрываем дропдаун
        return exists;
    }*/

    /**
     * Получает имя текущей ветки
     */
   public String getCurrentBranchName() {
       logger.info("Получение текущей ветки");
       return branchSelector.getText();
   }

    /**
     * Проверяет, доступна ли ветка в выпадающем списке
     */
    public boolean isBranchAvailableInDropdown(String branchName) {
        logger.info("Проверка доступности ветки {} в выпадающем списке", branchName);
        branchSelector.click();
        boolean exists = Link.byContainsText(branchName).isDisplayed();
        branchSelector.click();
        return exists;
    }

    /**
     * Кликает на файл по имени
     */
    public FilePage clickOnFile(String fileName) {
        // Находим все элементы с этим title
        var allLinks = $$x("//a[@title='" + fileName + "']");

        logger.info("Найдено элементов с title '{}': {}", fileName, allLinks.size());

        for (SelenideElement link : allLinks) {
            logger.info("  - displayed: {}, href: {}", link.isDisplayed(), link.getAttribute("href"));
            if (link.isDisplayed()) {
                link.click();
                return new FilePage();
            }
        }

        throw new RuntimeException("Не найден видимый элемент: " + fileName);
    }

    /**
     * Проверяет, существует ли файл в репозитории
     */
    public boolean isFileExists(String fileName) {
        logger.info("Проверка наличия файла: {}", fileName);

        // GitHub использует либо атрибут title, либо прямой текст внутри ссылки <a> для имен файлов
        String xpath = String.format("//a[@title='%s' or contains(text(), '%s')]", fileName, fileName);

        try {
            // shouldBe(visible) заставит Selenide ждать появления файла до 15 секунд
            $x(xpath).shouldBe(visible, Duration.ofSeconds(15));
            return true;
        } catch (Exception e) {
            logger.warn("Файл '{}' не появился на странице за 15 секунд", fileName);
            return false;
        }
    }

    public boolean isFileExistsInTable(String fileName) {
        logger.info("Проверка наличия файла: {}", fileName);

        // Ищем файл ТОЛЬКО в таблице со списком файлов
        String xpath = String.format("//table[contains(@class, 'files')]//a[@title='%s']", fileName);

        // Используем $$ - не ждем появления, просто проверяем наличие
        boolean exists = $$x(xpath).size() > 0;

        logger.info("Файл '{}' существует: {}", fileName, exists);
        return exists;
    }
    public RepositoryPage navigateToRoot() {
        logger.info("Переход в корень репозитория");

        // Если мы не на главной странице репозитория
        if (!WebDriverRunner.url().contains("/tree/") &&
                !WebDriverRunner.url().equals("https://github.com/NikLun4/NewRepoPublic1")) {

            // Идем в корень через breadcrumb
            $x("//a[contains(@class, 'breadcrumb') and contains(@href, 'NewRepoPublic1')]").click();
            Selenide.sleep(1000);
        }

        return this;
    }
    /**
     * Получает имя репозитория
     */
    public String getRepositoryName() {
        logger.info("Получаем имя репозитория из URL: {}", WebDriverRunner.url());

        // Ждем загрузки
        Selenide.Wait().withTimeout(Duration.ofSeconds(20))
                .until(driver -> !WebDriverRunner.url().contains("/new"));

        // Берем имя из URL
        String url = WebDriverRunner.url();
        String[] parts = url.split("/");
        String repoName = parts[parts.length - 1];

        logger.info("Имя репозитория из URL: {}", repoName);
        return repoName;
    }

    /**
     * Проверяет, является ли репозиторий приватным
     */
    public boolean isPrivate() {
        logger.info("Проверка, является ли репозиторий приватным");
        return $x(PRIVATE_BADGE_XPATH).isDisplayed();
    }
    public boolean isLoaded() {
        String currentUrl = WebDriverRunner.url();
        logger.info("Проверка загрузки страницы. URL: {}", currentUrl);
        return !currentUrl.contains("/new") && !currentUrl.equals("https://github.com/");
    }
    /**
     * Проверяет, отображается ли репозиторий в списке
     */
    public boolean isRepositoryInList(String repoName) {
        logger.info("Проверка наличия репозитория {} в списке", repoName);
        return $x(String.format(REPO_IN_LIST_XPATH, repoName)).isDisplayed();
    }

    /**
     * Открывает репозиторий по имени (статический метод для перехода)
     */
    public static RepositoryPage openRepository(String repoName) {
        logger.info("Открытие репозитория: {}", repoName);
        Link.byContainsText(repoName).click();
        return new RepositoryPage();
    }

    /**
     * Нажимает на вкладку Pull requests (альтернативный метод)
     */
    public PullRequestsPage clickPullRequestsTab() {
        logger.info("Нажатие на вкладку Pull requests");
        Link.byHref("/pulls").click();
        return new PullRequestsPage();
    }

    /*public boolean isFilePresent(String fileName) {
        logger.info("Проверка наличия файла: {}", fileName);
        return $x(String.format(FILE_EXISTS_XPATH, fileName)).isDisplayed();
    }*/
}
