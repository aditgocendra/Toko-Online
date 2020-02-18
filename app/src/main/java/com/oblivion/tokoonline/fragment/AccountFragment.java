package com.oblivion.tokoonline.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.adapter.ItemMySellAdapter;
import com.oblivion.tokoonline.model.ItemSell_model;
import com.oblivion.tokoonline.view.SettingActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class AccountFragment extends Fragment {

    private View view;
    private List<ItemSell_model> models;
    private RecyclerView recyclerItemAccount;
    private ItemMySellAdapter sellAdapter;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Account");

        setHasOptionsMenu(true);

        initComponent();

        return view;
    }

    private void initComponent(){

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        TextView viewDisplayName = view.findViewById(R.id.display_name);
        ImageView viewImageProfile = view.findViewById(R.id.image_profile);
        viewDisplayName.setText(mUser.getDisplayName());

        if (mUser.getPhotoUrl() != null){
            Picasso.get().load(mUser.getPhotoUrl()).fit().centerCrop().into(viewImageProfile);

        }

        recyclerItemAccount = view.findViewById(R.id.recycle_item_account);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerItemAccount.setLayoutManager(manager);

        getDataItem();

        sellAdapter = new ItemMySellAdapter(getContext(), models);
        recyclerItemAccount.setAdapter(sellAdapter);


   }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_right_account, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.setting:

                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                return true;


                default:
                    return super.onOptionsItemSelected(item);
        }

    }


    private void getDataItem(){

        models = new ArrayList<>();

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("itemSell");

        reference.orderByChild("userId").equalTo(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){

                        ItemSell_model sell_model = ds.getValue(ItemSell_model.class);
                        models.add(sell_model);

                    }
                    sellAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(), "Anda belum memosting apapun", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
