package com.oblivion.tokoonline.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.oblivion.tokoonline.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoConnectionFragment extends Fragment {


    public NoConnectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_no_connection, container, false);

//        Button close_app = view.findViewById(R.id.close_app_btn);
//
//        close_app.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Objects.requireNonNull(getActivity()).finish();
//            }
//        });

        return view;
    }

}
