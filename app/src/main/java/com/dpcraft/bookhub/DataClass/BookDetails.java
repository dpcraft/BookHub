package com.dpcraft.bookhub.DataClass;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DPC on 2017/3/14.
 */
public class BookDetails {
    @SerializedName("bookid")
    private int Id;
    //书籍基本信息
    @SerializedName("bookname")
    private String Name;
    @SerializedName("author")
    private String author;
    @SerializedName("publish")
    private String publishHouse;
    @SerializedName("pubTime")
    private String publishDate;
    @SerializedName("orig")
    private String originPrice;//原价
    @SerializedName("version")
    private String version;
    @SerializedName("isbn")
    private String ISBN;
    @SerializedName("type")
    private String type;
    //租售信息

    @SerializedName("isSell")
    private Boolean isSell;

    //private String endtime;

    @SerializedName("deposit")
    private String deposit;
    @SerializedName("price")
    private String price;
    @SerializedName("introduction")
    private String introduction;
    @SerializedName("userid")
    private String userId;

    //交易信息
    @SerializedName("intention")
    private Boolean intention;
    @SerializedName("taruserid")
    private String  tarUserId;
    @SerializedName("selled")
    private Boolean isSold;
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishHouse() {
        return publishHouse;
    }

    public void setPublishHouse(String publishHouse) {
        this.publishHouse = publishHouse;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {

        this.publishDate = publishDate;
    }

    public String getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(String originPrice) {
        this.originPrice = originPrice;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSell() {
        return isSell;
    }

    public void setSell(Boolean sell) {
        isSell = sell;
    }

//    public String getEndtime() {
//        return endtime;
//    }
//
//    public void setEndtime(String endtime) {
//        this.endtime = endtime;
//    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getIntention() {
        return intention;
    }

    public void setIntention(Boolean intention) {
        this.intention = intention;
    }

    public String getTarUserId() {
        return tarUserId;
    }

    public void setTarUserId(String tarUserId) {
        this.tarUserId = tarUserId;
    }

    public Boolean getSold() {
        return isSold;
    }

    public void setSold(Boolean sold) {
        isSold = sold;
    }




}
