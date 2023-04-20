package gmail.asteroster.allure;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.attachment;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class StepsTest {
    private static final String REPOSITORY = "LutSerg/files_18";
    private static final String ISSUE = "test_issue";

    @Test
    @DisplayName("Лямбда шаги через step (name, () -> {})")
    public void testLambdaSteps() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        step("Открываем главную сраницу", () -> {
            open("https://github.com");
        });

        step("Ищем репозиторий" + REPOSITORY, () -> {
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(REPOSITORY);
            $(".header-search-input").submit();
        });

        step ("Кликаем по ссылке репозитория" + REPOSITORY, () -> {
        $(linkText(REPOSITORY)).click();
        });

        step("Открываем таб Issues", () -> {
        $("#issues-tab").click();
        });

        step("Проверяем наличие Issue с названием " + ISSUE, () -> {
        $(withText( ISSUE)).should(Condition.exist);
        });
    }

    @Test
    @DisplayName("Шаги с аннотацией @Step")
    public void testAnnotatedStep () {
        WebSteps steps = new WebSteps();
        SelenideLogger.addListener("allure", new AllureSelenide());

        steps.openMainPage();
        steps.takeScreenshot();
        steps.searchForRepository(REPOSITORY);
        steps.takeScreenshot();
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.takeScreenshot();
        steps.openIssuesTab();
        steps.takeScreenshot();
        steps.shouldSeeIssueWithNumber(ISSUE);
        steps.takeScreenshot();
        attachment("Source", webdriver().driver().source());
    }
}
