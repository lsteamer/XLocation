package com.elmexicano.lsteamer.xlocation;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.foursquare.android.nativeoauth.FoursquareCancelException;
import com.foursquare.android.nativeoauth.FoursquareDenyException;
import com.foursquare.android.nativeoauth.FoursquareInvalidRequestException;
import com.foursquare.android.nativeoauth.FoursquareOAuth;
import com.foursquare.android.nativeoauth.FoursquareOAuthException;
import com.foursquare.android.nativeoauth.FoursquareUnsupportedVersionException;
import com.foursquare.android.nativeoauth.model.AccessTokenResponse;
import com.foursquare.android.nativeoauth.model.AuthCodeResponse;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements Serializable {

    //Client ID & Client Secret
    protected static final String CLIENT_ID = "150J3LTZ3W4AXHVDOTXVY0E1IRSQ4KJWSQLEAB0ON10JDDFH";
    protected static final String CLIENT_SECRET = "D1STHG5U5OJTMICQXAAO4RGAQCXGZ0S105LGWO0XG5Z4LKQ2";

    //Error Message
    protected static final String NOT_ONLINE = "No connection detected.";

    //Connection Tokens
    private static final int REQUEST_CODE_FSQ_CONNECT = 200;
    private static final int REQUEST_CODE_FSQ_TOKEN_EXCHANGE = 201;

    //Today's date for Foursquare query
    protected static String DATE;

    protected static Button user, no_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        user = (Button) findViewById(R.id.button_user_login);
        no_user = (Button) findViewById(R.id.button_no_user_login);

        user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onClickUser(v);
            }
        });

        no_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onClickNoUser(v);
            }
        });




        //Query requires today's date
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dayDateStack = new SimpleDateFormat("yyyyMMdd");
        DATE = dayDateStack.format(cal.getTime());

    }


    //Logging in without logging in
    public void onClickNoUser(View view){

        //Array to store the received values
        ArrayList<LocationData> locations = new ArrayList<>();
        //Class that downloads the HTML
        DownloadJSON seachAsyncTask = new DownloadJSON();

        try {
            //Location,Today's date, ClientID,ClientSecret
            locations = seachAsyncTask.execute("52.500342,13.425170",DATE,CLIENT_ID,CLIENT_SECRET).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //If there's an issue Toast error message
        if(locations==null){
            toastMessage(this,NOT_ONLINE);

        }
        else{
            //Calling the Intent
            Intent intent = new Intent(getApplicationContext(), LocationListActivity.class);

            //Sending the Array
            Bundle bundle = new Bundle();
            bundle.putSerializable("list",locations);
            intent.putExtras(bundle);

            //Ending this Activity
            finish();
            //Calling the next one
            startActivity(intent);
        }


    }

    public void onClickUser(View view){
        // Start the native auth flow.
        Intent intent = FoursquareOAuth.getConnectIntent(MainActivity.this, CLIENT_ID);

        // If the device does not have the Foursquare app installed, we'd
        // get an intent back that would open the Play Store for download.
        // Otherwise we start the auth flow.
        if (FoursquareOAuth.isPlayStoreIntent(intent)) {
            toastMessage(MainActivity.this, getString(R.string.app_not_installed_message));
            startActivity(intent);
        } else {
            startActivityForResult(intent, REQUEST_CODE_FSQ_CONNECT);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_FSQ_CONNECT:
                onCompleteConnect(resultCode, data);
                break;

            case REQUEST_CODE_FSQ_TOKEN_EXCHANGE:
                onCompleteTokenExchange(resultCode, data);
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void onCompleteConnect(int resultCode, Intent data) {
        AuthCodeResponse codeResponse = FoursquareOAuth.getAuthCodeFromResult(resultCode, data);
        Exception exception = codeResponse.getException();

        if (exception == null) {
            // Success.
            String code = codeResponse.getCode();
            performTokenExchange(code);

        } else {
            if (exception instanceof FoursquareCancelException) {
                // Cancel.
                toastMessage(this, "Canceled");

            } else if (exception instanceof FoursquareDenyException) {
                // Deny.
                toastMessage(this, "Denied");

            } else if (exception instanceof FoursquareOAuthException) {
                // OAuth error.
                String errorMessage = exception.getMessage();
                String errorCode = ((FoursquareOAuthException) exception).getErrorCode();
                toastMessage(this, errorMessage + " [" + errorCode + "]");

            } else if (exception instanceof FoursquareUnsupportedVersionException) {
                // Unsupported Fourquare app version on the device.
                toastError(this, exception);

            } else if (exception instanceof FoursquareInvalidRequestException) {
                // Invalid request.
                toastError(this, exception);

            } else {
                // Error.
                toastError(this, exception);
            }
        }
    }

    private void onCompleteTokenExchange(int resultCode, Intent data) {
        AccessTokenResponse tokenResponse = FoursquareOAuth.getTokenFromResult(resultCode, data);
        Exception exception = tokenResponse.getException();

        if (exception == null) {
            String accessToken = tokenResponse.getAccessToken();

            DownloadJSON seachAsyncTask = new DownloadJSON();
            ArrayList<LocationData> locations = new ArrayList<>();

            //Query requires today's date
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dayDateStack = new SimpleDateFormat("yyyyMMdd");

            try {
                //Location,Today's date, token
                locations = seachAsyncTask.execute("52.500342,13.425170",DATE,accessToken).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            //If there's an issue Toast error message
            if(locations==null){
                toastMessage(this,NOT_ONLINE);

            }
            else{

                Intent intent = new Intent(getApplicationContext(), LocationListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list",locations);
                intent.putExtras(bundle);
                finish();
                startActivity(intent);

            }

        } else {
            if (exception instanceof FoursquareOAuthException) {
                // OAuth error.
                String errorMessage = ((FoursquareOAuthException) exception).getMessage();
                String errorCode = ((FoursquareOAuthException) exception).getErrorCode();
                toastMessage(this, errorMessage + " [" + errorCode + "]");

            } else {
                // Other exception type.
                toastError(this, exception);
            }
        }
    }


    private void performTokenExchange(String code) {
        Intent intent = FoursquareOAuth.getTokenExchangeIntent(this, CLIENT_ID, CLIENT_SECRET, code);
        startActivityForResult(intent, REQUEST_CODE_FSQ_TOKEN_EXCHANGE);
    }

    public static void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toastError(Context context, Throwable t) {
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
    }






}
