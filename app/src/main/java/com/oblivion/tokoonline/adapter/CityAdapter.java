package com.oblivion.tokoonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.model.City_model;
import com.oblivion.tokoonline.view.MainActivity;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private Context mContext;
    private List<City_model> city_models;


    public CityAdapter(Context context, List<City_model> city_models){

        this.mContext = context;
        this.city_models = city_models;

    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_city, parent, false);
        return new CityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        final City_model model = city_models.get(position);


        holder.cityLocation.setText(model.getValue());
        holder.cityPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference
                        .child(mUser.getUid())
                        .child("location")
                        .setValue(model.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });


    }

    @Override
    public int getItemCount() {
        return city_models.size();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {

        private TextView cityLocation;
        private LinearLayout cityPick;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);

            cityLocation = itemView.findViewById(R.id.city_loc);
            cityPick = itemView.findViewById(R.id.pick_city);

        }
    }
}
