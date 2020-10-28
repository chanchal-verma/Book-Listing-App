package com.chanchal.sindhubhawanshaadi.layouts;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.chanchal.sindhubhawanshaadi.layouts.MainActivity.LOG_TAG;

public class QueryUtils {

//    private static final String Book_List_Api = "{ \"kind\": \"books#volume\",\"id\": \"qKFDDAAAQBAJ\",\"etag\": \"XKQgxdG0tug\",\"selfLink\": \"https://www.googleapis.com/books/v1/volumes/qKFDDAAAQBAJ\",\"volumeInfo\": {  \"title\": \"Android\",  \"authors\": [    \"Dixit P.K.\"  ],  \"publisher\": \"Vikas Publishing House\",  \"publishedDate\": \"2014\",  \"description\": \"Android is a movement that has transferred data from laptop to hand-held devices like mobiles. Though there are alternate technologies that compete with Android, but it is the front runner in mobile technology by a long distance. Good knowledge in basic Java will help you to understand and develop Android technology and apps. Many universities in India and across the world are now teaching Android in their syllabus, which shows the importance of this subject. This book can be read by anyone who knows Java and XML concepts. It includes a lot of diagrams along with explanations to facilitate better understanding by students. This book aptly concludes with a project that uses Android, which will greatly benefit students in learning the practical aspects of Android. Key Features • Instructions in designing different Android user interfaces • Thorough explanations of all activities • JSON • Android-based project to aid practical understanding\",  \"industryIdentifiers\": [    {      \"type\": \"ISBN_10\",      \"identifier\": \"9325977885\"    },    {      \"type\": \"ISBN_13\",      \"identifier\": \"9789325977884\"    }  ],  \"readingModes\": {    \"text\": false,    \"image\": true  },  \"pageCount\": 372,  \"printedPageCount\": 385,  \"printType\": \"BOOK\",  \"categories\": [    \"Computers / Computer Science\"  ],  \"averageRating\": 3,  \"ratingsCount\": 1,  \"maturityRating\": \"NOT_MATURE\",  \"allowAnonLogging\": true,  \"contentVersion\": \"preview-1.0.0\",  \"panelizationSummary\": {    \"containsEpubBubbles\": false,    \"containsImageBubbles\": false  },  \"imageLinks\": {    \"smallThumbnail\": \"https://books.google.com/books/content?id=qKFDDAAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&imgtk=AFLRE71onjdZ3GzCzN6efEgJ9uhc55YHESmanK4NJpiiRf58C8gd3xJ9o82ZS4cr4-Eno_ytI87g1u9c3dYbm1jvyU6zdabmiIoqFzVT71WZtGHlOcsFu0d6RShXy4NtVP4egO09Md2n&source=gbs_api\",    \"thumbnail\": \"https://books.google.com/books/content?id=qKFDDAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE72wexUWp_9Hr2TU8qlAz78lgcs9PPXRng59wPGxxrSrMs7WnCKECtXS0ibWSxIWNCGlgqsfdybmP8pIK8TXKL9DMFLicAKN7UEmqZditNDNAaZ7WFZmhuybBdKvrs8y0IflUxKh&source=gbs_api\",    \"small\": \"http://books.google.com/books/content?id=qKFDDAAAQBAJ&printsec=frontcover&img=1&zoom=2&edge=curl&imgtk=AFLRE72AbONnfTYz2aLa-mfO30QiOAC6y6SBMt9CnTrnihWPYEQN_8YEnNE_c7oKzegHVRYanS8w2ChfjtppfF4CONkvsXqmekbFvi5gR_Hc6WxqOdOYSVFo1ilFCeuzV66UuXUD007G&source=gbs_api\"  },  \"language\": \"en\",  \"previewLink\": \"http://books.google.com/books?id=qKFDDAAAQBAJ&hl=&source=gbs_api\",  \"infoLink\": \"https://play.google.com/store/books/details?id=qKFDDAAAQBAJ&source=gbs_api\",  \"canonicalVolumeLink\": \"https://play.google.com/store/books/details?id=qKFDDAAAQBAJ\"},\"saleInfo\": {  \"country\": \"US\",  \"saleability\": \"FOR_SALE\",  \"isEbook\": true,  \"listPrice\": {    \"amount\": 15,    \"currencyCode\": \"USD\"  },  \"retailPrice\": {    \"amount\": 9.99,    \"currencyCode\": \"USD\"  },  \"buyLink\": \"https://play.google.com/store/books/details?id=qKFDDAAAQBAJ&rdid=book-qKFDDAAAQBAJ&rdot=1&source=gbs_api\",  \"offers\": [    {      \"finskyOfferType\": 1,      \"listPrice\": {        \"amountInMicros\": 15000000,        \"currencyCode\": \"USD\"      },      \"retailPrice\": {        \"amountInMicros\": 9990000,        \"currencyCode\": \"USD\"      },      \"giftable\": true    }  ]},\"accessInfo\": {  \"country\": \"US\",  \"viewability\": \"PARTIAL\",  \"embeddable\": true,  \"publicDomain\": false,  \"textToSpeechPermission\": \"ALLOWED\",  \"epub\": {    \"isAvailable\": false  },  \"pdf\": {    \"isAvailable\": true,    \"acsTokenLink\": \"http://books.google.com/books/download/Android-sample-pdf.acsm?id=qKFDDAAAQBAJ&format=pdf&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"  },  \"webReaderLink\": \"http://play.google.com/books/reader?id=qKFDDAAAQBAJ&hl=&printsec=frontcover&source=gbs_api\",  \"accessViewStatus\": \"SAMPLE\",  \"quoteSharingAllowed\": false}\n}";

