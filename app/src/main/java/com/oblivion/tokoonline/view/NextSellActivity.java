package com.oblivion.tokoonline.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.Utils.UniversalImageLoader;

import java.util.List;
import java.util.Locale;

import static com.oblivion.tokoonline.Utils.UniversalImageLoader.setImage;

public class NextSellActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 101;
    private FusedLocationProviderClient fusedLocationClient;
    private String urlDisplayImage;
    private final String mAppend = "file:/";
    private ProgressBar findLocationProgress;
    private EditText locationEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_sell);

        urlDisplayImage = getIntent().getStringExtra("urlLocation");

        initComponent();



    }

    private void initComponent(){

        findLocationProgress = findViewById(R.id.progress_find_location);
        findLocationProgress.setVisibility(View.INVISIBLE);
        locationEdt = findViewById(R.id.your_location_edt);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        ImageView showImage = findViewById(R.id.image_pick_show);
        ProgressBar progressBar = findViewById(R.id.progress_image_sell);
        progressBar.setVisibility(View.INVISIBLE);
        ImageView tool_back = findViewById(R.id.tool_back);


        UniversalImageLoader universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

        setImage(urlDisplayImage, showImage, progressBar, mAppend);


        tool_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NextSellActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("activity", "nextsellActivity");
                startActivity(intent);
            }
        });

    }

    private void fetchlocation() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(NextSellActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            findLocationProgress.setVisibility(View.INVISIBLE);

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
        } else {

            getLastLocation();
        }

    }

    private void getLastLocation(){

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

                            Double lattiude = location.getLatitude();
                            Double longtitude = location.getLongitude();

                            try {
                                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                List<Address> addresses = geocoder.getFromLocation(lattiude, longtitude, 1);
                                if (addresses != null && addresses.size() > 0){

                                    String address = addresses.get(0).getAddressLine(0);

                                    findLocationProgress.setVisibility(View.INVISIBLE);
                                    locationEdt.setText(address);

                                }
                            }catch (Exception e){
                                Toast.makeText(NextSellActivity.this, "eroor : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_COARSE_LOCATION:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                }else {
                    Toast.makeText(this, "Izin Lokasi Diperlukan", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    public void share_location_click(View view) {

        findLocationProgress.setVisibility(View.VISIBLE);
        fetchlocation();
    }
}
