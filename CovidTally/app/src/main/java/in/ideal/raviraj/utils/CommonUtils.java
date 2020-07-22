package in.ideal.raviraj.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class CommonUtils {

    public static void saveString(final Context context, String key, String value){
        try {
            SharedPreferences sf = context.getSharedPreferences(Constants.APP_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sf.edit();
            editor.putString(key, value);
            editor.apply();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static String getString(final Context context, String key){
        SharedPreferences sf = context.getSharedPreferences(Constants.APP_PREFS, Context.MODE_PRIVATE);
        return sf.getString(key, "");
    }



}
