package com.elmexicano.lsteamer.xlocation;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

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

/**
 * Created by lsteamer on 30/05/2017.
 */

public class DownloadImagesJSON extends AsyncTask<String, Void, String[]> {


    @Override
    protected String[] doInBackground(String... strings) {

        // Declaring outside the try/catch to close them later
        HttpURLConnection urlCon = null;
        BufferedReader reader = null;

        String [] suffix;
        // Will contain the raw JSON response as a string.
        String searchJSONstr;


        try {

            // Construct the URL for the Foursquare query

            final String BASE_URL = "https://api.foursquare.com/v2/venues/";
            final String OAUTH_PARAM = "oauth_token";
            final String CLIENT_ID_PARAM = "client_id";
            final String CLIENT_SECRET_PARAM = "client_secret";
            final String VERSION = "v";

            Uri UriQ;


            if(strings.length==3){

                UriQ = Uri.parse(BASE_URL+strings[0]+"/photos").buildUpon()
                        .appendQueryParameter(OAUTH_PARAM, strings[2])
                        .appendQueryParameter(VERSION, strings[1])
                        .build();

            }
            else{

                UriQ = Uri.parse(BASE_URL+strings[0]+"/photos").buildUpon()
                        .appendQueryParameter(CLIENT_ID_PARAM, strings[2])
                        .appendQueryParameter(CLIENT_SECRET_PARAM, strings[3])
                        .appendQueryParameter(VERSION, strings[1])
                        .build();
            }

            //Read the URL
            URL url = new URL(UriQ.toString());

            //Accessing the URL
            urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setRequestMethod("GET");
            urlCon.connect();
            // Read the input stream into a String
            InputStream inputStream = urlCon.getInputStream();
            StringBuffer buffer = new StringBuffer();


            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            else{
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Adding a newline as buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                searchJSONstr = buffer.toString();


                suffix = cleanImageJSON(searchJSONstr);
                return suffix;

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlCon != null) {
                urlCon.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("ForecastFragment", "Error closing stream", e);
                }
            }
        }


    }


    private String[] cleanImageJSON(String htmlJSONstr){
        String[] suffix=null;
        try{



            JSONObject searchJSONObj = new JSONObject(htmlJSONstr);

            //And the following process will get the info for all of the Following days.
            JSONObject responseJSONArray = searchJSONObj.getJSONObject("response");
            JSONObject photosJSONArray = responseJSONArray.getJSONObject("photos");
            JSONArray itemsJSONArray = photosJSONArray.getJSONArray("items");


            suffix = new String[itemsJSONArray.length()];
            for (int i = 0; i < itemsJSONArray.length(); i++) {
                // Get the JSON object holding the venue
                JSONObject pictureJSONObj = itemsJSONArray.getJSONObject(i);
                suffix[i] = pictureJSONObj.getString("suffix");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return suffix;
    }

}
