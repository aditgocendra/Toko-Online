package com.oblivion.tokoonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.model.ItemSell_model;
import com.oblivion.tokoonline.model.User_model;
import com.oblivion.tokoonline.view.ItemViewDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecycleItemAdapter extends RecyclerView.Adapter<RecycleItemAdapter.ItemViewHolderVH> {


    private Context mContext;
    private List<ItemSell_model> itemSellModels;


    public RecycleItemAdapter(Context mContext, List<ItemSell_model> models){

        this.mContext = mContext;
        this.itemSellModels = models;



    }

    @NonNull
    @Override
    public ItemViewHolderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_show, parent, false);
        return new ItemViewHolderVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolderVH holder, int position) {
        final ItemSell_model sellModel = itemSellModels.get(position);



        Picasso.get().load(sellModel.getUrlPhotoItem()).fit().centerCrop().into(holder.imageItem);
        holder.textHeader.setText(sellModel.getHeaderAds());
        holder.textPrice.setText(sellModel.getPrice());

        holder.layoutDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(mContext, ItemViewDetail.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("idItem", sellModel.getIdUpload());
                intent.putExtra("urlPhotoPost", sellModel.getUrlPhotoItem());
                intent.putExtra("headerAds", sellModel.getHeaderAds());
                intent.putExtra("priceItem", sellModel.getPrice());
                intent.putExtra("condition", sellModel.getCondition());
                intent.putExtra("city", sellModel.getCity());
                intent.putExtra("address", sellModel.getAddress());
                intent.putExtra("date", sellModel.getTanggal());
                intent.putExtra("description", sellModel.getDescription());
                intent.putExtra("urlPhotoUser", sellModel.getUrlPhotoUser());
                intent.putExtra("nameUserPost", sellModel.getNamaUser());
                intent.putExtra("numberPhone", sellModel.getNumber());
                intent.putExtra("subCategory", sellModel.getCategory());
                intent.putExtra("activity", "viewCategory");

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemSellModels.size();
    }

    public class ItemViewHolderVH extends RecyclerView.ViewHolder {

        private ImageView imageItem;
        private TextView textHeader;
        private TextView textPrice;
        private LinearLayout layoutDetail;


        public ItemViewHolderVH(@NonNull View itemView) {
            super(itemView);

           imageItem = itemView.findViewById(R.id.image_item_show);
           textHeader = itemView.findViewById(R.id.header_ads_show);
           textPrice = itemView.findViewById(R.id.price_item_show);
           layoutDetail = itemView.findViewById(R.id.click_detail_item);

        }
    }
}
