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
import com.development.allanproject.databinding.FragmentCalendarBinding;
import com.development.allanproject.views.activity.AddAvailability;
import com.development.allanproject.views.activity.AddMyEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment implements View.OnClickListener {
    FragmentCalendarBinding calendarBinding;
    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        calendarBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_calendar, container, false);
        return calendarBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClickListener();
    }

    private void setClickListener(){
        calendarBinding.btnEvent.setOnClickListener(this);
        calendarBinding.btnAvailability.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_event:
                startActivity(new Intent(getActivity(), AddMyEvent.class));
                break;

            case R.id.btn_availability:
                startActivity(new Intent(getActivity(), AddAvailability.class));
                break;
        }
    }
}
