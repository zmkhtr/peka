package web.id.azammukhtar.peka;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Application;
import android.app.ProgressDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button mLockApp, mPass, mLockDevice, mLogout;
    private PasswordDatabase db = new PasswordDatabase(this);
    private List<String> pass = new ArrayList<>();
    private String mText = "";
    private Context mContext;
    private Intent mServiceIntent;
    private BackgroundService backgroundService;
    private SessionManager sessionManager;
    private FirebaseAuth mAuth;

    public static final int RESULT_ENABLE = 11;
    private DevicePolicyManager devicePolicyManager;
    private ActivityManager activityManager;
    private ComponentName componentName;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        componentName = new ComponentName(this, MyAdmin.class);

        mLockApp = findViewById(R.id.btnMainLock);
        mPass = findViewById(R.id.btnMainPassword);
        mLockDevice = findViewById(R.id.btnMainLockDevice);
        mLogout = findViewById(R.id.btnMainLogout);

        mContext = this;
        sessionManager = new SessionManager(mContext);
        mAuth = FirebaseAuth.getInstance();
        validatePermission();
        getSupportActionBar().hide();

        Cursor res = db.getAllData();
        while (res.moveToNext()) {
            pass.add(res.getString(0));
        }

        if (mAuth.getUid() == null){
            mLogout.setVisibility(View.INVISIBLE);
        } else {
            mLogout.setVisibility(View.VISIBLE);
            mLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signOut();
                    Toasty.info(mContext, "Logout Berhasil", Toasty.LENGTH_SHORT).show();
                    mLogout.setVisibility(View.INVISIBLE);
                }
            });
        }
        buttonOnClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        boolean isActive = devicePolicyManager.isAdminActive(componentName);
    }

//    private void getLockPermission(){
//        boolean active = devicePolicyManager.isAdminActive(componentName);
//        if(active){
//            devicePolicyManager.lockNow();
//        } else {
//            Toast.makeText(mContext, "Mohon enable device admin features", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
//            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
//                    "Mohon enable features device admin untuk menggunakan fitur ini");
//        }
//    }

    private void buttonOnClick(){
        mLockApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "Menscan Aplikasi",
                        "Loading. Mohon tunggu...", true);
                dialog.show();

                startActivity(new Intent(MainActivity.this, LockAppActivity.class));
                finish();
            }
        });


        mLockDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user == null) {
                    Toasty.info(mContext, "Anda harus login untuk menggunakan fitur ini !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, BlankActivity.class);
                    startActivity(intent);
                } else if (user != null) {
                    Intent intent = new Intent(MainActivity.this, ListDeviceActivity.class);
                    startActivity(intent);
//                    boolean active = devicePolicyManager.isAdminActive(componentName);
//                    if(active){
////                        devicePolicyManager.lockNow();
//                        Toast.makeText(mContext, "GGWP", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(mContext, "Mohon enable device admin features", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//                        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
//                        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
//                                "Mohon enable features device admin untuk menggunakan fitur ini");
//                        startActivityForResult(intent, RESULT_ENABLE);
//                    }
                }
            }
        });
        mPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Enter Password");

                final EditText input = new EditText(mContext);

                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mText = input.getText().toString();
                        if (mText.isEmpty()) {
                            Toasty.warning(mContext, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                        } else {
                            db.insertData(mText);
                            Toasty.info(mContext, "Password berhasil di update", Toast.LENGTH_SHORT).show();
                            sessionManager.setPassword(mText);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_ENABLE :
                if (resultCode == Activity.RESULT_OK){
                    Toast.makeText(mContext, "Feature Device Admin Telah di Enable", Toast.LENGTH_SHORT).show();
                } else {
                }
        }
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(serviceInfo.service.getClassName())) {
                Log.d(TAG, "Running");
                return true;
            }
        }
        Log.d(TAG, "isMyServiceRunning: not running");
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void validatePermission(){
        sessionManager = new SessionManager(mContext);
        String password = sessionManager.getPassword();
        Log.d(TAG, "onStart: " + password);

        if (!isAccessGranted()) {
            new AlertDialog.Builder(this)
                    .setTitle("USAGE STATE Permission")
                    .setMessage("Mohon perbolehkan permission USAGE STATE untuk aplikasi ini pada Setting")
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
        backgroundService = new BackgroundService();
        mServiceIntent = new Intent(this, backgroundService.getClass());
        if (!isMyServiceRunning(backgroundService.getClass())) {
            startService(mServiceIntent);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onStart() {
        super.onStart();



    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean isAccessGranted() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT) {
                mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        applicationInfo.uid, applicationInfo.packageName);
            }
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
