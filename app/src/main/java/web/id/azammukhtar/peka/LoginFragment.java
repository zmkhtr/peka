package web.id.azammukhtar.peka;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import es.dmoral.toasty.Toasty;
import web.id.azammukhtar.peka.Model.UserDevice;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";

    private Button btnLogin, btnRegister;
    private EditText etEmail, etPassword;
    private FirebaseAuth mAuth;
    private Context mContext;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogin = view.findViewById(R.id.btnLoginProceed);
        btnRegister = view.findViewById(R.id.btnLoginRegister);

        etEmail = view.findViewById(R.id.edtLoginEmail);
        etPassword = view.findViewById(R.id.edtLoginPassword);

        mAuth = FirebaseAuth.getInstance();
        mContext = getContext();
        buttonClick();
    }

    private void validate(){

       final String email = etEmail.getText().toString();
       final String password = etPassword.getText().toString();

       if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
           Toasty.warning(getActivity().getApplicationContext(), "Masukkan email yang valid !", Toasty.LENGTH_SHORT).show();
       } else if (email.isEmpty() || password.isEmpty()){
           Toasty.warning(getActivity().getApplicationContext(), "Form Tidak Boleh Kosong !", Toasty.LENGTH_SHORT).show();
       } else if(Patterns.EMAIL_ADDRESS.matcher(email).matches() && !email.isEmpty() && !password.isEmpty()) {
           final ProgressDialog dialog = ProgressDialog.show(mContext, "Login",
                   "Loading. Mohon tunggu...", true);
           dialog.show();
           mAuth.signInWithEmailAndPassword(email, password)
                   .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               // Sign in success, update UI with the signed-in user's information
                               FirebaseUser user = mAuth.getCurrentUser();
                               String deviceToken = FirebaseInstanceId.getInstance().getToken();
                               UserDevice userDevice =
                                       new UserDevice(
                                               email,
                                               user.getUid(),
                                               password,
                                               Build.BRAND,
                                               Build.MODEL,
                                               deviceToken);
                               // Write a message to the database
                               FirebaseDatabase database = FirebaseDatabase.getInstance();
                               DatabaseReference myRef = database.getReference(user.getUid());

//                               myRef.setValue(user.getUid());
                               myRef.child(deviceToken).setValue(userDevice);
                               Log.d(TAG, "createUserWithEmail:success");
                               Toasty.info(mContext, "Login Berhasil !", Toast.LENGTH_SHORT).show();
                               getActivity().onBackPressed();
                               dialog.dismiss();

                           } else {
                               // If sign in fails, display a message to the user.
                               Log.w(TAG, "createUserWithEmail:failure", task.getException());
                               Toasty.error(mContext, "Login gagal !", Toast.LENGTH_SHORT).show();
                               dialog.dismiss();
                           }
                       }
                   });
       }

    }

    private void buttonClick(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new RegisterFragment()).addToBackStack("RegisterFragment").commit();
            }
        });
    }
}
