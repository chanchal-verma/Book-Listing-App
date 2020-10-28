package com.chanchal.sindhubhawanshaadi.layouts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<BookList>{

    public BookAdapter(@NonNull Context context, ArrayList<BookList> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;
        if(listViewItem == null)
        {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item , parent ,false);
        }
        BookList currentBook = getItem(position);

        TextView author = (TextView) listViewItem.findViewById(R.id.author);
        author.setText("Authors :"+currentBook.getBookAuthor());

        TextView title = (TextView) listViewItem.findViewById(R.id.Title);
        title.setText("Title :"+currentBook.getBookTitle());

        TextView country = (TextView) listViewItem.findViewById(R.id.country);
        country.setText(currentBook.getCountry());

        TextView currencyCode = (TextView) listViewItem.findViewById(R.id.currencyCode);
        currencyCode.setText(currentBook.getCurrencyCode());

        TextView pubDate = (TextView) listViewItem.findViewById(R.id.PubDate);
        pubDate.setText("Year :"+currentBook.getPubDate());

        TextView amount = (TextView) listViewItem.findViewById(R.id.amount);
        String price = doubleFormatter(currentBook.getPrice());
        amount.setText(price+" ");

        ImageView image = (ImageView) listViewItem.findViewById(R.id.logo);


        if (currentBook != null) {
            new DownloadImageTask(image).execute(currentBook.getImage());
        }

        return listViewItem;
    }

    public static String doubleFormatter(Double Price)
    {
        DecimalFormat formatter = new DecimalFormat("0.00");
        return formatter.format(Price);
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
