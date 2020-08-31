package com.development.allanproject.views.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.development.allanproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyShiftOver extends Fragment {

    public MyShiftOver() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_shift_over, container, false);
    }
}
