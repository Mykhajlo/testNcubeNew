package com.ncube.hotline;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.By.className;

/**
 * Created by mykhail on 21.11.17.
 */
public class Utils {
    private static final Logger LOGGER = Logger.getLogger(Utils.class);

    public static void switchToIframe(WebDriver driver) throws InterruptedException {
        driver.switchTo().frame(driver.findElement(By.id("iframeContainer")));
        LOGGER.info("Bingo! You are at iframeContainer");
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5, 10000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"game\"]/div[1]/main-header/header/div/div/div[2]/div[2]"))); // find Balance
            LOGGER.info("Balance is present");
        } catch (Throwable e) {
            LOGGER.info("Error while switching to the frame. " + e.getMessage());
        }
        // Thread.sleep(1000);
    }

    public static ChromeDriver setupEnvironment() {
        //System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver.dmg");
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            System.setProperty("webdriver.chrome.driver", "src//test//resources//chromedriver_win.exe");
        }
        if (osName.contains("mac")) {
            System.setProperty("webdriver.chrome.driver", "src//test//resources//chromedriver");
        }
        //linux
        ChromeDriver driver = new ChromeDriver();
        Dimension d = new Dimension(1400, 900); // > HD resolution
        driver.manage().window().setSize(d);
        //driver.manage().window().maximize(); // full size  of screen
        return driver;
    }

    public static void openPhonePage(WebDriver driver) throws InterruptedException {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(30, SECONDS)
                .pollingEvery(500, MILLISECONDS)
                .ignoring(NoSuchElementException.class);
        driver.get("http://hotline.ua");
        driver.getWindowHandle();
        Thread.sleep(2000);
        // Fill 'Login'   field
        //WebDriverWait wait = new WebDriverWait(driver, 30);

        if (ofNullable(driver.findElement(By.xpath("//*[@id=\"page-index\"]/header/div[1]/div/div/div[1]/div/div[2]/div/div[2]/div"))).isPresent()) {
            LOGGER.info("Element is present");
            driver.findElement(By.xpath("//*[@id=\"page-index\"]/header/div[1]/div/div/div[1]/div/div[2]/div/div[2]/div/i")).click();
        } else {
            LOGGER.info("Element is not displayed");
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchbox")));// "Search" field is visible
        driver.findElement(By.id("searchbox"))
                .sendKeys("iPhone");
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);

        //PageLoadTimeout Command
        wait.until(ExpectedConditions.visibilityOfElementLocated(className("ui-menu")));

        List<WebElement> linksToClick = driver.findElements(className("ui-menu-item-wrapper")); // results

        Optional<WebElement> webElementOptional = linksToClick
                .stream()
                .filter(element -> element.getText().contains("Apple iPhone 8"))
                .findFirst();
        if (webElementOptional.isPresent()) {
            LOGGER.info("Element = " + webElementOptional.get().getText());
            webElementOptional.get().click();
        } else {
            LOGGER.info("Element is absent");
        }

        for (String handleGame : driver.getWindowHandles()) {
            driver.switchTo().window(handleGame);
        }
        LOGGER.info(driver.getCurrentUrl());
        //Open url
    }

}
