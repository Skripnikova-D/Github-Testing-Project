package pages;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.codeborne.selenide.Selenide.$x;

/**
 * Базовый класс для всех страниц.
 * Содержит общие методы для работы со страницами.
 */
public class BasePage {
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected static final String BODY_XPATH = "//body";

    /**
     * Проверяет, загружена ли страница (по умолчанию - проверка body)
     */
    public boolean isLoaded() {
        return $x(BODY_XPATH).isDisplayed();
    }

}
