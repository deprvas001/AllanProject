package com.development.views.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.development.allanproject.R;
import com.development.allanproject.databinding.FragmentFormsBinding;
import com.development.views.adapter.FormsAdapter;
import com.development.views.adapter.MyDocumentAdapter;
import com.development.views.model.FormsClass;
import com.development.views.model.MyDocumentClass;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormsFragment extends Fragment {
    FormsAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    FragmentFormsBinding formsBinding;
    public FormsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        formsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_forms, container, false);
        setRecyclerView();
        return formsBinding.getRoot();
    }

    private void setRecyclerView() {

        List<FormsClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            FormsClass document = new FormsClass();
            document.setName("Forms Name");
            dataList.add(document);
        }
        adapter = new FormsAdapter(getActivity(), dataList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        formsBinding.recyclerView.setLayoutManager(mLayoutManager);
        formsBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        formsBinding.recyclerView.setAdapter(adapter);
    }
}
