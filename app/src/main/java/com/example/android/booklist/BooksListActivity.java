package com.example.android.booklist;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sweety Jain on 11/14/2017 at 1:49 PM.
 */

public class BooksListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BookList>> {
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;
    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;
    /** Adapter for the list of earthquakes */
    private BookListAdapter mAdapter;
    /** URL for earthquake data from the USGS dataset */
    //private static final String USGS_REQUEST_URL =
    //      "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";
    private  String BOOKS_REQUEST_URL ;
    // public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private String authorKeyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);
        Intent myIntent = getIntent();
        Bundle b = myIntent.getExtras();//bundle is used to pass object from one activity to another activity via intent.
        if (b != null) {
            authorKeyword = (String) b.get("keyword");
            BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=intitle:"+authorKeyword;
            Log.v("BooksListActivity.this", BOOKS_REQUEST_URL);
            //searchTextView.setText(j + " starts!!");
        }
        //   if(isConnected == )
        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        // loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        // Create a fake list of earthquake locations.
        //  ArrayList<String> earthquakes = new ArrayList<>();
        //  ArrayList<EarthQuake> earthquakes = QueryUtils.extractEarthquakes();

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView)findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);
        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter= new BookListAdapter(
                this,new ArrayList<BookList>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);
//        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                // Find the current earthquake that was clicked on
//                EarthQuake currentEarthquake = mAdapter.getItem(position);
//
//                // Convert the String URL into a URI object (to pass into the Intent constructor)
//                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
//
//                // Create a new intent to view the earthquake URI
//                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
//
//                // Send the intent to launch a new activity
//                startActivity(websiteIntent);
//            }
//        });
// Get a reference to the LoaderManager, in order to interact with loaders.
        // LoaderManager loaderManager = getLoaderManager();
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    @Override
    public Loader<List<BookList>> onCreateLoader(int i, Bundle bundle) {

      /*  SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );*/

        Uri baseUri = Uri.parse(BOOKS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
       // uriBuilder.appendQueryParameter("minmag", minMagnitude);
        //uriBuilder.appendQueryParameter("orderby", orderBy);

        return new BookListLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<BookList>> loader, List<BookList> books) {
        ProgressBar progressView = (ProgressBar)findViewById(R.id.loading_spinner);
        progressView.setVisibility(View.GONE);
       // Log.v("EarthQuakeActivity","In onLoadFinished by sweety");
        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_books);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<BookList>> loader) {
       // Log.v("EarthQuakeActivity","In onLoaderReset by sweety");
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

}
