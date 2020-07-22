package in.ideal.raviraj.utils;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

public class LocationFinder extends Service implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 200 * 10 * 1;
    protected LocationManager locationManager;
    Context context;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    Location location;
    double latitude;
    double longitude;
    double accurate;
    boolean hasLocationChanged;

    public LocationFinder(Context context) {
        this.context = context;
        getLocation();
    }

    @SuppressLint("MissingPermission")
    public void getLocation() {
        try {
            locationManager = (LocationManager) context
                    .getSystemService(LOCATION_SERVICE);// getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);// getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                // Log.e(“Network-GPS”, “Disable”);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.canGetLocation = true;

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        //Log.e(“GPS Enabled”, “GPS Enabled”);
        if (locationManager != null) {
            location = locationManager.getLastKnownLocation(GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                accurate = location.getAccuracy();
                longitude = location.getLongitude();

                Log.e("Longitute", "is::" + location.getLongitude());
                Log.e("Longitutur", "is::" + location.getLatitude());
                Log.e("accurate", "Accurate Location" + accurate);
                getAddress(latitude, longitude);
            }else{
                location = locationManager.getLastKnownLocation(NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    accurate = location.getAccuracy();
                    longitude = location.getLongitude();

                    Log.e("Longitute", "is::" + location.getLongitude());
                    Log.e("Longitutur", "is::" + location.getLatitude());
                    Log.e("accurate", "Accurate Location" + accurate);
                    getAddress(latitude, longitude);
                }
            }
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        float bestAccuracy = -1f;
        if (location.getAccuracy() != 0.0f
                && (location.getAccuracy() < bestAccuracy) || bestAccuracy == -1f) {
            if (location.getAccuracy() < 10) {
                locationManager.removeUpdates(this);

            }
        }
        /*   bestAccuracy = location.getAccuracy();*/

        if (bestAccuracy < 15) {

            Log.v("BEST ACCURACY", "BEST ACCURACY" + bestAccuracy);
        }
        Log.e("onLocationChanged :: ", location.toString());
        getLocation();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.e("onStatusChanged :: ", s);
//        getLocation();
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.e("onProviderEnabled :: ", s);
        hasLocationChanged = false;
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.e("onProviderDisabled :: ", s);
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        Log.e("Lt", "LAti" + latitude);
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        Log.e("Longit", "Longitute" + longitude);
        return longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
           /* add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();*/

            Log.v("IGA", "Address" + add);

            ObserveActions.getInstance().update(obj);
            hasLocationChanged = true;

            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Location ", "Error  "+e.getMessage());
        }
    }

}
