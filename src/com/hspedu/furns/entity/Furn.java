package com.hspedu.furns.entity;

import java.math.BigDecimal;

public class Furn {
    /*
    CREATE TABLE `furn`(
    `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT, #id
    `name` VARCHAR(64) NOT NULL, #家居名
    `maker` VARCHAR(64) NOT NULL, #制造商
    `price` DECIMAL(11,2) NOT NULL , #价格 定点数
    `sales` INT UNSIGNED NOT NULL, #销量
    `stock` INT UNSIGNED NOT NULL, #库存
    `img_path` VARCHAR(256) NOT NULL #存放图片的路径
    )CHARSET utf8 ENGINE INNODB
     */
    private Integer id; // Integer可以传空 防止空指针 表里如果没有限制nut null 传null给int会空指针

    private String name;

    private String maker;

    private BigDecimal price;

    private Integer sales;

    private Integer stock = 1000000;

    // 表字段img_path
    private String imgPath = "assets/images/product-image/default.jpg";

    public Furn() {
    }

    public Furn(Integer id, String name, String maker, BigDecimal price, Integer sales, Integer stock, String imgPath) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.sales = sales;
        this.stock = stock;
        if (!(null == imgPath || "".equals(imgPath))) {
            this.imgPath = imgPath;
            System.out.println("null != imgPath");
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "Furn{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maker='" + maker + '\'' +
                ", price=" + price +
                ", sales=" + sales +
                ", stock=" + stock +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
