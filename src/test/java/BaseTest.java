import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.AfterEach;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class BaseTest {
    AndroidDriver driver;

    public void setUp(String appPath) throws URISyntaxException, MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setAppWaitActivity("*")
                .setUdid("emulator-5554")
                .setApp(appPath); // Путь к приложению
                ;
                // Путь к chromedriver, но он должен быть последним, т.к. не возвращает экземпляр capabilities
                ;
        driver = new AndroidDriver(
                new URI("http://127.0.0.1:4723").toURL(), options
        );
    }

    @AfterEach
    public void tearDown() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        driver.quit();
    }
}
