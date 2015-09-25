package net.calit2.mooc.iot_db410c.webserver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Name:        WebServerActivity
 * Description: Activity class to allow the application to be run. This class
 *              immediately closes after running the service in the background
 */
public class WebServerActivity extends Activity {

    /**
     * Name:        onCreate
     * Description: Instantiates WebServerService to run in the background
     *
     * @param savedInstanceBundle Reference to bundle object. Activities can be
     *                            restored to a former state using data saved
     *                            in this bundle. This parameter is unused.
     */
    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        Intent intent = new Intent(this, WebServerService.class);
        startService(intent);
        finish();
    }
}