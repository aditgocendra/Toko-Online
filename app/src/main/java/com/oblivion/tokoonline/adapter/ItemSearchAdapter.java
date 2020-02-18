package com.oblivion.tokoonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.model.ItemSell_model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemSearchAdapter extends RecyclerView.Adapter<ItemSearchAdapter.ItemSearchVH> implements Filterable {


    private Context mContext;
    private List<ItemSell_model> sell_modelList;
    private List<ItemSell_model> mUploadSearch;

    public ItemSearchAdapter(Context mContext){

        this.mContext = mContext;
        this.sell_modelList = new ArrayList<>();
        this.mUploadSearch = new ArrayList<>();

    }

    public void addAll(List<ItemSell_model> newData)
    {
        int initSize = sell_modelList.size();
        sell_modelList.addAll(newData);
        notifyItemRangeChanged(initSize, newData.size());
        mUploadSearch.addAll(newData);
    }

    @NonNull
    @Override
    public ItemSearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_layout, parent, false);
        return new ItemSearchVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSearchVH holder, int position) {
        ItemSell_model model = sell_modelList.get(position);


        Picasso.get().load(model.getUrlPhotoItem()).fit().centerCrop().into(holder.itemViewSearch);
        holder.textViewSearch.setText(model.getHeaderAds());


    }

    @Override
    public int getItemCount() {
        return sell_modelList.size();
    }

    @Override
    public Filter getFilter() {
        return mUploadFilter;
    }

    private Filter mUploadFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<ItemSell_model> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(mUploadSearch);

            }else {
                String filterPatern = constraint.toString().toLowerCase().trim();

                for (ItemSell_model sell_model : mUploadSearch)
                {
                    if (sell_model.getHeaderAds().toLowerCase().contains(filterPatern))
                    {
                        filteredList.add(sell_model);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            sell_modelList.clear();
            sell_modelList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



    public class ItemSearchVH extends RecyclerView.ViewHolder {

        private ImageView itemViewSearch;
        private TextView textViewSearch;
        public ItemSearchVH(@NonNull View itemView) {
            super(itemView);

            itemViewSearch = itemView.findViewById(R.id.item_by_search);
            textViewSearch = itemView.findViewById(R.id.header_by_search);
        }
    }




}
