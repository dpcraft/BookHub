package com.dpcraft.bookhub.DataClass;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jim on 13-7-10.
 */
public class UploadBookInfo implements Parcelable {

    private String mTitle="";
    private Bitmap mBitmap;
    private String mAuthor="";
    private String mPublishHouse ="";
    private String mPublishDate="";
    private String mISBN="";
    private String mOriginPrice = "";
    private String mIsSold;
    private String mDeposit = "";
    private String mIntroduction = "";
    private String price = "";



    private String bookType = "";


    public void setTitle(String Title)
    {
        mTitle=Title;
    }
    public void setBitmap(Bitmap bitmap)
    {
        mBitmap=bitmap;
    }
    public void setAuthor(String Author)
    {
        mAuthor=Author;
    }
    public void setISBN(String ISBN)
    {
        mISBN=ISBN;
    }
    public void setPublishDate(String PublishDate)
    {
        mPublishDate=PublishDate;
    }
    public void setPublishHouse(String PublishHouse)
    {
        mPublishHouse = PublishHouse;
    }
    public void setOriginPrice(String originPrice){
        mOriginPrice = originPrice;}
    public String getTitle() {return mTitle;}
    public Bitmap getBitmap()
    {
        return mBitmap;
    }
    public String getAuthor() {return mAuthor;}
    public String getISBN()
    {
        return mISBN;
    }
    public String getPublishDate()
    {
        return mPublishDate;
    }
    public String getPublishHouse()
    {
        return mPublishHouse;
    }
    public String getOriginPrice(){return mOriginPrice;}
    public String getmIsSold() {
        return mIsSold;
    }

    public void setmIsSold(String mIsSold) {
        this.mIsSold = mIsSold;
    }

    public String getmDeposit() {
        return mDeposit;
    }

    public void setmDeposit(String mDeposit) {
        this.mDeposit = mDeposit;
    }

    public String getmIntroduction() {
        return mIntroduction;
    }

    public void setmIntroduction(String mIntroduction) {
        this.mIntroduction = mIntroduction;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }


    public static final Parcelable.Creator<UploadBookInfo> CREATOR = new Creator<UploadBookInfo>() {

        @Override
        public UploadBookInfo createFromParcel(Parcel source) {
            UploadBookInfo bookInfo = new UploadBookInfo();
            bookInfo.mTitle = source.readString();
            bookInfo.mBitmap = source.readParcelable(Bitmap.class.getClassLoader());
            bookInfo.mAuthor = source.readString();
            bookInfo.mPublishHouse = source.readString();
            bookInfo.mPublishDate = source.readString();
            bookInfo.mISBN = source.readString();
            bookInfo.mOriginPrice = source.readString();
            return bookInfo;
        }

        @Override
        public UploadBookInfo[] newArray(int size) {
            return new UploadBookInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeParcelable(mBitmap, flags);
        dest.writeString(mAuthor);
        dest.writeString(mPublishHouse);
        dest.writeString(mPublishDate);
        dest.writeString(mISBN);
        dest.writeString(mOriginPrice);
    }

}
