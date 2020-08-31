package com.development.allanproject.views.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.development.allanproject.R;
import com.development.allanproject.databinding.FragmentMyShiftBinding;
import com.development.allanproject.views.activity.ViewMyShift;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyShift extends Fragment implements View.OnClickListener {
FragmentMyShiftBinding shiftBinding;
    public MyShift() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        shiftBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_shift, container, false);
        return shiftBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClickListener();
    }

    private void setClickListener(){
        shiftBinding.cardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardView:
                  startActivity(new Intent(getContext(),ViewMyShift.class));
                break;
        }
    }
}
