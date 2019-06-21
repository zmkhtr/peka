package web.id.azammukhtar.peka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    private Button mButton;
    Button mAnswer1, mAnswer2, mAnswer3, mAnswer4;
    TextView mQuestion;
    private EditText mPass;
    private TextView mStatus;
    String mString1, mString2, jawaban;
    int random;

    public LockScreenActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

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

        jawaban = getData();

        mAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jawaban = getData();
                if (mAnswer1.getText().toString().equals(jawaban)) {
                    Toasty.success(LockScreenActivity.this, "Jawaban Kamu Benar !", Toast.LENGTH_SHORT).show();
//                Intent intent = getPackageManager().getLaunchIntentForPackage(mString2);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
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
                jawaban = getData();
                if (mAnswer2.getText().toString().equals(jawaban)) {
                    Toasty.success(LockScreenActivity.this, "Jawaban Kamu Benar !", Toast.LENGTH_SHORT).show();
//                    Intent intent = getPackageManager().getLaunchIntentForPackage(mString2);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
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
                jawaban = getData();
                if (mAnswer3.getText().toString().equals(jawaban)) {
                    Toasty.success(LockScreenActivity.this, "Jawaban Kamu Benar !", Toast.LENGTH_SHORT).show();
//                    Intent intent = getPackageManager().getLaunchIntentForPackage(mString2);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
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
                jawaban = getData();
                if (mAnswer4.getText().toString().equals(jawaban)) {
                    Toasty.success(LockScreenActivity.this, "Jawaban Kamu Benar !", Toast.LENGTH_SHORT).show();
//                    Intent intent = getPackageManager().getLaunchIntentForPackage(mString2);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
                    finish();
                } else {
                    getData();
                    Toasty.error(LockScreenActivity.this, "Jawaban Kamu Salah, coba lagi !", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mString1.equals(mPass.getText().toString())){
////                    onBackPressed();
////                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(mString2);
////                    launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                    launchIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
////                    startActivity(launchIntent);
//                    finish();
//                    Toast.makeText(LockScreenActivity.this, "Password Benar", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(LockScreenActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
//                    mPass.setText("");
//                }
//            }
//        });
    }

    private String getData() {
        List<Pertanyaan> pertanyaan = new ArrayList<>();
        pertanyaan.add(new Pertanyaan(1, "Siapakah penemu bola lampu?",
                "Thomas Alfa Edison",
                "Thomas Alfamart",
                "Renayldo",
                "Renayldo Kakap",
                "Thomaes Alfa Edison",
                "Pengetahuan Umum",
                "SD Kelas 1"
        ));
        pertanyaan.add(new Pertanyaan(
                1,
                "Dengan apa ikan hiu bernapas?",
                "Insang",
                "Paru-paru",
                "Perut",
                "Sendirinya",
                "Insang",
                "Pengetahuan Alam",
                "SD Kelas 1"
        ));
        pertanyaan.add(new Pertanyaan(
                1,
                "5 + 5 = ?",
                "10",
                "17",
                "29",
                "12",
                "10",
                "Matematika",
                "SD Kelas 2"
        ));
        pertanyaan.add(new Pertanyaan(
                1,
                "Lambang bilangan dari tujuh belas adalah",
                "7",
                "17",
                "27",
                "71",
                "17",
                "Matematika",
                "SD Kelas 1"
        ));
        pertanyaan.add(new Pertanyaan(
                2,
                "Delapan ditambah enam sama dengan …",
                "empat belas",
                "dua belas",
                "tiga belas",
                "delapan puluh enam",
                "empat belas",
                "Matematika",
                "SD Kelas 1"
        ));
        pertanyaan.add(new Pertanyaan(
                3,
                "8 + 7 – 5 = …",
                "8",
                "10",
                "11",
                "9",
                "10",
                "Matematika",
                "SD Kelas 1"
        ));
        pertanyaan.add(new Pertanyaan(
                4,
                "Di dalam kandang ada 17 ayam. Dijual paman 8. Paman membeli lagi 4. Jumlah ayam paman sekarang ada … ekor",
                "9",
                "12",
                "13",
                "11",
                "13",
                "Matematika",
                "SD Kelas 1"
        ));
        pertanyaan.add(new Pertanyaan(
                5,
                "Di sebuah lapangan ada 12 ekor kambing, 9 ekor sapi, dan 11 ekor kerbau. Urutan jumlah hewan dari yang paling sedikit ialah …",
                "kambing, sapi, kerbau",
                "kerbau, kambing, sapi",
                "sapi, kerbau, kambing",
                "sapi, kerbau, domba",
                "kambing, sapi, kerbau",
                "Matematika",
                "SD Kelas 1"
        ));
// kelas 2
        pertanyaan.add(new Pertanyaan(
                6,
                "Yang tidak termasuk bangun datar adalah …",
                "Segitiga",
                "Lingkaran",
                "Kubus",
                "Jajar genjang",
                "Kubus",
                "Matematika",
                "SD Kelas 2"
        ));
        pertanyaan.add(new Pertanyaan(
                7,
                "4 x 9 = 36 Jika ditulis dalam operasi penjumlahan adalah …",
                "4 + 9",
                "4 + 4 + 4 + 4",
                "9 + 9 + 9 + 9",
                "9 + 9 + 4 + 4",
                "9 + 9 + 9 + 9",
                "Matematika",
                "SD Kelas 2"
        ));
        pertanyaan.add(new Pertanyaan(
                8,
                "Perkalian di bawah ini yang hasilnya tepat adalah",
                "4 x 9 = 38",
                "4 x 7 = 27",
                "8 x 4 = 32",
                "8 x 8 = 63",
                "8 x 4 = 32",
                "Matematika",
                "SD Kelas 2"
        ));
        pertanyaan.add(new Pertanyaan(
                9,
                "7 x … = 77",
                "11",
                "12",
                "13",
                "14",
                "11",
                "Matematika",
                "SD Kelas 2"
        ));
        pertanyaan.add(new Pertanyaan(
                10,
                "Operasi perkalian berikut ini yang hasilnya tidak 60 ialah",
                "12 x 5",
                "6 x 10",
                "15 x 4",
                "6 x 6",
                "6 x 6",
                "Matematika",
                "SD Kelas 2"
        ));
//kelas 3
        pertanyaan.add(new Pertanyaan(
                11,
                "Sudut yang besarnya 90° disebut sudut",
                "Siku-siku",
                "Lancip",
                "Garis diagonal",
                "Tumpul",
                "Siku-siku",
                "Matematika",
                "SD Kelas 3"
        ));
        pertanyaan.add(new Pertanyaan(
                12,
                "Bangun yang memiliki 4 sudut siku-siku dan 2 pasang sisi yang sama adalah",
                "Trapesium",
                "Layang-layang",
                "Jajar genjang",
                "Persegi panjang",
                "Persegi panjang",
                "Matematika",
                "SD Kelas 3"
        ));
        pertanyaan.add(new Pertanyaan(
                13,
                "Ibu membeli minyak goreng 1 liter, kemudian dipakai untuk menggoreng 3/5 liter. Sisa minyak goreng ibu adalah … liter",
                "1/5",
                "2/5",
                "3/5",
                "4/5",
                "2/5",
                "Matematika",
                "SD Kelas 3"
        ));
        pertanyaan.add(new Pertanyaan(
                14,
                "Ibu membeli pita sepanjang 5 meter. Pita itu digunakan untuk menghias baju sepanjang 2 meter. Sisa pita ibu adalah … bagian.",
                "1/5",
                "2/5",
                "3/5",
                "4/5",
                "3/5",
                "Matematika",
                "SD Kelas 3"
        ));
        pertanyaan.add(new Pertanyaan(
                15,
                "Ibu membeli sebuah melon. Ibu membaginya menjadi beberapa bagian sama besar untuk ayah, kakak, aku, adik, dan ibu sendiri. Bagian yang aku terima adalah …",
                "1/5",
                "2/5",
                "3/5",
                "4/5",
                "1/5",
                "Matematika",
                "SD Kelas 3"
        ));
        random = randInt(0, pertanyaan.size() - 1);
        mQuestion.setText(pertanyaan.get(random).getQuestion());
        mAnswer1.setText(pertanyaan.get(random).getOption1());
        mAnswer2.setText(pertanyaan.get(random).getOption2());
        mAnswer3.setText(pertanyaan.get(random).getOption3());
        mAnswer4.setText(pertanyaan.get(random).getOption4());
        final String answer = pertanyaan.get(random).getAnswerNr();
        return answer;
    }


    public static int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        //
        // In particular, do NOT do 'Random rand = new Random()' here or you
        // will get not very good / not very random results.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Toast.makeText(this, "Please Answer The Question", Toast.LENGTH_SHORT).show();
    }
}
