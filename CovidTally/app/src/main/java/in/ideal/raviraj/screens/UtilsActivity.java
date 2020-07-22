package in.ideal.raviraj.screens;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import in.ideal.raviraj.R;

public class UtilsActivity extends AppCompatActivity {

    ProgressDialog pd;

    protected void showProgress(){
        if(pd == null) {
            pd = new ProgressDialog(UtilsActivity.this);
        }
        pd.setIndeterminate(false);
        pd.setCancelable(false);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if(!pd.isShowing() && !isFinishing()) {
            pd.show();
        }

        pd.setContentView(R.layout.progress_layout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pd!=null && pd.isShowing()){
                    pd.dismiss();
                }
            }
        },30000);

    }

    protected void dismissProgress(){
        if(pd!=null && pd.isShowing()){
            pd.dismiss();
        }
    }

    protected void showMessgae(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Alert");
        builder.setMessage(message);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

    protected void showMessgae(String message, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Alert");
        builder.setMessage(message);
        builder.setPositiveButton("Exit", listener);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }
}
