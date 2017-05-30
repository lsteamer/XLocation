package com.elmexicano.lsteamer.xlocation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //Small test
    private static String CLIENT_ID = "150J3LTZ3W4AXHVDOTXVY0E1IRSQ4KJWSQLEAB0ON10JDDFH";
    private static String CLIENT_SECRET = "D1STHG5U5OJTMICQXAAO4RGAQCXGZ0S105LGWO0XG5Z4LKQ2";
    //
    private static final int REQUEST_CODE_FSQ_CONNECT = 200;
    private static final int REQUEST_CODE_FSQ_TOKEN_EXCHANGE = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }

    protected void onClickNoUser(View view){
        Intent intent = new Intent(getApplicationContext(), LocationListActivity.class);
        finish();
        startActivity(intent);
    }






    /**
    * The Following are the Toolbar Menu methods
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * End of the Toolbar Menu Methods
     */
}
