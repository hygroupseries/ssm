package com.soecode.lyf.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 图书实体（Oracle book 表：id/name/author/price/publish_date）
 */
public class BookInfo {

    private Long id;
    private String name;
    private String author;
    private BigDecimal price;
    private Date publishDate;

    public BookInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "BookInfo{id=" + id + ", name='" + name + "', author='" + author
                + "', price=" + price + ", publishDate=" + publishDate + "}";
    }
}
