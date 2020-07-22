package in.ideal.raviraj.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

public class Constants {

    public final static String BASE_URL = "https://api.covid19api.com/";
    public final static String APP_PREFS = "APP_PREFS";
    public static String USERS_COUNTRY = "Users_Country";
    public static String USERS_COUNTRY_CODE = "Users_Country_Code";
    public static int GPS_REQUEST = 9999;


    public static boolean permissionNeeded(Context ctx) {

        return ContextCompat.checkSelfPermission(ctx, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }


}
