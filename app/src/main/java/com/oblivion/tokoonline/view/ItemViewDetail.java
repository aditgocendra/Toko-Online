package com.oblivion.tokoonline.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.Utils.FavoriteUpdate;
import com.oblivion.tokoonline.Utils.NumberFormat;
import com.squareup.picasso.Picasso;

public class ItemViewDetail extends AppCompatActivity {


    private static final int REQUEST_CALL = 123;
    private String idItem,
            urlItem,
            headerAds,
            priceItem,
            condition,
            city,
            address,
            date,
            description,
            urlUser,
            nameUser,
            phoneNumber,
            subCategory,
            activity;

    private FavoriteUpdate favoriteUpdate;
    private ImageView favoriteImage;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view_detail);

        idItem = getIntent().getStringExtra("idItem");
        urlItem = getIntent().getStringExtra("urlPhotoPost");
        headerAds = getIntent().getStringExtra("headerAds");
        priceItem = getIntent().getStringExtra("priceItem");
        condition = getIntent().getStringExtra("condition");
        city = getIntent().getStringExtra("city");
        address = getIntent().getStringExtra("address");
        date = getIntent().getStringExtra("date");
        description = getIntent().getStringExtra("description");
        urlUser = getIntent().getStringExtra("urlPhotoUser");
        nameUser = getIntent().getStringExtra("nameUserPost");
        phoneNumber = getIntent().getStringExtra("numberPhone");
        subCategory = getIntent().getStringExtra("subCategory");
        activity = getIntent().getStringExtra("activity");

        favoriteUpdate = new FavoriteUpdate();

        initComponent();


    }


    private void initComponent() {

        NumberFormat format = new NumberFormat();



        favoriteImage = findViewById(R.id.favorite_action);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        favoriteUpdate.setUserFavoriteItem(mUser.getUid(), idItem, favoriteImage);

        ImageView viewPostImage = findViewById(R.id.detail_photo_item);
        ImageView viewUserPost = findViewById(R.id.detail_photo_user_item);

        TextView headerTxt = findViewById(R.id.detail_header_item);
        TextView priceTxt = findViewById(R.id.detail_price_item);
        TextView conditionTxt = findViewById(R.id.detail_condition_item);
        TextView cityTxt = findViewById(R.id.detail_location_city);
        TextView addressTxt = findViewById(R.id.detail_address_item);
        TextView dateTxt = findViewById(R.id.detail_date_item);
        TextView descriptionTxt = findViewById(R.id.detail_deskripsi_item);
        TextView nameTxt = findViewById(R.id.detail_name_user);
        TextView phoneTxt = findViewById(R.id.detail_phone_item);


        Picasso.get().load(urlItem).fit().centerCrop().into(viewPostImage);
        Picasso.get().load(urlUser).fit().centerCrop().into(viewUserPost);

        headerTxt.setText(headerAds);
        priceTxt.setText(format.currencyFormatter(priceItem));
        conditionTxt.setText(condition);
        cityTxt.setText(city);
        addressTxt.setText(address);
        dateTxt.setText(date);
        descriptionTxt.setText(description);
        nameTxt.setText(nameUser);
        phoneTxt.setText(phoneNumber);

        Toolbar toolbar = findViewById(R.id.toolbar_detail_item);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activity.equals("home")){

                    Intent intent = new Intent(ItemViewDetail.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else if (activity.equals("settingActivity")){
                    Intent intent = new Intent(ItemViewDetail.this, MainActivity.class);
                    intent.putExtra("activity", activity);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(ItemViewDetail.this, ItemViewByCategory.class);
                    intent.putExtra("subKategori", subCategory);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.report:

                Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();

                return true;


            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void favorite_click(View view) {

        favoriteUpdate.userUpdateFavoriteItem(idItem, favoriteImage, ItemViewDetail.this);


    }

    public void click_phone_call(View view) {

        CallPhone();

    }

    private void CallPhone(){
        if (ContextCompat.checkSelfPermission(ItemViewDetail.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(ItemViewDetail.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

        }else{

            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                CallPhone();
            }else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void send_message_click(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:" + phoneNumber));
        startActivity(intent);

    }


    @Override
    public void onBackPressed() {
        if (activity.equals("home")){
            Intent intent = new Intent(ItemViewDetail.this, MainActivity.class);
            startActivity(intent);
        }else if (activity.equals("settingActivity")){
            Intent intent = new Intent(ItemViewDetail.this, MainActivity.class);
            intent.putExtra("activity", activity);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(ItemViewDetail.this, ItemViewByCategory.class);
            intent.putExtra("subKategori", subCategory);
            startActivity(intent);
        }

    }
}
