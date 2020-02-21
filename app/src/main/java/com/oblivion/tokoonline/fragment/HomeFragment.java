package com.oblivion.tokoonline.fragment;


import android.content.Intent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.adapter.ItemHorizontalAdapter;
import com.oblivion.tokoonline.adapter.SliderAdapter;
import com.oblivion.tokoonline.model.ItemSell_model;
import com.oblivion.tokoonline.view.PickLocationActivity;

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


public class HomeFragment extends Fragment {

    private View view;
    private List<ItemSell_model> models;
    private RecyclerView horizontalItem;
    private ItemHorizontalAdapter itemHorizontalAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setSlider();
        setHasOptionsMenu(true);

        setAdmob(view);

        setNewItem();

        initComponent(view);

        return view;
    }


    private void initComponent(View view){

        LinearLayout electronic_btn ,acc_elec_btn, book_pen_btn, fashion_btn, vehicle_btn, food_and_drink_btn;


        electronic_btn = view.findViewById(R.id.kat_electronic);
        acc_elec_btn = view.findViewById(R.id.kat_acc_electronic);
        book_pen_btn = view.findViewById(R.id.kat_book_and_pen);
        fashion_btn = view.findViewById(R.id.kat_fashion);
        vehicle_btn = view.findViewById(R.id.kat_vehicle);
        food_and_drink_btn = view.findViewById(R.id.kat_food_and_drink);


        itemHorizontalAdapter = new ItemHorizontalAdapter(getContext(), models);

        horizontalItem = view.findViewById(R.id.horizontal_item_view);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
//        manager.setStackFromEnd(true);

        horizontalItem.setLayoutManager(manager);
        horizontalItem.setAdapter(itemHorizontalAdapter);




        electronic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =  new Intent(getContext(), ElectronicActivity.class);
                startActivity(intent);

            }
        });


        acc_elec_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getContext(), AccElectronicActivity.class);
                startActivity(intent);

            }
        });

        book_pen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BookAndPenActivity.class);
                startActivity(intent);
            }
        });

        fashion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getContext(), FashionActivity.class);
                startActivity(intent);
            }
        });

        vehicle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(), VehicleActivity.class);
                startActivity(intent);
            }
        });

        food_and_drink_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FoodAndDrinkActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.topbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
//            case R.id.notif:
//
//                Toast.makeText(getContext(), "Belum dibuat", Toast.LENGTH_SHORT).show();
//                return true;

            case R.id.location:

                Intent intent = new Intent(getContext(), PickLocationActivity.class);
                startActivity(intent);
                return true;

            case R.id.search://            case R.id.search:
//
//                Fragment newFragment = new SearchFragment();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.frame_layout, newFragment, "fragment");
//                fragmentTransaction.commit();
//                return true;

                Fragment newFragment = new SearchFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, newFragment, "fragment");
                fragmentTransaction.commit();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void setNewItem(){
        models = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("itemSell");

        reference.limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    ItemSell_model itemSell_model = snapshot.getValue(ItemSell_model.class);
                    models.add(itemSell_model);

                }
                itemHorizontalAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void setSlider(){

        SliderView sliderView =  view.findViewById(R.id.imageSlider);
        SliderAdapter adapter = new SliderAdapter(getContext());
        sliderView.setSliderAdapter(adapter);


        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINDEPTHTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setScrollTimeInSec(5); //set scroll delay in seconds :
        sliderView.startAutoCycle();


    }


    private void setAdmob(View view) {

        final AdView mAdView;

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = view.findViewById(R.id.adView);
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




}
