package com.oblivion.tokoonline.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.oblivion.tokoonline.R;

public class ForgotPassword extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    EditText email_send_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();
        email_send_edt = findViewById(R.id.email_forgot_editText);


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

    public void forgot_password(String email)
    {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               // progressBar.setVisibility(View.INVISIBLE);
                if (task.isSuccessful())
                {
                    Toast.makeText(ForgotPassword.this,
                            "Kami Telah Mengirimkan Bantuan Lewat Email Anda",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(ForgotPassword.this, task.getException().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void send_help(View view) {

        String email = email_send_edt.getText().toString().trim();

        if (email.isEmpty()){
            email_send_edt.setError("Anda belum memasukkan email anda");
            email_send_edt.requestFocus();
        }else if(!ValidasiEmail(email)){
            email_send_edt.setError("Format email salah");
            email_send_edt.requestFocus();
        }else {

            forgot_password(email);

        }


    }
}
