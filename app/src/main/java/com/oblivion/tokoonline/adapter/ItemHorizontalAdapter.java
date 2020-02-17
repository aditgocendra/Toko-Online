package com.oblivion.tokoonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.Utils.NumberFormat;
import com.oblivion.tokoonline.model.ItemSell_model;
import com.oblivion.tokoonline.view.ItemViewDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemHorizontalAdapter extends RecyclerView.Adapter<ItemHorizontalAdapter.ItemHorizontalVH> {

    private Context mContext;
    private List<ItemSell_model> models;

    public ItemHorizontalAdapter(Context mContext, List<ItemSell_model> models){

        this.mContext = mContext;
        this.models = models;


    }



    @NonNull
    @Override
    public ItemHorizontalAdapter.ItemHorizontalVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizontal_scroll, parent, false);
        return new ItemHorizontalVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHorizontalAdapter.ItemHorizontalVH holder, int position) {
        final ItemSell_model sell_model = models.get(position);


        Picasso.get().load(sell_model.getUrlPhotoItem()).fit().centerCrop().into(holder.viewItemphoto);


        NumberFormat format = new NumberFormat();

        holder.headerAds.setText(sell_model.getHeaderAds());
        holder.priceItem.setText(format.currencyFormatter(sell_model.getPrice()));

        holder.item_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(mContext, ItemViewDetail.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("idItem", sell_model.getIdUpload());
                intent.putExtra("urlPhotoPost", sell_model.getUrlPhotoItem());
                intent.putExtra("headerAds", sell_model.getHeaderAds());
                intent.putExtra("priceItem", sell_model.getPrice());
                intent.putExtra("condition", sell_model.getCondition());
                intent.putExtra("city", sell_model.getCity());
                intent.putExtra("address", sell_model.getAddress());
                intent.putExtra("date", sell_model.getTanggal());
                intent.putExtra("description", sell_model.getDescription());
                intent.putExtra("urlPhotoUser", sell_model.getUrlPhotoUser());
                intent.putExtra("nameUserPost", sell_model.getNamaUser());
                intent.putExtra("numberPhone", sell_model.getNumber());
                intent.putExtra("subCategory", sell_model.getCategory());
                intent.putExtra("activity", "home");

                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ItemHorizontalVH extends RecyclerView.ViewHolder {

        private ImageView viewItemphoto;
        private TextView headerAds, priceItem;
        private LinearLayout item_click;

        public ItemHorizontalVH(@NonNull View itemView) {
            super(itemView);

            viewItemphoto = itemView.findViewById(R.id.image_item);
            headerAds = itemView.findViewById(R.id.header_ads_view);
            priceItem = itemView.findViewById(R.id.price_item_view);
            item_click = itemView.findViewById(R.id.click_detail_item);

        }
    }

}
