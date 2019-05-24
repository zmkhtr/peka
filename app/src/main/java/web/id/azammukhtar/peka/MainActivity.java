package web.id.azammukhtar.peka;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button mLockApp, mPass;
    private PasswordDatabase db = new PasswordDatabase(this);
    private List<String> pass = new ArrayList<>();
    private String mText="";
    private Context mContext;
    private Intent mServiceIntent;
    private BackgroundService backgroundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLockApp = findViewById(R.id.btnMainLock);
        mPass = findViewById(R.id.btnMainPassword);

        mContext = this;

        Cursor res = db.getAllData();
        while (res.moveToNext()){
            pass.add(res.getString(0));
        }

        mLockApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.isEmpty()){
                    Toast.makeText(mContext, "First set password", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, LockAppActivity.class));
                    finish();
                }
            }
        });

        mPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Enter Password");

                final EditText input = new EditText(mContext);

                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mText = input.getText().toString();
                        if(mText.isEmpty()){
                            Toast.makeText(mContext, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                        } else {
                            int zz = db.deleteData(mText);
                            db.insertData(mText);
                            Toast.makeText(mContext, "Password berhasil di update", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,MainActivity.class));
                            finish();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)){
                if (serviceClass.getName().equals(serviceInfo.service.getClassName())) {
                    Log.i(TAG, "Running");
                    return true;
                }
        }
        Log.d(TAG, "isMyServiceRunning: not running");
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onStart() {
        super.onStart();
        if(!isAccessGranted()){
            new AlertDialog.Builder(this)
                    .setTitle("USAGE_STATS Permission")
                    .setMessage("Allow USAGE_STATS permission in Setting")
                    .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                        }
                    })
                    .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
        else if(pass.isEmpty()){
            mPass.setText("Set Password");

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Enter Password");

            final EditText input = new EditText(mContext);

            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mText = input.getText().toString();
                    if (mText.isEmpty()){
                        Toast.makeText(mContext, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean tt = db.insertData(mText);
                        pass.add(mText);
                        Toast.makeText(mContext, "password added succesfully", Toast.LENGTH_SHORT).show();
                        mPass.setText("UPDATE PASSWORD");
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
        backgroundService = new BackgroundService();
        mServiceIntent = new Intent(this, backgroundService.getClass());
        if(!isMyServiceRunning(backgroundService.getClass())){
            startService(mServiceIntent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean isAccessGranted(){
        try{
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0 );
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            if (Build.VERSION.SDK_INT> Build.VERSION_CODES.KITKAT){
                mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        applicationInfo.uid, applicationInfo.packageName);
            }
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
