package com.example.android.booklist;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import java.util.List;

/**
 * Created by Sweety Jain on 11/15/2017 at 9:55 AM.
 */








/**
 * Loads a list of earthquakes by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class BookListLoader extends AsyncTaskLoader<List<BookList>> {

    /** Tag for log messages */
    private static final String LOG_TAG = BookListLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link EarthquakeLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public BookListLoader(Context context, String url) {
        // Log.v("EarthquakeLoader.java","in constructor");
        super(context);
        mUrl = url;
      //  Log.v("EarthquakeLoader.java","in constructor");
    }

    @Override
    protected void onStartLoading() {
     //   Log.v("EarthquakeLoader.java","in forceload methos");
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<BookList> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
       // Log.v("EarthquakeLoader.java","in loadinbackground");
        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<BookList> books = QueryUtils.fetchBookListData(mUrl);
        return books;
    }
}