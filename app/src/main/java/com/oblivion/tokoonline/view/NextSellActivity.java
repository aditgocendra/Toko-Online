package com.oblivion.tokoonline.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.Utils.UniversalImageLoader;
import com.oblivion.tokoonline.model.ItemSell_model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.oblivion.tokoonline.Utils.UniversalImageLoader.setImage;

public class NextSellActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION_ADDRESS = 101;
    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION_CITY = 102;
    private FusedLocationProviderClient fusedLocationClient;
    private String urlDisplayImage;
    private final String mAppend = "file:/";
    private ProgressBar findLocationProgress;
    private ProgressDialog progressDialog;
    private EditText locationEdt, locationCityEdt, headerAdsEdt, numberPhoneEdt, priceItemEdt, descriptionItemEdt;
    private FirebaseUser mUser;
    private Spinner spinner;
    private String address;
    private String city;
    private String headerAds;
    private String price;
    private String numberPhone;
    private String description;
    private String subCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_sell);

        urlDisplayImage = getIntent().getStringExtra("urlLocationPhoto");
        subCategory = getIntent().getStringExtra("subKategori");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        initComponent();
        spinner();
    }

    private void initComponent() {

        findLocationProgress = findViewById(R.id.progress_find_location);
        findLocationProgress.setVisibility(View.INVISIBLE);
        locationEdt = findViewById(R.id.your_location_edt_address);
        locationCityEdt = findViewById(R.id.your_location_city_edt);
        headerAdsEdt = findViewById(R.id.header_ads_edt);
        numberPhoneEdt = findViewById(R.id.number_phone);

        priceItemEdt = findViewById(R.id.price_item);
        setCurrency(priceItemEdt);

        descriptionItemEdt = findViewById(R.id.description_item);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Proses upload silahkan tunggu");

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


    private void setCurrency(final EditText edt) {
        edt.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
//                if(!s.toString().equals(current)){
//                edt.removeTextChangedListener(this);
//                    Locale local = new Locale("id", "id");
//                    String cleanString = s.toString().replaceAll("[Rp,.]", "");
//
//                    double parsed = Double.parseDouble(cleanString);
//
//                    NumberFormat formatter = NumberFormat
//                            .getCurrencyInstance(local);
//                            formatter.setMaximumFractionDigits(0);
//                            formatter.setParseIntegerOnly(true);
//                    String formatted = NumberFormat.getCurrencyInstance().format((parsed));
//
//                    current = formatted;
//                    edt.setText(formatted);
//                    edt.setSelection(formatted.length());
//
//                    edt.addTextChangedListener(this);
//                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    edt.removeTextChangedListener(this);

                    Locale local = new Locale("id", "id");
//                    String replaceable = String.format("[Rp,.\\s]",
//                            NumberFormat.getCurrencyInstance().getCurrency()
//                                    .getSymbol(local));
                    String cleanString = s.toString().replaceAll("[Rp.,]",
                            "");

                    double parsed;
                    try {
                        parsed = Double.parseDouble(cleanString);
                    } catch (NumberFormatException e) {
                        parsed = 0.00;
                    }

                    NumberFormat formatter = NumberFormat
                            .getCurrencyInstance(local);
                    formatter.setMaximumFractionDigits(0);
                    formatter.setParseIntegerOnly(true);
                    String formatted = formatter.format((parsed));

//                    String replace = String.format("[Rp\\s]",
//                            NumberFormat.getCurrencyInstance().getCurrency()
//                                    .getSymbol(local));
//                    String clean = formatted.replaceAll(replace, "");

                    current = formatted;
                    edt.setText(current);
                    edt.setSelection(current.length());
                    edt.addTextChangedListener(this);
                }
            }
        });
    }


    private void getDataForm(){

         address = locationEdt.getText().toString().trim();
         city = locationCityEdt.getText().toString().trim();
         headerAds = headerAdsEdt.getText().toString().trim();
         price = priceItemEdt.getText().toString().trim();
         numberPhone = numberPhoneEdt.getText().toString().trim();
         description = descriptionItemEdt.getText().toString().trim();

    }

    private void validationForm(){

        getDataForm();

        if (headerAds.isEmpty()){
            headerAdsEdt.setError("Judul harus diisi");
            headerAdsEdt.requestFocus();
        }else if (headerAds.length() < 10){
            headerAdsEdt.setError("Judul kurang dari 15 character");
            headerAdsEdt.requestFocus();
        }else if(price.isEmpty()){
            priceItemEdt.setError("Harga harus diisi");
            priceItemEdt.requestFocus();
        }else if(numberPhone.isEmpty()){
            numberPhoneEdt.setError("Nomor harus diisi");
            numberPhoneEdt.requestFocus();
        }else if(numberPhone.length() < 10){
            numberPhoneEdt.setError("Format nomor tidak valid");
            numberPhoneEdt.requestFocus();
        }else if(address.isEmpty()){
            locationEdt.setError("Alamat Harus diisi");
            locationEdt.requestFocus();
        }else if(city.isEmpty()){
            locationEdt.setError("Kota Harus diisi");
            locationEdt.requestFocus();
        }else if(description.isEmpty()){
            descriptionItemEdt.setError("Deskripsi Harus diisi");
            descriptionItemEdt.requestFocus();
        }else if(description.length() < 25){
            descriptionItemEdt.setError("Deskripsi minimal 25 character");
            descriptionItemEdt.requestFocus();
        }else {
            postItemSell();
        }


    }

    private void postItemSell(){
        progressDialog.show();

        final StorageReference postItem = FirebaseStorage.getInstance().getReference("PostItem");

        Uri imageUri =  Uri.fromFile(new File(urlDisplayImage));
        String path  = urlDisplayImage;
        String filename= path.substring(path.lastIndexOf("/")+1);

        try {

            InputStream is = getContentResolver().openInputStream(imageUri);

            Bitmap bitmap = BitmapFactory.decodeStream(is);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] final_image = baos.toByteArray();


            final StorageReference fileToupload = postItem.child(filename);

            UploadTask uploadTask = fileToupload.putBytes(final_image);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    fileToupload.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String url = String.valueOf(uri);

                            pushItemToDatabase(url);

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(NextSellActivity.this, "Upload photo gagal", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void pushItemToDatabase(String urlPhotoItem){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("itemSell");
        String idUpload = reference.push().getKey();
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        String currentTime = DateFormat.getTimeInstance().format(calendar.getTime());

        ItemSell_model sell_model = new ItemSell_model(idUpload, headerAds, price, numberPhone,
                subCategory, spinner.getSelectedItem().toString() ,city, address, urlPhotoItem, description,
                mUser.getUid(), mUser.getDisplayName(), String.valueOf(mUser.getPhotoUrl()), currentDate, currentTime,
                "0", "0");

        reference.child(idUpload).setValue(sell_model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Toast.makeText(NextSellActivity.this, "Succsess", Toast.LENGTH_SHORT).show();
            }
        });








    }



    private void fetchlocation(String btn) {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(NextSellActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            findLocationProgress.setVisibility(View.INVISIBLE);

            if (btn.equals("address")){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_COARSE_LOCATION_ADDRESS);
            }else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_COARSE_LOCATION_CITY);
            }

        } else {

            getLastLocation(btn);
        }

    }

    private void getLastLocation(final String btn){

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

                            double lattiude = location.getLatitude();
                            double longtitude = location.getLongitude();

                            try {
                                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                List<Address> addresses = geocoder.getFromLocation(lattiude, longtitude, 1);
                                if (addresses != null && addresses.size() > 0){

                                    if (btn.equals("address")){
                                        String address = addresses.get(0).getAddressLine(0);
                                        findLocationProgress.setVisibility(View.INVISIBLE);
                                        locationEdt.setText(address);

                                    }else {

                                        String city = addresses.get(0).getSubAdminArea();
                                        locationCityEdt.setText(city);
                                    }


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
            case MY_PERMISSIONS_REQUEST_COARSE_LOCATION_ADDRESS:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation("address");
                }else {
                    Toast.makeText(this, "Izin Lokasi Diperlukan", Toast.LENGTH_SHORT).show();
                }

            }
            case MY_PERMISSIONS_REQUEST_COARSE_LOCATION_CITY:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation("city");
                }else {
                    Toast.makeText(this, "Izin Lokasi Diperlukan", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    public void share_location_click(View view) {
        String btn  = "address";
        findLocationProgress.setVisibility(View.VISIBLE);
        fetchlocation(btn);
    }

    private void spinner(){
        spinner = findViewById(R.id.spinner_condition);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.condition, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
       spinner.setSelection(0);
    }


    public void share_city_location(View view) {
        String btn = "city";
        fetchlocation(btn);

    }

    public void post_ads(View view) {
        validationForm();
    }
}
