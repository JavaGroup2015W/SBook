package com.gdeer.sbook.bean;

/**
 * Created by caisi on 2016/2/8.
 */
public class SellerItem {
    private String UserName;
    private Double price;
    private String area;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
