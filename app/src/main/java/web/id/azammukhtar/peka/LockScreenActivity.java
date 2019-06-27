package web.id.azammukhtar.peka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import es.dmoral.toasty.Toasty;
import web.id.azammukhtar.peka.Model.Pertanyaan;

public class LockScreenActivity extends AppCompatActivity {
    private static final String TAG = "LockScreenActivity";

    private Button mButton;
    Button mAnswer1, mAnswer2, mAnswer3, mAnswer4;
    TextView mQuestion;
    private EditText mPass;
    private TextView mStatus;
    String mString1, mString2, jawaban;
    int random;
    private SessionManager sessionManager;

    public LockScreenActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        sessionManager = new SessionManager(this);
//        mButton = findViewById(R.id.btnLockscreenUnlock);
//        mPass = findViewById(R.id.edtLockscreenPassword);
//        mStatus = findViewById(R.id.textLockscreenStatus);

        getSupportActionBar().hide();
        mQuestion = findViewById(R.id.textLockQuestion);
        mAnswer1 = findViewById(R.id.btnLockAnswer1);
        mAnswer2 = findViewById(R.id.btnLockAnswer2);
        mAnswer3 = findViewById(R.id.btnLockAnswer3);
        mAnswer4 = findViewById(R.id.btnLockAnswer4);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mString1 = extras.getString("name");
            mString2 = extras.getString("pack");
        }


        getData();
        setOnClick();
    }

    private void setOnClick(){
        mAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswer1.getText().toString().equals(sessionManager.getAnswer())) {
                    Toasty.success(LockScreenActivity.this, "Jawaban Kamu Benar !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    getData();
                    Toasty.error(LockScreenActivity.this, "Jawaban Kamu Salah, coba lagi !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswer2.getText().toString().equals(sessionManager.getAnswer())) {
                    Toasty.success(LockScreenActivity.this, "Jawaban Kamu Benar !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    getData();
                    Toasty.error(LockScreenActivity.this, "Jawaban Kamu Salah, coba lagi !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswer3.getText().toString().equals(sessionManager.getAnswer())) {
                    Toasty.success(LockScreenActivity.this, "Jawaban Kamu Benar !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    getData();
                    Toasty.error(LockScreenActivity.this, "Jawaban Kamu Salah, coba lagi !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswer4.getText().toString().equals(sessionManager.getAnswer())) {
                    Toasty.success(LockScreenActivity.this, "Jawaban Kamu Benar !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    getData();
                    Toasty.error(LockScreenActivity.this, "Jawaban Kamu Salah, coba lagi !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getData() {
        List<Pertanyaan> pertanyaan = DummyData.createKategoriList();

        random = randInt(0, pertanyaan.size() - 1);
        mQuestion.setText(pertanyaan.get(random).getQuestion());
        mAnswer1.setText(pertanyaan.get(random).getOption1());
        mAnswer2.setText(pertanyaan.get(random).getOption2());
        mAnswer3.setText(pertanyaan.get(random).getOption3());
        mAnswer4.setText(pertanyaan.get(random).getOption4());
        sessionManager.setAnswer(pertanyaan.get(random).getAnswerNr());
    }


    public static int randInt(int min, int max) {


        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Toasty.info(this, "Mohon jawab pertanyaannya !", Toast.LENGTH_SHORT).show();

    }

}
