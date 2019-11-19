package com.saucelabs.example.features;

import com.saucelabs.example.pages.InventoryPage;
import com.saucelabs.example.pages.LoginPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class LoginFeatures extends BaseFeature
{
    @Test
    public void verifyValidUsersCanSignIn()
    throws MalformedURLException
    {
        URL url = new URL("https://ondemand.saucelabs.com:443/wd/hub");

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("name", "Verify Valid Users Can Sign In");
        sauceOptions.setCapability("build", "build-1234");

        sauceOptions.setCapability("extendedDebugging", false);
        sauceOptions.setCapability("capturePerformance", false);

        RemoteWebDriver driver = createDesktopDriver("chrome", "78.0", "Windows 10", sauceOptions);

        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);

        JavascriptExecutor jsExec = (JavascriptExecutor)driver;
        jsExec.executeScript("sauce:context=>>> Verify we are on the Inventory Page");

        loginPage.navigateTo(LoginPage.PAGE_URL);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");

        loginPage.clickLogin();
        inventoryPage.waitForPageLoad();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, InventoryPage.PAGE_URL, "Current URL does not match Expected");

        jsExec.executeScript("sauce:job-result=true");
        driver.quit();
    }
}
