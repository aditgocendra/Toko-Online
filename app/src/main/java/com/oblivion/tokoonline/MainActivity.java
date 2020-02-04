package com.oblivion.tokoonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText email_login_edt, pass_login_edt;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
        mAuth = FirebaseAuth.getInstance();
    }

    private void initComponent(){

        email_login_edt = findViewById(R.id.email_login_editText);
        pass_login_edt = findViewById(R.id.sandi_login_editText);

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
                            updateUI(user);
                        } else {

                            Toast.makeText(MainActivity.this, "Login gagal, harap coba beberap saat lagi",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                    }
                });

    }

    private void updateUI(FirebaseUser user) {

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
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

        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);

    }

}
