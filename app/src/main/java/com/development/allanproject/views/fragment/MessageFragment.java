package com.development.allanproject.views.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.development.allanproject.R;
import com.development.allanproject.databinding.FragmentMessageBinding;
import com.development.allanproject.adapter.NotificationAdapter;
import com.development.allanproject.model.NotificationClass;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
    FragmentMessageBinding messageBinding;
    NotificationAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        messageBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_message, container, false);
        return messageBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView();
    }

    private void setRecyclerView() {

        List<NotificationClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            NotificationClass reference = new  NotificationClass();
            reference.setName("Job “Apollo” you applied for the job ");
            dataList.add(reference);
        }
//        adapter = new NotificationAdapter(getContext(), dataList);
//        mLayoutManager = new LinearLayoutManager(getContext());
//        messageBinding.recyclerView.setLayoutManager(mLayoutManager);
//        messageBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
//        messageBinding.recyclerView.setAdapter(adapter);
    }
}
