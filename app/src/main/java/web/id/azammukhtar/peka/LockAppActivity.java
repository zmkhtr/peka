package web.id.azammukhtar.peka;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import web.id.azammukhtar.peka.Adapter.ListAplikasiAdapter;
import web.id.azammukhtar.peka.Model.Aplikasi;
import web.id.azammukhtar.peka.Model.Pertanyaan;

public class LockAppActivity extends AppCompatActivity {
    private static final String TAG = "LockAppActivity";

    private List<Aplikasi> appList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListAplikasiAdapter adapter;
    private CompositeDisposable disposable = new CompositeDisposable();

    Apply_password_on_appDatabase db = new Apply_password_on_appDatabase(this);
    PasswordDatabase passwordDatabase = new PasswordDatabase(this);
    List<String> lock = new ArrayList<>();

    String pass="";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_app);

        Cursor res = db.getAllData();
        Cursor res2 = passwordDatabase.getAllData();

        while (res.moveToNext()) {
            lock.add(res.getString(0));
        }
        while (res2.moveToNext()) {
            pass = res2.getString(0);
        }

        recyclerView = findViewById(R.id.recyclerLockAppAplikasi);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);
        adapter = new ListAplikasiAdapter();
        adapter.setAplikasiList(appList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        getInstalledApps();
        adapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListner() {
            @Override
            public void onClick(View view, int position) {
                Aplikasi aplikasi = appList.get(position);
                if (aplikasi.getLocked() == R.drawable.ic_lock){
                    db.deleteData(aplikasi.getPackageName());
                    Toast.makeText(LockAppActivity.this, "Kunci aplikasi dibuka", Toast.LENGTH_SHORT).show();
                    aplikasi.setLocked(R.drawable.ic_lock_open);
                } else {
                    db.insertData(aplikasi.getPackageName(), pass);
                    Toast.makeText(LockAppActivity.this, "Berhasil mengunci aplikasi", Toast.LENGTH_SHORT).show();
                    aplikasi.setLocked(R.drawable.ic_lock);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

//        rxJava();
    }

//    private void rxJava(){
//        Observable<Aplikasi> observableList = Observable
//                .fromIterable(getInstalledApps())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//
//        observableList.subscribe(new Observer<Aplikasi>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                disposable.add(d);
//            }
//
//            @Override
//            public void onNext(Aplikasi aplikasi) {
//                appList.add(new Aplikasi(aplikasi.getNamaAplikasi(), aplikasi.getIconAplikasi(),
//                        R.drawable.ic_lock_open, aplikasi.getPackageName()));
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "onError: ", e);
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private List<Aplikasi> getInstalledApps(){

        List<Aplikasi> res = new ArrayList<>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < packs.size(); i++){
            PackageInfo p = packs.get(i);
            Log.d(TAG, "getInstalledApps: " + packs.size());
            if (lock.contains(packs.get(i).packageName)){
                if ((isSystemPackage(p) == false)){
                    String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                    Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                    appList.add(new Aplikasi(appName, icon, R.drawable.ic_lock, packs.get(i).packageName));
                }
            } else {
                if ((isSystemPackage(p) == false)){
                    String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                    Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                    appList.add(new Aplikasi(appName, icon, R.drawable.ic_lock_open, packs.get(i).packageName));
                }
            }
            }
        return res;
    }

    private boolean isSystemPackage(PackageInfo p) {
        return ((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(LockAppActivity.this, MainActivity.class));
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
