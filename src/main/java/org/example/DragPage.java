package org.example;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static java.time.Duration.ofMillis;

public class DragPage {
    protected final AppiumDriver driver;

    @AndroidFindBy(accessibility = "drag-l1")
    private WebElement dragLeftFirst;
    @AndroidFindBy(accessibility = "drop-l1")
    private WebElement dropLeftFirst;
    @AndroidFindBy(accessibility = "drag-l2")
    private WebElement dragLeftSecond;
    @AndroidFindBy(accessibility = "drop-l2")
    private WebElement dropLeftSecond;
    @AndroidFindBy(accessibility = "drag-l3")
    private WebElement dragLeftThird;
    @AndroidFindBy(accessibility = "drop-l3")
    private WebElement dropLeftThird;
    @AndroidFindBy(accessibility = "drag-c1")
    private WebElement dragMiddleFirst;
    @AndroidFindBy(accessibility = "drop-c1")
    private WebElement dropMiddleFirst;
    @AndroidFindBy(accessibility = "drag-c2")
    private WebElement dragMiddleSecond;
    @AndroidFindBy(accessibility = "drop-c2")
    private WebElement dropMiddleSecond;
    @AndroidFindBy(accessibility = "drag-c3")
    private WebElement dragMiddleThird;
    @AndroidFindBy(accessibility = "drop-c3")
    private WebElement dropMiddleThird;
    @AndroidFindBy(accessibility = "drag-r1")
    private WebElement dragRightFirst;
    @AndroidFindBy(accessibility = "drop-r1")
    private WebElement dropRightFirst;
    @AndroidFindBy(accessibility = "drag-r2")
    private WebElement dragRightSecond;
    @AndroidFindBy(accessibility = "drop-r2")
    private WebElement dropRightSecond;
    @AndroidFindBy(accessibility = "drag-r3")
    private WebElement dragRightThird;
    @AndroidFindBy(accessibility = "drop-r3")
    private WebElement dropRightThird;


    public DragPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private Point getElementCenter(WebElement element) {
        var location = element.getLocation();
        var size = element.getSize();

        return new Point(
                location.getX() + (size.getWidth() / 2),
                location.getY() + (size.getHeight() / 2)
        );
    }

    public void dragDropMethod(WebElement location, WebElement destination){
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence dragAndDrop = new Sequence(finger, 1);
        dragAndDrop.addAction(
                finger.createPointerMove(
                        ofMillis(100),
                        PointerInput.Origin.viewport(),
                        getElementCenter(location).x,
                        getElementCenter(location).y
                )
        );
        dragAndDrop.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        dragAndDrop.addAction(new Pause(finger, ofMillis(600)));
        dragAndDrop.addAction(
                finger.createPointerMove(
                        ofMillis(600),
                        PointerInput.Origin.viewport(),
                        getElementCenter(destination).x,
                        getElementCenter(destination).y
                )
        );
        dragAndDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(dragAndDrop));
    }

    public void dragAndDropPieces() {
        HomePage homePage = new HomePage(driver);
        homePage.openMenu("Drag");
        dragDropMethod(dragLeftFirst, dropLeftFirst);
        dragDropMethod(dragLeftSecond, dropLeftSecond);
        dragDropMethod(dragLeftThird, dropLeftThird);
        dragDropMethod(dragMiddleFirst, dropMiddleFirst);
        dragDropMethod(dragMiddleSecond, dropMiddleSecond);
        dragDropMethod(dragMiddleThird, dropMiddleThird);
        dragDropMethod(dragRightFirst, dropRightFirst);
        dragDropMethod(dragRightSecond, dropRightSecond);
        dragDropMethod(dragRightThird, dropRightThird);
    }

    public String congratulationsText() {
        return driver.findElement(AppiumBy.xpath("//android.widget.TextView")).getText();
    }
}
