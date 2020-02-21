package com.oblivion.tokoonline.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.model.ItemSell_model;
import com.oblivion.tokoonline.view.ItemViewDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemMySellAdapter extends RecyclerView.Adapter<ItemMySellAdapter.ItemMysellVH> {


    private Context mContext;
    private List<ItemSell_model> itemSellModels;

    public ItemMySellAdapter(Context mContext, List<ItemSell_model> itemSellModels){
        this.mContext = mContext;
        this.itemSellModels = itemSellModels;


    }


    @NonNull
    @Override
    public ItemMysellVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item_sell, parent, false);
        return new ItemMysellVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemMysellVH holder, final int position) {
        final ItemSell_model model = itemSellModels.get(position);

        Picasso.get().load(model.getUrlPhotoItem()).fit().centerCrop().into(holder.itemAccount);
        holder.headerAccount.setText(model.getHeaderAds());
        holder.priceAccount.setText(model.getPrice());

        holder.layoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(mContext, ItemViewDetail.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("idItem", model.getIdUpload());
                intent.putExtra("urlPhotoPost", model.getUrlPhotoItem());
                intent.putExtra("headerAds", model.getHeaderAds());
                intent.putExtra("priceItem", model.getPrice());
                intent.putExtra("condition", model.getCondition());
                intent.putExtra("city", model.getCity());
                intent.putExtra("address", model.getAddress());
                intent.putExtra("date", model.getTanggal());
                intent.putExtra("description", model.getDescription());
                intent.putExtra("urlPhotoUser", model.getUrlPhotoUser());
                intent.putExtra("nameUserPost", model.getNamaUser());
                intent.putExtra("numberPhone", model.getNumber());
                intent.putExtra("subCategory", model.getCategory());
                intent.putExtra("activity", "settingActivity");

                mContext.startActivity(intent);
            }
        });


        holder.moreSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                more_sett(model, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemSellModels.size();
    }

    public class ItemMysellVH extends RecyclerView.ViewHolder {

        private ImageView itemAccount, moreSetting;
        private TextView headerAccount, priceAccount;
        private LinearLayout layoutClick;

        public ItemMysellVH(@NonNull View itemView) {
            super(itemView);

            itemAccount = itemView.findViewById(R.id.image_item_account);
            headerAccount = itemView.findViewById(R.id.header_ads_account);
            priceAccount = itemView.findViewById(R.id.price_item_account);
            moreSetting = itemView.findViewById(R.id.more_set);
            layoutClick = itemView.findViewById(R.id.click_item_account);
        }
    }


    private void more_sett(final ItemSell_model model_sell, final int positionData){

        ListView listView = new ListView(mContext);

        final List<String> data = new ArrayList<>();
        data.add("Hapus Post");
        // data.add("Share");

        //Array Adapter

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1 , data);
        listView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        builder.setView(listView);
        final AlertDialog alertDialog = builder.create();

        alertDialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                if (position == 0){

                    final StorageReference photoRef =
                            FirebaseStorage.getInstance()
                                    .getReferenceFromUrl(model_sell.getUrlPhotoItem());

                    final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

                    photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            final DatabaseReference refHapus = FirebaseDatabase.getInstance().getReference();
                            Query deletePost = refHapus.child("itemSell").orderByChild("idUpload").equalTo(model_sell.getIdUpload());
                            final Query deleteFavorite = refHapus.child("userItemFavorite").orderByChild("idUpload").equalTo(model_sell.getIdUpload());

                            deletePost.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dsDelete : dataSnapshot.getChildren())
                                    {
                                        deleteFavorite.getRef().removeValue();
                                        dsDelete.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(mContext, "Berhasil Menghapus Post", Toast.LENGTH_SHORT).show();
                                                alertDialog.dismiss();
                                                removeItem(positionData);

                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }
                    });

                }

            }
        });


    }

    private void removeItem(int position) {
        itemSellModels.remove(position);
        notifyItemRemoved(position);
    }
}
