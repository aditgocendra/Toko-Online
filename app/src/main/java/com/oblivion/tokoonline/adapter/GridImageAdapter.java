package com.oblivion.tokoonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.Utils.SqaureImageView;

import java.util.ArrayList;

import static com.oblivion.tokoonline.Utils.UniversalImageLoader.setImage;

public class GridImageAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResource;
    private String mAppend;
    private ArrayList<String> imgURLs;

    public GridImageAdapter(Context context, int layoutResource, String append, ArrayList<String> imgURLs) {
        super(context, layoutResource, imgURLs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        this.layoutResource = layoutResource;
        mAppend = append;
        this.imgURLs = imgURLs;

    }

    private static class ViewHolder{
        SqaureImageView image;
        ProgressBar mProgressBar;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        /*
        Viewholder build pattern (Similar to recyclerview)
         */
        final ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();
            holder.mProgressBar =  convertView.findViewById(R.id.gridImageProgressbar);
            holder.image =  convertView.findViewById(R.id.gridImageView);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        final String imgURL = getItem(position);


        setImage(imgURL, holder.image, holder.mProgressBar, mAppend);


        return convertView;
    }



}
