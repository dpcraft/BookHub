package com.dpcraft.bookhub.DataClass;

/**
 * Created by DPC on 2017/4/15.
 */
public class BookDetailsIncludeUser {
    private UserInfo user;
    private BookDetails book;

    public BookDetails getBook() {
        return book;
    }

    public void setBook(BookDetails book) {
        this.book = book;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }


}
