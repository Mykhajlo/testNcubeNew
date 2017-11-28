package com.ncube.hotline.tests;

import static com.ncube.hotline.Utils.openPhonePage;
import static com.ncube.hotline.Utils.setupEnvironment;

import com.ncube.hotline.Goods;
import com.ncube.hotline.PageElements;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


///ddsd

import java.util.*;




public class HotlineTest {

    private static final Logger LOGGER = Logger.getLogger( HotlineTest.class);

    public static WebDriver driver;

    @BeforeClass
    public void setUp() throws InterruptedException {
        driver = setupEnvironment();
    }

    @BeforeMethod
    public void openPage() throws InterruptedException {
        openPhonePage(driver);
    }

    @AfterClass
    public void tearDown() {
//      driver.close();
        driver.quit();
    }
    @Test
    public void HotlineTestFeatures () throws Exception {
//
//        FluentWait<WebDriver> wait = new FluentWait<>(driver)
//                .withTimeout(30, SECONDS)
//                .pollingEvery(500, MILLISECONDS)
//                .ignoring(NoSuchElementException.class);

        LOGGER.info("You are at phone page");

        PageElements.clickAllSuggestions(driver); // Open all Suggestions

        Thread.sleep(3000);

        // Responses

        List<WebElement> webElementResponses = driver.findElements(By.xpath("//a[@data-tracking-id='offer-1']")); // Responce all (webelements)
        List<WebElement> webElementWarranties = driver.findElements(By.xpath("//div[@class='cell-2 cell-md m_b-sm']"));
        List<WebElement> webElementPrices = driver.findElements(By.xpath("//a[@class='price-lg']")); // webElementPrices all (webelements)

        LOGGER.info("Total responses = " + webElementResponses.size());
        LOGGER.info("Total warranties = " + webElementWarranties.size());
        LOGGER.info("Total prices = " + webElementPrices.size());

        int[] responses = new int[webElementResponses.size()];
        int[] warranties = new int[webElementWarranties.size()];
        double[] prices = new double[webElementPrices.size()];

        Iterator <WebElement> iterator = webElementResponses.iterator();

        for (int i = 0; iterator.hasNext(); i++) {
            WebElement itemR= iterator.next();
            String labelR = itemR.getText();
            responses[i] = Integer.parseInt(labelR.trim().replaceAll("\\D+","").replaceAll("\\s",""));

           // LOGGER.info("Results Response = " + responses[i]);
        }

        // Warranties

        iterator = webElementWarranties.iterator();

        for (int i = 0; iterator.hasNext(); i++) {
            WebElement itemW = iterator.next();
            String labelW = itemW.getText();
            warranties[i] = Integer.parseInt(labelW.substring(0, 2).trim().replaceAll("\\D+","0").replaceAll("\\s",""));

            //LOGGER.info("Results Warranty = " + warranties[i]);
        }

        // Prices

        iterator = webElementPrices.iterator();

        for (int i = 0; iterator.hasNext(); i++) {
            WebElement itemP = iterator.next();
            String labelP = itemP.getText();
            prices[i] = Double.parseDouble(labelP.trim().replace(",",".").replaceAll("\\s",""));

            //LOGGER.info("Results Price = " + prices[i]);
        }


        TreeSet<Goods> Products = new TreeSet<>();

        for (int i = 0; i < webElementPrices.size(); i++) {

            Goods Good = new Goods(responses[i], warranties[i], prices[i], webElementPrices.get(i));

            Products.add(Good);

            //LOGGER.info(Good.toString());

        }


        for (Goods good : Products)
            if (good.getAmountWarranty() > 5 && good.getAmountResponse() > 9) {
                LOGGER.info("Result is found: " + good.toString());
                good.getPriceElement().click();

                Assert.assertTrue(driver.getPageSource().contains("Apple iPhone 8"));
                LOGGER.info("Test is passed");

                //LOGGER.info("Gooooooo-> " + good.getAmountPrice().toString().substring(0, good.getAmountPrice().toString().indexOf('.')));
              /*  if (driver.getPageSource().contains("Apple iPhone 8")) { //    && driver.getPageSource().contains((good.getAmountPrice().toString().substring(0, good.getAmountPrice().toString().indexOf('.'))))
                    LOGGER.info("Bingo");
                    LOGGER.info("Test is passed");
                }*/
                break;
            }

        Thread.sleep(5000);

    }
}