    public static ArrayList<BookList> fetchBookList(String requestUrl)
    {
        ArrayList<BookList> book = new ArrayList<>();
        URL url = convertUrl(requestUrl);
        String jsonResponse = null ;
        try{
            jsonResponse = makeHttpRequest(url);
        }
        catch(IOException e){
            Log.e(LOG_TAG , "Error closing input stream" ,e);
        }
        book = extractBooks(jsonResponse);
        return book;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = null;
        if(url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");

            if(urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readInputStream(inputStream);
            }
            else
            {
                Log.e(LOG_TAG ,"Error Response code"+urlConnection.getResponseCode());
            }
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG ,"Problem retrieving the json data",e);
        }

        return jsonResponse;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream!=null) {
            InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(reader);
            String Line = bufferedReader.readLine();
            while (Line != null) {
                output.append(Line);
                Line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static URL convertUrl(String requestUrl) {
        if(requestUrl == null)
        {
            return null;
        }
        URL url = null;
        try{
            url = new URL(requestUrl);
        }
        catch (MalformedURLException e)
        {
            Log.v(LOG_TAG , "Problem building the url" , e);
        }
        return url;
    }

    public static ArrayList<BookList> extractBooks(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        ArrayList<BookList> books = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(jsonResponse);
            JSONArray items = jsonObj.getJSONArray("items");

            for (int j = 0; j < items.length(); ++j) {
                JSONObject item = items.getJSONObject(j);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");

                String title = volumeInfo.getString("title");
                JSONArray authors = volumeInfo.getJSONArray("authors");
                String author = "";
                for (int i = 0; i < authors.length(); ++i) {
                    String allAuthor = authors.getString(i);
                    if (author != "") {
                        author += "," + allAuthor;
                    } else {
                        author += allAuthor;
                    }

                }


                String pubDate = volumeInfo.getString("publishedDate");

                JSONObject accessInfo = item.getJSONObject("accessInfo");
                String country = accessInfo.getString("country");

                JSONObject saleInfo = item.getJSONObject("saleInfo");
                String buyLink = saleInfo.getString("buyLink");
//                JSONObject listPrice = saleInfo.getJSONObject("retailPrice");
//                Double amount = listPrice.getDouble("amount");
                Double amount = 45.68;
                String currencyCode = "INR";
//                String currencyCode = listPrice.getString("currencyCode");
                String image = imageLinks.getString("smallThumbnail");

                BookList bookInfo = new BookList(author, title, pubDate, country, amount, currencyCode, image , buyLink);

                books.add(bookInfo);
            }
        }
         catch (JSONException e)
         {
             Log.v("QueryUtils" , "something went wrong" , e);
         }

        return books;
    }


    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        if(focusedView!=null)
        {
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken() , 0);
        }

    }
}

