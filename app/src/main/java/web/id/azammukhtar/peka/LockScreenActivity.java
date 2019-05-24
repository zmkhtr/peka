package web.id.azammukhtar.peka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LockScreenActivity extends AppCompatActivity {
    private Button mButton;
    private EditText mPass;
    private TextView mStatus;
    String mString1, mString2;
    public LockScreenActivity(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        mButton = findViewById(R.id.btnLockscreenUnlock);
        mPass = findViewById(R.id.edtLockscreenPassword);
        mStatus = findViewById(R.id.textLockscreenStatus);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            mString1 = extras.getString("name");
            mString2 = extras.getString("pack");
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mString1.equals(mPass.getText().toString())){
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(mString2);
                    launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(launchIntent);
                    finish();
                    Toast.makeText(LockScreenActivity.this, "Password Benar", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LockScreenActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                    mPass.setText("");
                }
            }
        });
    }
}
