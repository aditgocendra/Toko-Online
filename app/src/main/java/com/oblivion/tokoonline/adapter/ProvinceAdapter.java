package com.oblivion.tokoonline.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.fragment.CityFragment;
import com.oblivion.tokoonline.model.Province_model;

import java.util.List;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ProvinceViewHolder> {

    private Context mContext;
    private List<Province_model> province_model;

    public ProvinceAdapter(Context mContext, List<Province_model> province_model){

    this.mContext = mContext;
    this.province_model = province_model;

    }

    @NonNull
    @Override
    public ProvinceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_province, parent, false);
        return new ProvinceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinceViewHolder holder, int position) {
        final Province_model model = province_model.get(position);

        holder.province_loc.setText(model.getProvinsi());
        holder.openCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CityFragment fragment = new CityFragment();
                Bundle bundle = new Bundle();
                bundle.putString("provinsi", model.getProvinsi());
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();

            }
        });



    }

    @Override
    public int getItemCount() {
        return province_model.size();
    }

    public class ProvinceViewHolder extends RecyclerView.ViewHolder {

        private TextView province_loc;
        private LinearLayout openCity;

        public ProvinceViewHolder(@NonNull View itemView) {
            super(itemView);

            province_loc = itemView.findViewById(R.id.province_location);
            openCity = itemView.findViewById(R.id.cityOpen);
        }
    }
}
