package com.oblivion.tokoonline.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.adapter.RecycleItemAdapter;
import com.oblivion.tokoonline.model.Favorite_model;
import com.oblivion.tokoonline.model.ItemSell_model;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {

    private List<ItemSell_model> models;
    private RecyclerView viewItemRecycle;
    private RecycleItemAdapter itemAdapter;


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);

        Toolbar toolbar = v.findViewById(R.id.toolBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Favorite");
        setHasOptionsMenu(true);

        getData();

        viewItemRecycle = v.findViewById(R.id.recycle_favorite);
        viewItemRecycle.setHasFixedSize(true);
        viewItemRecycle.setLayoutManager(new GridLayoutManager(getContext(), 2));

        models = new ArrayList<>();
        itemAdapter = new RecycleItemAdapter(getContext(), models);

        return v;

    }

    private void setItem(String idUpload){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("itemSell");

        reference.orderByChild("idUpload").equalTo(idUpload).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                        ItemSell_model model = snapshot.getValue(ItemSell_model.class);
                        models.add(model);

                    }
//                    itemAdapter.notifyDataSetChanged();
                    viewItemRecycle.setAdapter(itemAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void getData(){


        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("userItemFavorite");

        reference.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                            Favorite_model model = snapshot.getValue(Favorite_model.class);
                            setItem(model.getIdUpload());
                        }


                    }else {
                        Toast.makeText(getContext(), "Anda belum menambahkan barang favorite", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
