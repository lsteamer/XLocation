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
import java.util.ArrayList;

/**
 * Created by lsteamer on 30/05/2017.
 */

public class DownloadJSON extends AsyncTask<String, Void, ArrayList<LocationData>> {


    @Override
    protected ArrayList<LocationData> doInBackground(String... strings) {

        // Declaring outside the try/catch to close them later
        HttpURLConnection urlCon = null;
        BufferedReader reader = null;

        ArrayList<LocationData> locations;

        // Will contain the raw JSON response as a string.
        String searchJSONstr;

        try {
            // Construct the URL for the Foursquare query
            final String BASE_URL = "https://api.foursquare.com/v2/venues";
            final String OAUTH_PARAM = "oauth_token";
            final String CLIENT_ID_PARAM = "client_id";
            final String CLIENT_SECRET_PARAM = "client_secret";
            final String VERSION = "v";
            final String LATLON_PARAM = "ll";

            Uri UriQ;
            if(strings.length==3){

                //If it's with Token
                UriQ = Uri.parse(BASE_URL+"/search").buildUpon()
                        .appendQueryParameter(LATLON_PARAM, strings[0])
                        .appendQueryParameter(OAUTH_PARAM, strings[2])
                        .appendQueryParameter(VERSION, strings[1])
                        .build();
            }
            else{
                //If it's with Client ID and Client Secret
                UriQ = Uri.parse(BASE_URL+"/search").buildUpon()
                        .appendQueryParameter(LATLON_PARAM, strings[0])
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

                //cleaning the JSON data
                locations = cleanJSONHTML(searchJSONstr);
                //Returning the array
                return locations;
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


    private ArrayList<LocationData> cleanJSONHTML (String strJSON){

        ArrayList<LocationData> locations = new ArrayList<>();
        //ordering the list through distance
        ArrayList<Integer> lista = new ArrayList<>();
        int order = 0;
        try{


            JSONObject searchJSONObj = new JSONObject(strJSON);

            //And the following process will get the info for all of the Venues.
            JSONObject responseJSONArray = searchJSONObj.getJSONObject("response");
            JSONArray venueJSONArray = responseJSONArray.getJSONArray("venues");

            //Declaring the variables
            String locationID, title,address, postalCode, category, phoneNumber, formattedPhoneNumber, twitter, instagram,  facebook, icon;
            int distance;
            float latitude, longitude;

            //For as long as there are venues in the JSON
            for (int i = 0; i < venueJSONArray.length(); i++) {

                order = 0;


                // Get the JSON object holding the venue
                JSONObject venueJSONObj = venueJSONArray.getJSONObject(i);

                locationID = venueJSONObj.getString("id");
                title = venueJSONObj.getString("name");


                /**
                 * Some Data Fields are not present in all of the Locations
                 * I don't think it's good practice to try/catch every single field
                 * Time permitting, research a solution
                 */
                //Location data
                JSONObject locationJSON = venueJSONObj.getJSONObject("location");
                longitude = Float.parseFloat(locationJSON.getString("lng"));
                latitude = Float.parseFloat(locationJSON.getString("lat"));
                distance = locationJSON.getInt("distance");

                try{
                    address = locationJSON.getString("address");
                }catch (JSONException e) {
                    address = "";
                }
                try{
                    postalCode = locationJSON.getString("postalCode");
                }catch (JSONException e) {
                    postalCode ="";
                }


                //Category data
                try{
                    JSONObject categoriesJSON = venueJSONObj.getJSONArray("categories").getJSONObject(0);
                    category = categoriesJSON.getString("name");
                    JSONObject iconJSON = categoriesJSON.getJSONObject("icon");
                    icon = iconJSON.getString("prefix");
                    icon=icon+"bg_88.png";

                }catch (JSONException e) {
                    category ="";
                    icon="https://ss0.4sqi.net/img/categories_v2/building/home_bg_88.png";

                }


                phoneNumber = "";
                formattedPhoneNumber = "";
                twitter = "";
                facebook = "";
                instagram = "";

                order = insertOrder(lista, distance);

                lista.add(order,distance);

                locations.add(order, new LocationData(locationID, title, address, distance, latitude, longitude, postalCode,
                        category, phoneNumber, formattedPhoneNumber, twitter,
                        instagram, facebook, icon));


            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return locations;

    }

    private int insertOrder(ArrayList<Integer> lista, int distance){
        int i=0;

        for (; i< lista.size(); i++){
            if(lista.get(i)>distance)
                break;
        }

        return i;
    }

}
