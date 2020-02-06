package com.oblivion.tokoonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.oblivion.tokoonline.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {


        switch (position) {
            case 0:
                Picasso.get()
                        .load("https://firebasestorage.googleapis.com/v0/b/info-razia.appspot.com/o/LaporRaziaImage%2F1578798716798.jpg?alt=media&token=07500c1e-48b2-4938-8a3f-7a7cef696b2b")
                        .fit()
                        .centerCrop()
                        .into(viewHolder.imageViewBackground);
                break;
            case 1:
                Picasso.get()
                        .load("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260")
                        .fit()
                        .centerCrop()
                        .into(viewHolder.imageViewBackground);
                break;
            case 2:
                Picasso.get()
                        .load("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
                        .fit()
                        .centerCrop()
                        .into(viewHolder.imageViewBackground);
                break;


        }

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return 3;
    }

    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View viewItem;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            viewItem = itemView;
            imageViewBackground = viewItem.findViewById(R.id.iv_auto_image_slider);


        }
    }
}
