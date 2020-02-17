package com.oblivion.tokoonline.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.model.User_model;

public class LoginActivity extends AppCompatActivity {

    private EditText email_login_edt, pass_login_edt;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponent();
        mAuth = FirebaseAuth.getInstance();
    }

    private void initComponent(){

        email_login_edt = findViewById(R.id.email_login_editText);
        pass_login_edt = findViewById(R.id.sandi_login_editText);

        progressBar = findViewById(R.id.spin_kit);
        Sprite circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);
        progressBar.setVisibility(View.INVISIBLE);

        reference = FirebaseDatabase.getInstance().getReference("Users");
    }

    public void masuk(View view) {
        validasiForm();
    }

    private void validasiForm(){
        String email = email_login_edt.getText().toString().trim();
        String pass = pass_login_edt.getText().toString().trim();

        if (email.isEmpty()){
            email_login_edt.setError("Email harus diisi");
            email_login_edt.requestFocus();
        }else if(!ValidasiEmail(email)){
            email_login_edt.setError("Format email salah");
        }else if(pass.isEmpty()){
            pass_login_edt.setError("Kata sandi masih kosong");
            pass_login_edt.requestFocus();
        }else{
            progressBar.setVisibility(View.VISIBLE);
            userLogin(email, pass);
        }

    }

    private void userLogin(String email, String pass){

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user.isEmailVerified()){
                                saveUser(user);
                            }else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this, "Akun Belum Terverifikasi", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Login gagal, harap coba beberap saat lagi",
                                    Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }


                    }
                });

    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private static boolean ValidasiEmail(String email){
        boolean validasi;
        String emailPatern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

        if (email.matches(emailPatern) || email.matches(emailPattern2) && email.length() > 0)
        {
            validasi = true;
        }else{
            validasi = false;
        }

        return validasi;
    }

    public void daftar_txt(View view) {

        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
         
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()){
            updateUI(currentUser);
        }else {
            updateUI(null);
        }

    }

    public void help_login(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
        startActivity(intent);

    }

    private void saveUser(final FirebaseUser user){

        reference.orderByChild("userId").equalTo(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){

                    User_model user_model = new User_model(user.getUid(),
                            user.getDisplayName(),
                            user.getEmail(), "null",
                            String.valueOf(user.getPhotoUrl()),
                            "null");
                    reference.child(user.getUid()).setValue(user_model);
                    progressBar.setVisibility(View.INVISIBLE);
                    updateUI(user);

                }else {
                    updateUI(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
                updateUI(null);
                Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
