package com.oblivion.tokoonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.model.ItemSell_model;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(@NonNull ItemMysellVH holder, int position) {
        ItemSell_model model = itemSellModels.get(position);

        Picasso.get().load(model.getUrlPhotoItem()).fit().centerCrop().into(holder.itemAccount);
        holder.headerAccount.setText(model.getHeaderAds());
        holder.priceAccount.setText(model.getPrice());

    }

    @Override
    public int getItemCount() {
        return itemSellModels.size();
    }

    public class ItemMysellVH extends RecyclerView.ViewHolder {

        private ImageView itemAccount;
        private TextView headerAccount, priceAccount;

        public ItemMysellVH(@NonNull View itemView) {
            super(itemView);

            itemAccount = itemView.findViewById(R.id.image_item_account);
            headerAccount = itemView.findViewById(R.id.header_ads_account);
            priceAccount = itemView.findViewById(R.id.price_item_account);
        }
    }
}
