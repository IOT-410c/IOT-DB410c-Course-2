package calit2.networktests;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Name: NetworkTestsLocationListener
 * Description: Defines a concrete LocationListener class to be used with the
 *              GPS Test of the application.
 */
public class NetworkTestsLocationListener implements LocationListener {

    /**
     * Name: onLocationChanged
     */
    @Override
    public void onLocationChanged(Location location) {}

    /**
     * Name: onStatusChanged
     */
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}


    /**
     * Name: onProviderEnabled
     */
    @Override
    public void onProviderEnabled(String s) {}

    /**
     * Name: onProviderDisabled
     */
    @Override
    public void onProviderDisabled(String s) {}
}
