package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Базовый класс для всех страниц.
 * Содержит общие методы для работы со страницами.
 */
public class BasePage {
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    /**
     * Проверяет, загружена ли страница (по умолчанию - проверка body)
     */
    public boolean isLoaded() {
        return $x("//body").isDisplayed();
    }

    /**
     * Получает текущий URL страницы
     */
    public String getCurrentUrl() {
        return Selenide.webdriver().driver().url();
    }

    /**
     * Получает заголовок страницы
     */
    public String getPageTitle() {
        return Selenide.title();
    }
}