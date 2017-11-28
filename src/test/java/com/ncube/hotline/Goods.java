package com.ncube.hotline;

import org.openqa.selenium.WebElement;

/**
 * Created by mykhail on 24.11.17.
 */
public class Goods implements Comparable<Goods>{

    Integer index;
    Integer amountResponse;
    Integer amountWarranty;
    Double amountPrice;
    WebElement priceElement;

    public Goods(Integer amountResponse, Integer amountWarranty, Double amountPrice, WebElement priceElement) {
        // this.index = index;


        this.amountResponse = amountResponse;
        this.amountWarranty = amountWarranty;
        this.amountPrice = amountPrice;
        this.priceElement = priceElement;
    }


    @Override
    public String toString() {
        return "Product:\n\tResponses number: " + getAmountResponse() + "\n\tWarranty: " + getAmountWarranty() + "\n\tPrice: " + getAmountPrice();
    }

    public Integer getIndex() {
        return index;
    }

    @Override
    public int compareTo(Goods Good)
    {
        return Double.compare(this.getAmountPrice(), Good.getAmountPrice());
    }



    public void setIndex(Integer index) {
        this.index = index;
    }


    public Integer getAmountResponse() {
        return amountResponse;
    }

    public void setAmountResponse(Integer amountResponse) {
        this.amountResponse = amountResponse;
    }

    public Integer getAmountWarranty() {
        return amountWarranty;
    }

    public void setAmountWarranty(Integer amountWarranty) {
        this.amountWarranty = amountWarranty;
    }

    public Double getAmountPrice() {
        return amountPrice;
    }

    public void setAmountPrice(Double amountPrice) {
        this.amountPrice = amountPrice;
    }

    public WebElement getPriceElement() {
        return priceElement;
    }

    public void setPriceElement(WebElement priceElement) {
        this.priceElement = priceElement;
    }
}
