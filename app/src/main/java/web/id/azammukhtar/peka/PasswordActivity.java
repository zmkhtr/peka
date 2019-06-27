package web.id.azammukhtar.peka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import es.dmoral.toasty.Toasty;

public class PasswordActivity extends AppCompatActivity {
    private static final String TAG = "PasswordActivity";
    private SessionManager sessionManager;
    private EditText mPassword;
    private TextView mText;
    private Button mMasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        sessionManager = new SessionManager(this);
        mPassword = findViewById(R.id.edtPasswordPassword);
        mText = findViewById(R.id.textPasswordInfo);
        mMasuk = findViewById(R.id.btnPasswordProceed);
        Log.d(TAG, "onCreate: " + sessionManager.getPassword());

        getSupportActionBar().hide();
        validateOnClick();
        validatePassword();
        getDeviceInfo();
        String deviceToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "onCreate: " + deviceToken);
    }

    private void validatePassword(){
        String password = sessionManager.getPassword();
        if (password == null){
            mText.setText("Enter NEW password");
        } else if (password != null){
            mText.setText("Enter Your Password");
        }
    }

    private void getDeviceInfo(){
        Log.i("TAG", "SERIAL: " + Build.SERIAL);
        Log.i("TAG","MODEL: " + Build.MODEL);
        Log.i("TAG","ID: " + Build.ID);
        Log.i("TAG","Manufacture: " + Build.MANUFACTURER);
        Log.i("TAG","brand: " + Build.BRAND);
        Log.i("TAG","type: " + Build.TYPE);
        Log.i("TAG","user: " + Build.USER);
        Log.i("TAG","BASE: " + Build.VERSION_CODES.BASE);
        Log.i("TAG","INCREMENTAL " + Build.VERSION.INCREMENTAL);
        Log.i("TAG","SDK  " + Build.VERSION.SDK);
        Log.i("TAG","BOARD: " + Build.BOARD);
        Log.i("TAG","BRAND " + Build.BRAND);
        Log.i("TAG","HOST " + Build.HOST);
        Log.i("TAG","FINGERPRINT: "+Build.FINGERPRINT);
        Log.i("TAG","Version Code: " + Build.VERSION.RELEASE);
    }

    private void validateOnClick () {
        mMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = sessionManager.getPassword();
                String inputPassword = mPassword.getText().toString().trim();
                if (mPassword.getText().toString().isEmpty()){
                    Toasty.warning(PasswordActivity.this,"Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (password == null ){
                    sessionManager.setPassword(inputPassword);
                    Log.d(TAG, "onClick: " + inputPassword + " " + sessionManager.getPassword());
                    Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toasty.info(PasswordActivity.this,"Password Berhasil Dipasang", Toast.LENGTH_SHORT).show();
                } else if (password != null){
                    if (inputPassword.equals(password)){
                        Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toasty.success(PasswordActivity.this,"Password Benar", Toast.LENGTH_SHORT).show();
                    } else {
                        Toasty.error(PasswordActivity.this,"Password Salah", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: " + sessionManager.getPassword());
        validatePassword();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
