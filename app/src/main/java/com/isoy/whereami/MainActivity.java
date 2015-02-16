package com.isoy.whereami;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {
    private LocationManager locationManager;
    private int refreshCounter;
    private TextView textCounter;
    //private Button btnWhereAmI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //textCounter = (TextView) findViewById(R.id.textView2);
        //btnWhereAmI = (Button) findViewById(R.id.btnwhereami);

    }
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            refreshCounter++;
            textCounter.setText(Integer.toString(refreshCounter));
        }

        @Override
        public void onProviderDisabled(String provider) {
            refreshCounter = 0;
            textCounter.setText("0");
        }

        @Override
        public void onProviderEnabled(String provider) {
            refreshCounter = 0;
            textCounter.setText("0");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    public void updateLocation(View v) {
        refreshCounter = 0;
        //textCounter.setText("0");

        //updating GPS location


        String provider;
        String method = "gps";
        if (method == "gps")
            provider = LocationManager.GPS_PROVIDER;
        else
            provider = LocationManager.NETWORK_PROVIDER;

        locationManager.requestLocationUpdates(provider, 1000, 0,
                locationListener);


        //locationManager.removeUpdates(locationListener);

        /*
        try {
            provider = LocationManager.GPS_PROVIDER;
            locationManager.requestLocationUpdates(provider, 1000, 0, locationListener);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error Updating GPS Location",
                    Toast.LENGTH_SHORT).show();
        }
        //Updating network location
        try {
            provider = LocationManager.NETWORK_PROVIDER;
            locationManager.requestLocationUpdates(provider, 1000, 0, locationListener);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error Updating NETWORK Location",
                    Toast.LENGTH_SHORT).show();
        }*/

        //} else
        //locationManager.removeUpdates(locationListener);
    }
    public void BtnWhereAmI(View v) {
        //updateLocation();
        String provider;TextView textView;

        //updating GPS location


        textView = (TextView) findViewById(R.id.textView);
        //provider = LocationManager.NETWORK_PROVIDER;
        //locationManager.requestLocationUpdates(provider, 1000, 0, locationListener);
        Location location;

        location = locationManager.getLastKnownLocation("gps");
        //Tries to get the gps location but settles for network location if gps isnt found
        if(location == null){
            location = locationManager.getLastKnownLocation("network");
            Toast.makeText(getApplicationContext(), "NETWORK Location",
                    Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(getApplicationContext(), "GPS Location",
                    Toast.LENGTH_SHORT).show();


        /*location = null;
        if (location == null) {
            if (locationManager.isProviderEnabled("gps"))
                textView.setText("No GPS signal!");
            else
                textView.setText("Turn GPS on!");
        } else
            textView.setText("Network not enabled!");
*/
        long now = Calendar.getInstance().getTimeInMillis();
        textView.setText(String.format("Latitude = %s\nLongitude = %s\n"
                        + "Accuracy = %f\n" + "%d seconds ago",
                location.getLatitude(), location.getLongitude(),
                location.getAccuracy(), (now - location.getTime()) / 1000));
    }


    @Override
    protected void onResume() {
        super.onResume();
        //setButtonState();
    }


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
}
