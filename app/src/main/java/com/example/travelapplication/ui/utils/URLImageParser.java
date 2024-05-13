package com.example.travelapplication.ui.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class URLImageParser implements Html.ImageGetter {
    private Context context;
    private View container;

    public URLImageParser(View container, Context context) {
        this.context = context;
        this.container = container;
    }

    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();
        ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask(urlDrawable);
        asyncTask.execute(source);
        return urlDrawable;
    }

    private class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        private URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable urlDrawable) {
            this.urlDrawable = urlDrawable;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            // Set the correct bounds according to the result from the HTTP call
            urlDrawable.setBounds(0, 0, result.getIntrinsicWidth(), result.getIntrinsicHeight());
            // Change the reference of the current drawable to the result from the HTTP call
            urlDrawable.drawable = result;
            // Redraw the image by invalidating the container
            URLImageParser.this.container.invalidate();
        }

        private Drawable fetchDrawable(String urlString) {
            try {
                InputStream is = fetch(urlString);
                return Drawable.createFromStream(is, "src");
            } catch (Exception e) {
                return null;
            }
        }

        private InputStream fetch(String urlString) throws MalformedURLException, IOException {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return connection.getInputStream();
            } else {
                // Handle other response codes (e.g., 404, 500, etc.) as needed
                throw new IOException("HTTP response code: " + responseCode);
            }
        }

    }
}


