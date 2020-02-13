package com.oblivion.tokoonline.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.adapter.ProvinceAdapter;
import com.oblivion.tokoonline.model.Province_model;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProvinceFragment extends Fragment {

    private ProvinceAdapter adapter;
    private List<Province_model> province_models;
    private LinearLayoutManager manager;
    private RecyclerView mProvince;
    private String activity;


    public ProvinceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_province, container, false);

        mProvince = view.findViewById(R.id.recyclerView_listProvinsi);

        manager = new LinearLayoutManager(getContext());
        mProvince.setHasFixedSize(true);
        mProvince.setLayoutManager(manager);

        province_models = new ArrayList<>();

        adapter = new ProvinceAdapter(getContext(), province_models);
        mProvince.setAdapter(adapter);
        getProvinceData();

        return view;
    }


    private void getProvinceData(){



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lokasi");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    province_models.add(ds.getValue(Province_model.class));

                }
                mProvince.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
