package com.oblivion.tokoonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email_edt, pass_edt, confirm_edt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponent();

        mAuth = FirebaseAuth.getInstance();


    }

    private void initComponent(){

        email_edt = findViewById(R.id.email_editText);
        pass_edt = findViewById(R.id.pass_editText);
        confirm_edt = findViewById(R.id.confirm_editText);

    }

    public void daftar_btn(View view) {

        validasiForm();
    }

    private void validasiForm(){

        String email = email_edt.getText().toString().trim();
        String pass = pass_edt.getText().toString().trim();
        String confirm_pass = confirm_edt.getText().toString().trim();

        if (email.isEmpty()){
            email_edt.setError("Email Dibutuhkan");
            email_edt.requestFocus();
        }else if(!ValidasiEmail(email)){
            email_edt.setError("Format email salah");
            email_edt.requestFocus();
        }else  if(pass.isEmpty()){
            pass_edt.setError("Kata Sandi Masih Kosong");
            pass_edt.requestFocus();
        }else if(confirm_pass.isEmpty()){
            confirm_edt.setError("Konfirmasi Sandi Harus Diisi");
            confirm_edt.requestFocus();
        }else if(!pass.equals(confirm_pass)){
            Toast.makeText(this, "Sandi dan konfirmasi sandi tidak sama", Toast.LENGTH_SHORT).show();

        }else{
           createUser(email, pass);
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


    private void createUser(String email, String pass){

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final FirebaseUser user = mAuth.getCurrentUser();

                            assert user != null;
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this,
                                                "Daftar berhasil, silahkan lakukan verifikasi", Toast.LENGTH_LONG).show();
                                        updateUI(user);
                                    }else {
                                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                        } else {

                            Toast.makeText(RegisterActivity.this, "Daftar gagal, silahkan coba beberapa saat lagi",
                                    Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }


                    }
                });

    }

    private void updateUI(FirebaseUser user) {

        if (user != null){

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


}
