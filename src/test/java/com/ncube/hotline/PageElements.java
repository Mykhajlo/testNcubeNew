package com.ncube.hotline;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by mykhail on 22.11.17.
 */
public class PageElements {
    public static void clickAllSuggestions(WebDriver driver) {
        //all Suggestions gg
        driver.findElement(By.xpath("//*[@id=\"page-product\"]/div[3]/div[1]/ul/li[2]/span")).click();// Click All Suggestions
    }
}
