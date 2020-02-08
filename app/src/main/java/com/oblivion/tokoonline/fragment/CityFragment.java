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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.adapter.CityAdapter;
import com.oblivion.tokoonline.adapter.ProvinceAdapter;
import com.oblivion.tokoonline.model.City_model;
import com.oblivion.tokoonline.model.Province_model;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityFragment extends Fragment {


    private CityAdapter adapter;
    private List<City_model> city_models;
    private LinearLayoutManager manager;
    private RecyclerView mCity;
    private String province;

    public CityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        province = getArguments().getString("provinsi");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_city, container, false);

        mCity = v.findViewById(R.id.recyclerView_listCity);

        manager = new LinearLayoutManager(getContext());
        mCity.setHasFixedSize(true);
        mCity.setLayoutManager(manager);

        city_models = new ArrayList<>();

        adapter = new CityAdapter(getContext(), city_models);
        mCity.setAdapter(adapter);
        getCityData();

        return v;
    }


    private void getCityData(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("detailLokasi");

        reference.child(province).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    city_models.add(ds.getValue(City_model.class));

                }
                mCity.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
