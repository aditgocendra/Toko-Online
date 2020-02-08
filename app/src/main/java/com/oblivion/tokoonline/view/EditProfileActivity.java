package com.oblivion.tokoonline.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.telecom.PhoneAccount;
import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.model.User_model;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity {


    private static final int PICK_MEDIA_GALLERY = 1001 ;
    private FirebaseUser mUser;
    private ImageView userPhoto;
    private TextView displayName;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initComponent();



    }

    private void initComponent(){

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        userPhoto = findViewById(R.id.photo_user_img);
        displayName = findViewById(R.id.userDisplayName);

        progressBar = findViewById(R.id.spin_kit);
        Sprite circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);
        progressBar.setVisibility(View.INVISIBLE);

        ImageView topbar_back = findViewById(R.id.tool_back);

        topbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditProfileActivity.this, SettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });


        setDataComponent();

    }

    private void setDataComponent(){


        if (mUser.getDisplayName() != null){
            displayName.setText(mUser.getDisplayName());
        }

        if (mUser.getPhotoUrl() != null){
            Picasso.get()
                    .load(mUser.getPhotoUrl())
                    .fit()
                    .centerCrop()
                    .into(userPhoto);

        }

    }

    private void alertDialogName(){

        View view = LayoutInflater.from(EditProfileActivity.this).inflate(R.layout.dialog_alert, null);

        final EditText editText = view.findViewById(R.id.username_edit);
        editText.setText(mUser.getDisplayName());
        final AlertDialog.Builder dialogEdit = new AlertDialog.Builder(EditProfileActivity.this);

        dialogEdit.setView(view)
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nama = editText.getText().toString().trim();
                        updateNamaUserAuth(nama);
                    }
                });

        dialogEdit.setNegativeButton("Batalkan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = dialogEdit.create();

        alertDialog.show();

    }

    private void updateNamaUserAuth(final String namaBaru){

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(namaBaru)
                .build();

        mUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updateNamaUserData(namaBaru);
                        }else {
                            Toast.makeText(EditProfileActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void updateNamaUserData(final String namaBaru) {

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.orderByKey().equalTo(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    String key = ds.getKey();
                    ref.child(key).child("name").setValue(namaBaru);

                }

                setDataComponent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void edit_photo_user(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"pilih gambar"), PICK_MEDIA_GALLERY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null){

            Uri fileUri = data.getData();
            try {

                InputStream is = getContentResolver().openInputStream(fileUri);

                Bitmap bitmap = BitmapFactory.decodeStream(is);
                userPhoto.setImageBitmap(bitmap);

            }catch (Exception e){
                e.printStackTrace();

            }

            updatePhotoProfile(fileUri);

        }

    }

    private void updatePhotoProfile(Uri fileUri){

        String nameFile = getFileName(fileUri);

        StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference();

        try {

            InputStream is = getContentResolver().openInputStream(fileUri);

            Bitmap bitmap = BitmapFactory.decodeStream(is);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] final_image = baos.toByteArray();

            final StorageReference fileToupload = firebaseStorage.child("PhotoUser").child(nameFile);
            UploadTask uploadTask = fileToupload.putBytes(final_image);
            progressBar.setVisibility(View.VISIBLE);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    fileToupload.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(final Uri uri) {

                            final String url = String.valueOf(uri);

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(Uri.parse(url))
                                    .build();

                            mUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                updateUrlUser(url);

                                            }else {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(EditProfileActivity.this, "Photo Gagal Diubah", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(EditProfileActivity.this, "Gagal mengupload", Toast.LENGTH_SHORT).show();
                }
            });


        }catch (IOException e){
            e.printStackTrace();
        }



    }

    private void updateUrlUser(String url) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.child(mUser.getUid()).child("photo_url").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(EditProfileActivity.this, "Photo berhasil diubah", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getFileName(Uri uri){
        String result = null;
        if (uri.getScheme().equals("content")){
            Cursor cursor = getContentResolver().query(uri , null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }
        if (result == null){
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1){
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public void edit_name_profile(View view) {

        alertDialogName();
    }


}
