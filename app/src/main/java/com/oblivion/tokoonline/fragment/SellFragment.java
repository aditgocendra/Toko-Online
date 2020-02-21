package com.oblivion.tokoonline.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.Utils.FilePaths;
import com.oblivion.tokoonline.Utils.FileSearch;
import com.oblivion.tokoonline.Utils.UniversalImageLoader;
import com.oblivion.tokoonline.adapter.GridImageAdapter;
import com.oblivion.tokoonline.view.ChoseCategorySell;
import com.oblivion.tokoonline.view.NextSellActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import static com.oblivion.tokoonline.Utils.UniversalImageLoader.setImage;


public class SellFragment extends Fragment {

    private static final int NUM_GRID_COLUMNS = 3;
    private static final int PERMISSION_CODE = 1001 ;
    private GridView gridView;
    private ImageView imageView;
    private ProgressBar progressBar;
    private ArrayList<String> directories;
    private Spinner directorySpinner;

    private final String mAppend = "file:/";
    private ArrayList<String> imgURL;

    private String urlFile;



    public SellFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sell, container, false);


        directorySpinner = view.findViewById(R.id.spinnerDirectory);
        gridView = view.findViewById(R.id.myGrid);
        imageView = view.findViewById(R.id.image_pick);
        progressBar = view.findViewById(R.id.progres);
        progressBar.setVisibility(View.INVISIBLE);

        directories = new ArrayList<>();

        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getContext());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());


        TextView next_btn = view.findViewById(R.id.next_id);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (urlFile != null){

                    updateUI(urlFile);
                }else {
                    updateUI(imgURL.get(0));

                }

            }
        });




        reqPermission();

        return view;
    }

    private void init(){
        FilePaths filePath = new FilePaths();

        directories.add(filePath.PICTURES);

        if(FileSearch.getDirectoryPath(filePath.PICTURES) != null ){
            directories.addAll(FileSearch.getDirectoryPath(filePath.PICTURES)) ;
        }

        directories.add(filePath.CAMERA);
        directories.add(filePath.WA);
        directories.add(filePath.Screenshoot);
        directories.add(filePath.Bluetooth);
//        if (FileSearch.getFilePath(filePath.CAMERA) != null){
//            directories.add(filePath.CAMERA);
//        }
//        if (FileSearch.getFilePath(filePath.WA) != null){
//            directories.add(filePath.WA);
//        }
//        if (FileSearch.getFilePath(filePath.Screenshoot) != null){
//            directories.add(filePath.Screenshoot);
//        }
//        if (FileSearch.getFilePath(filePath.Bluetooth) != null){
//            directories.add(filePath.Bluetooth  );
//        }


      //  Toast.makeText(getContext(), String.valueOf(directories), Toast.LENGTH_SHORT).show();




            final ArrayList<String> data = new ArrayList<>();
            getNameDirectory(data);



            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                    R.layout.spinner_item_style, data);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            directorySpinner.setAdapter(adapter);

            directorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("TAG", "onItemClick: selected: " + directories.get(position));

                    //setup our image grid for the directory chosen
                        setupGridView(directories.get(position));


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });





    }

    private void setupGridView(String s){
        imgURL = FileSearch.getFilePath(s);
        int gridWith = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWith/NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imageWidth);


        final GridImageAdapter adapter = new GridImageAdapter(getContext(), R.layout.item_grid, mAppend, imgURL);
        gridView.setAdapter(adapter);


       if (imgURL.size() > 0){

        setImage(imgURL.get(0), imageView, progressBar, mAppend);
       }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("TAG", "onItemClick: selected an image: " + imgURL.get(position));
                if (imgURL.size() > 0) {
                    setImage(imgURL.get(position), imageView, progressBar, mAppend);

                    urlFile = imgURL.get(position);
                }


            }
        });

    }




    private void getNameDirectory(ArrayList<String> data){



        for (int i = 0; i < directories.size(); i++){

            String absolutePath = directories.get(i);
            String directoryName = null;
            if((absolutePath != null) && (absolutePath.length()>0)){
                int indexOfFileSeparator = absolutePath.lastIndexOf("/");
                if (indexOfFileSeparator >= 0) {
                    directoryName = absolutePath.substring(indexOfFileSeparator + 1);
                    data.add(directoryName);
                }
            }

        }


    }

    private void reqPermission(){

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){

                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permission, PERMISSION_CODE);


            }else {
                //permission granted

                init();
            }
        }else {
            // os system < marsmellow
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                }
            }
        }
    }


    private void updateUI(String url){

        Intent intent = new Intent(getContext(), ChoseCategorySell.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("urlLocationPhoto", url);
        startActivity(intent);



    }
}
