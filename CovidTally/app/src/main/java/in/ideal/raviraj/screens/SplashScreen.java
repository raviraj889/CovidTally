package in.ideal.raviraj.screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;

import in.ideal.raviraj.R;
import in.ideal.raviraj.utils.CommonUtils;
import in.ideal.raviraj.utils.Constants;
import in.ideal.raviraj.utils.GpsUtils;
import in.ideal.raviraj.utils.LocationFinder;
import in.ideal.raviraj.utils.ObserveActions;
import okhttp3.internal.Util;

public class SplashScreen extends UtilsActivity implements Observer, GpsUtils.onGpsListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        checkPermission();

    }

    private void init(){
        ObserveActions.getInstance().addObserver(this);
        new GpsUtils(this).turnGPSOn(this);
    }

    private void checkPermission() {
        if (Constants.permissionNeeded(SplashScreen.this)) {
            ActivityCompat.requestPermissions(SplashScreen.this,
                    new String[]{
                            Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
            // init();
        } else {
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED
                && grantResults[3] == PackageManager.PERMISSION_GRANTED && grantResults[4] == PackageManager.PERMISSION_GRANTED) {
            init();
        } else {

            showMessgae("Please grant permissions", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    checkPermission();
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constants.GPS_REQUEST && requestCode != Activity.RESULT_OK){
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();
        }

    }

    @Override
    public void update(Observable observable, Object o) {

        try {
            Address address = (Address) o;

            Log.e("Country :: " , address.getCountryCode() + " - " + address.getCountryName());

            CommonUtils.saveString(SplashScreen.this, Constants.USERS_COUNTRY, address.getCountryName());
            CommonUtils.saveString(SplashScreen.this, Constants.USERS_COUNTRY_CODE, address.getCountryCode());

            ObserveActions.getInstance().deleteObserver(this);
            startActivity(new Intent(this, MainActivity.class));
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gpsStatus(boolean isGPSEnable) {
        if (isGPSEnable) {
            new LocationFinder(SplashScreen.this).getLocation();
        }else{
            showMessgae("Please enable your location other wise we cannot show your country name on top");
        }
    }

}