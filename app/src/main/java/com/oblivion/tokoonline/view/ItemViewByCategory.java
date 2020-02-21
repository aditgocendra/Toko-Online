package com.oblivion.tokoonline.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.adapter.RecycleItemAdapter;
import com.oblivion.tokoonline.adapter.SliderAdapter;
import com.oblivion.tokoonline.model.ItemSell_model;
import com.oblivion.tokoonline.model.User_model;
import com.oblivion.tokoonline.view.category.AccElectronicActivity;
import com.oblivion.tokoonline.view.category.BookAndPenActivity;
import com.oblivion.tokoonline.view.category.ElectronicActivity;
import com.oblivion.tokoonline.view.category.FashionActivity;
import com.oblivion.tokoonline.view.category.FoodAndDrinkActivity;
import com.oblivion.tokoonline.view.category.VehicleActivity;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemViewByCategory extends AppCompatActivity {


    private List<ItemSell_model> models;
//    private List<ItemSell_model> paginatedItem;
    private List<User_model> user_models;
//    private int mResult;


    private RecyclerView viewItemRecycle;
    private RecycleItemAdapter itemAdapter;
    private String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view_by_category);

        String subCategory = getIntent().getStringExtra("subKategori");
        activity = getIntent().getStringExtra("activity");

        setSlider();

//        getUserLocation();
        setItem(subCategory);

        viewItemRecycle = findViewById(R.id.recycle_item);

        viewItemRecycle.setHasFixedSize(true);
        viewItemRecycle.setLayoutManager(new GridLayoutManager(this, 2));

        itemAdapter = new RecycleItemAdapter(this, models);
        viewItemRecycle.setAdapter(itemAdapter);

        setAdmob();

        Toolbar toolbar = findViewById(R.id.toolbar_item_category);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activity.equals("electronic")){

                    Intent intent = new Intent(ItemViewByCategory.this, ElectronicActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else if (activity.equals("AccElectronic")){

                    Intent intent = new Intent(ItemViewByCategory.this, AccElectronicActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else if (activity.equals("bookandpen")){

                    Intent intent = new Intent(ItemViewByCategory.this, BookAndPenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else if (activity.equals("fashion")){

                    Intent intent = new Intent(ItemViewByCategory.this, FashionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else if (activity.equals("foodanddrink")){

                    Intent intent = new Intent(ItemViewByCategory.this, FoodAndDrinkActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{

                    Intent intent = new Intent(ItemViewByCategory.this, VehicleActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        });


    }


    private void setSlider(){

        SliderView sliderView =  findViewById(R.id.imageSlider);
        SliderAdapter adapter = new SliderAdapter(this);
        sliderView.setSliderAdapter(adapter);


        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINDEPTHTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setScrollTimeInSec(5); //set scroll delay in seconds :
        sliderView.startAutoCycle();

    }

    private void setItem(String subCategory){

        models = new ArrayList<>();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("itemSell");

        reference.orderByChild("category").equalTo(subCategory).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                        ItemSell_model model = snapshot.getValue(ItemSell_model.class);
                        models.add(model);

                    }

                   itemAdapter.notifyDataSetChanged();

                }else {
                    Toast.makeText(ItemViewByCategory.this, "Belum ada post", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void getUserLocation(){

        user_models = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

        reference.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){

                        User_model model = ds.getValue(User_model.class);
                        user_models.add(model);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void setAdmob() {

        final AdView mAdView;

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });


    }


//    private void setPaginatedItem(){
//
//        paginatedItem = new ArrayList<>();
//
//        if (models != null){
//
//            try {
//
//                int iterations = models.size();
//
//                if (iterations > 10){
//                    iterations = 10;
//                }
//
//                mResult = 10;
//
//                for (int i = 0; i < iterations; i++ ){
//                    paginatedItem.add(models.get(i));
//                }
//
//                itemAdapter.notifyDataSetChanged();
//
//            }catch (NullPointerException e){
//                e.printStackTrace();
//            }catch (IndexOutOfBoundsException e){
//                e.printStackTrace();
//            }
//
//        }
//
//    }

//    private void displayMorePost(){
//
//        try {
//            if (models.size() > mResult && models.size() > 0){
//                int iterations;
//
//                if (models.size() > (mResult + 10)){
//                    //display more
//                    iterations = 10;
//                }else {
//                    iterations = models.size() - mResult;
//                }
//
//                //add new post
//                for (int i = 0; i < mResult + iterations; i++){
//
//                    paginatedItem.add(models.get(i));
//
//                }
//                itemAdapter.notifyDataSetChanged();
//            }
//        }catch (NullPointerException e){
//            e.printStackTrace();
//        }catch (IndexOutOfBoundsException e){
//            e.printStackTrace();
//        }

//    }




}
