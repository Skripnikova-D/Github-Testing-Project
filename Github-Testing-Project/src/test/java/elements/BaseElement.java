package elements;


import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;

import java.lang.reflect.UndeclaredThrowableException;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Базовый класс для всех элементов на странице (кнопок, полей ввода, ссылок).
 * Содержит общую логику поиска элементов и проверки их видимости.
 */
public class BaseElement{
    protected static final int WAIT_SECONDS = 5;    //время, которое Selenide будет ждать появления элемента на странице
    protected final SelenideElement baseElement;    //сылка на элемент в браузере
    private static final Logger logger = LoggerFactory.getLogger(BaseElement.class);    //Логгер для записи сообщений о работе класса

    /**
     * Конструктор с подстановкой значения в шаблон XPath.
     * Пример: new BaseElement("//button[@id='%s']", "submit-btn")
     *
     * @param xpath          Шаблон XPath с %s
     * @param attributeValue Значение для подстановки вместо %s
     */
    protected BaseElement(String xpath, String attributeValue) {
        String fullXpath = String.format(xpath, attributeValue);
        logger.info("Поиск элемента по XPath: {}", fullXpath);
        baseElement = $x(fullXpath);
    }

    /**
     * Конструктор для готового XPath (без подстановки).
     * Пример: new BaseElement("//button[@id='submit-btn']")
     *
     * @param xpath Полный XPath элемента
     */
    protected BaseElement(String xpath) {
        logger.info("Поиск элемента по готовому XPath: {}", xpath);
        baseElement = $x(xpath);
    }

    /**
     * Проверяет, отображается ли элемент на странице.
     * Сначала ждёт до WAIT_SECONDS секунд, пока элемент станет видимым.
     * Если элемент появился — возвращает true.
     * Если элемент не появился или его нет на странице — возвращает false.
     *
     * @return true, если элемент виден; false, если нет
     */
    public boolean isDisplayed() {
        try {
            logger.debug("Проверка видимости элемента: {}", baseElement);
            return baseElement.shouldBe(visible, Duration.ofSeconds(WAIT_SECONDS)).isDisplayed();
        } catch (UndeclaredThrowableException | ElementNotFound e) {
            logger.warn("Элемент не найден или не стал видимым за {} секунд", WAIT_SECONDS);
            return false;
        }
    }
}