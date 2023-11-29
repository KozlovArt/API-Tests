package helpers;

import api.Authorization;
import io.restassured.response.Response;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        Authorization auth = new Authorization();
        Response authResponse = auth.getAuthResponse();

        open("/images/gplaypattern.jpg");
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));
    }

}
