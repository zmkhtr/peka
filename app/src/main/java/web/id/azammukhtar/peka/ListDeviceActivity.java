package web.id.azammukhtar.peka;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;
import web.id.azammukhtar.peka.Adapter.ListAplikasiAdapter;
import web.id.azammukhtar.peka.Adapter.ListDeviceAdapter;
import web.id.azammukhtar.peka.Model.UserDevice;

public class ListDeviceActivity extends AppCompatActivity {
    private static final String TAG = "ListDeviceActivity";
    private RecyclerView recyclerView;
    private ListDeviceAdapter adapter;
    private List<UserDevice> deviceList = new ArrayList<>();
    private Context mContext;
    private String mText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_device);
        AndroidNetworking.initialize(getApplicationContext());
        mContext = this;
        recyclerView = findViewById(R.id.recyclerListDevice);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);
        adapter = new ListDeviceAdapter();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        setOnClick();
        fetchDeviceList();
    }

    private void setOnClick(){
        adapter.setOnItemClickListener(new ListDeviceAdapter.OnViewClick() {
            @Override
            public void onViewClick(int position) {
                UserDevice userDevice = deviceList.get(position);
                Log.d(TAG, "onViewClick: " + userDevice.getHandphoneToken());
                sendLock(userDevice.getHandphoneToken(), "Orang tua-mu mengunci devicemu !");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void fetchDeviceList(){
        final ProgressDialog dialog = ProgressDialog.show(mContext, "Memuat data",
                "Loading. Mohon tunggu...", true);
        dialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference(user.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (!dataSnapshot.exists()) {
                    Log.d(TAG, "onDataChange: no data");
                    dialog.dismiss();
                } else if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot infoSnapshot : dataSnapshot.getChildren()) {
                        deviceList.add(infoSnapshot.getValue(UserDevice.class));
                        Log.d(TAG, "onDataChange: getdata");
                        dialog.dismiss();
                    }
                    adapter.setDeviceList(deviceList);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                dialog.dismiss();
            }
        });
    }

    private void sendLock(String token, String message){
        final ProgressDialog dialog = ProgressDialog.show(mContext, "Mengunci target device",
                "Loading. Mohon tunggu...", true);
        dialog.show();
        AndroidNetworking.get("http://jawabdulu.azammukhtar.web.id/api/notifsend/"+token+"/"+message)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d(TAG, "onResponse: " + response);
                        Toasty.success(mContext, "Succes mengunci perangkat", Toasty.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, "onError: ", error);
                        Toasty.error(mContext, "Gagal mengunci perangkat", Toasty.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
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

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(ListDeviceActivity.this, MainActivity.class));
        finish();
    }
}
