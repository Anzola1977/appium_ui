package org.example;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WebViewPage {
    protected final AndroidDriver driver;

    @AndroidFindBy(accessibility = "WebView")
    private WebElement webView;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Kevin Lamping\"]")
    private WebElement link;

    @AndroidFindBy(xpath = "//android.widget.Button[@text=\"Toggle navigation bar\"]")
    private WebElement menuBurger;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc=\"Community\"]")
    private WebElement community;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Need Help?\"]")
    private WebElement textLocation;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Office Hours »\"]")
    private WebElement officeHoursLink;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Contributing to a big Open Source project can be hard. Especially if the codebase is large and requires a lot of context to understand what certain changes do. Then there is the possibility that tools or infrastructure is being used that are unknown. Nevertheless the project depends on your contributions and help to identify bugs and fix them. How can we overcome this hurdle?\"]")
    private WebElement text;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Office Hours\"]")
    private WebElement officeHours;

    public WebViewPage(AppiumDriver driver) {
        this.driver = (AndroidDriver) driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void switchToWebView() {
        Set<String> contextNames = driver.getContextHandles();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        wait.until(d -> contextNames.size() > 1);
        //driver.context("WEBVIEW");
        driver.context(contextNames.toArray()[1].toString());
    }

    public void switchToNewWebView(){
        //String firstHandle = driver..getContextHandles();
        Set<String> contextNames = driver.getContextHandles();

        for (String contextHandle : contextNames) {
                driver.switchTo().defaultContent();
            }
        }


    public void switchToNativeApp() {
        driver.context("NATIVE_APP");
    }

    public String getMainPageText() {
        HomePage homePage = new HomePage(driver);
        homePage.openMenu("Webview");
        switchToWebView();
        return driver.findElement(By.cssSelector("header > div > p"))
                .getText();
    }

    public String findText() {
        HomePage homePage = new HomePage(driver);
        homePage.openMenu("Webview");
        menuBurger.click();
        community.click();
        vertikalSwipe();
        officeHoursLink.click();
        return text.getText();
    }

    public void vertikalSwipe() {
        Point source = link.getLocation();
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(
                finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), source.x, source.y));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(
                finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), source.x, source.y - (source.x * 20)));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(new Pause(finger, Duration.ofMillis(600)));
        driver.perform(List.of(swipe));
    }
}
