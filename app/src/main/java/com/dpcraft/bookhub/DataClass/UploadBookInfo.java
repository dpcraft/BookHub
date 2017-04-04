package com.dpcraft.bookhub.DataClass;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Jim on 13-7-10.
 */
public class UploadBookInfo implements Parcelable {

    private String mTitle="";
    private Bitmap mBitmap;
    private String mAuthor="";
    private String mPublisher="";
    private String mPublishDate="";
    private String mISBN="";
    private String mPrice = "";

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
    public void setPublisher(String Publisher)
    {
        mPublisher=Publisher;
    }
    public void setPrice(String price){mPrice = price;}
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
    public String getPublisher()
    {
        return mPublisher;
    }
    public String getPrice(){return mPrice;}

    public static final Parcelable.Creator<UploadBookInfo> CREATOR = new Creator<UploadBookInfo>() {

        @Override
        public UploadBookInfo createFromParcel(Parcel source) {
            UploadBookInfo bookInfo = new UploadBookInfo();
            bookInfo.mTitle = source.readString();
            bookInfo.mBitmap = source.readParcelable(Bitmap.class.getClassLoader());
            bookInfo.mAuthor = source.readString();
            bookInfo.mPublisher = source.readString();
            bookInfo.mPublishDate = source.readString();
            bookInfo.mISBN = source.readString();
            bookInfo.mPrice = source.readString();
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
        dest.writeString(mPublisher);
        dest.writeString(mPublishDate);
        dest.writeString(mISBN);
        dest.writeString(mPrice);
    }

}
