package com.example.android.booklist;

/**
 * Created by Sweety Jain on 11/14/2017 at 10:43 AM.
 */


public class BookList {

    private String mAuthor;

    private String mTitle;

    public BookList(String author, String title){
      mAuthor = author;
        mTitle = title;
    }
    /**
     * Returns the website URL to find more information about the earthquake.
     */
    public String getmAuthor() {
        return mAuthor;
    }
    public String getmTitle() {
        return mTitle;
    }

}
