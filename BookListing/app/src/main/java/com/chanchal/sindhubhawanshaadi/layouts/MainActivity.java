package com.chanchal.sindhubhawanshaadi.layouts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;

import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.Context;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<BookList>> {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static  String Book_List_Api = "https://www.googleapis.com/books/v1/volumes";

    private static final int Book_LOADER_ID = 1;

    private static ListView list;

    private static String searchQuery;

    private static  TextView emptyScreen;

    private  static ProgressBar pb;

    private static  BookAdapter adapter;

    private static TextView SearchBar;

    private static Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = cm.getActiveNetworkInfo();

//        by using AsyncTask
//        BookApiTask books = new BookApiTask();
//        books.execute(Book_List_Api);


        list = (ListView) findViewById(R.id.list);

        adapter = new BookAdapter(MainActivity.this,new ArrayList<BookList>());

        list.setAdapter(adapter);

        emptyScreen = (TextView) findViewById(R.id.emptyScreenText);
        list.setEmptyView(emptyScreen);

        pb = findViewById(R.id.progressBar);

        searchButton = (Button) findViewById(R.id.searchButton);

        SearchBar = (TextView) findViewById(R.id.searchBar);

        if(netInfo!=null && netInfo.isConnectedOrConnecting())
        {
            android.app.LoaderManager bookLoader = getLoaderManager();
            bookLoader.initLoader(Book_LOADER_ID , null,this);
        }
        else
        {
            pb.setVisibility(View.GONE);
            emptyScreen.setText("No internet Connection , please try again");
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchBar.clearFocus();
                emptyScreen.setText("");
                QueryUtils.hideSoftKeyboard(MainActivity.this);
                adapter.clear();
                searchQuery = SearchBar.getText().toString();

                if(TextUtils.isEmpty(searchQuery))
                {
                    SearchBar.setError("Please Enter something");
                }
                else
                {
                    pb.setVisibility(View.VISIBLE);
                    getLoaderManager().restartLoader(Book_LOADER_ID , null , MainActivity.this);
                }
            }

        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BookList book = adapter.getItem(i);
                String buyLink = book.getBuyLink();
                Uri url = Uri.parse(buyLink);

                Intent intent = new Intent(Intent.ACTION_VIEW , url);
                startActivity(intent);
            }
        });
    }


    @Override
    public Loader<ArrayList<BookList>> onCreateLoader(int id,  Bundle args) {
        Log.i("There is no instance", ": Created new one loader at the beginning!");

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String maxBookResults = sharedPrefs.getString(
          getString(R.string.settings_book_key),
          getString(R.string.settings_book_default)
        );
        Uri baseUri = Uri.parse(Book_List_Api);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q" , searchQuery);
        uriBuilder.appendQueryParameter("format" , "geojson");
        uriBuilder.appendQueryParameter("maxResults" , maxBookResults);
        uriBuilder.appendQueryParameter("filter" , "paid-ebooks");
        return new BooksAsyncLoader(this , uriBuilder.toString());
    }

    @Override
    public void onLoadFinished( Loader<ArrayList<BookList>> loader, ArrayList<BookList> data) {

        pb = findViewById(R.id.progressBar);

        pb.setVisibility(View.GONE);

        emptyScreen.setText("No such books available");

        adapter.clear();

        if(data!=null && !data.isEmpty())
        {
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<BookList>> loader) {
        adapter.clear();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main , menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_settings)
        {
            Intent settingsIntent = new Intent(this , bookSettings.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



//    private  static class BookApiTask extends AsyncTask<String, Void, ArrayList<BookList>> {
//
//        protected ArrayList<BookList> doInBackground(String... urls) {
//           if(urls.length == 0 || urls == null)
//           {
//               return null;
//           }
//           else{
//               ArrayList<BookList> books = QueryUtils.fetchBookList(urls[0]);
//               return books;
//           }
//        }
//
//        protected void onPostExecute(ArrayList<BookList> book) {
//                adapter.clear();
//                if(book.size()!=0 || !book.isEmpty())
//                {
//                    adapter.addAll(book);
//                }
//        }
//    }

    private static  class BooksAsyncLoader extends AsyncTaskLoader<ArrayList<BookList>> {

        private String url;
        public BooksAsyncLoader (Context content , String url) {
            super(content);
            this.url = url;
        }

        @Override
        public ArrayList<BookList> loadInBackground() {
            if(url == null)
            {
                return null;
            }
            Log.i("book", ": fetched");

            ArrayList<BookList> book = QueryUtils.fetchBookList(url);
            return book;
        }

        protected void onStartLoading(){
            forceLoad();
        }
    }



}

