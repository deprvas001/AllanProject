package com.development.allanproject.views.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.development.allanproject.R;
import com.development.allanproject.databinding.FragmentTodayShfitBinding;
import com.development.allanproject.adapter.ShiftAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayShfitFragment extends Fragment {
    ShiftAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    FragmentTodayShfitBinding shfitBinding;
    public TodayShfitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        shfitBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_today_shfit, container, false);
      //  setRecyclerView();
        showCustomDialog();
        return shfitBinding.getRoot();
    }

   /* private void setRecyclerView() {

        List<HiddenJobClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            HiddenJobClass document = new HiddenJobClass();
            document.setName("Facility Name");
            dataList.add(document);
        }
        adapter = new ShiftAdapter(getActivity(), dataList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        shfitBinding.recyclerView.setLayoutManager(mLayoutManager);
        shfitBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        shfitBinding.recyclerView.setAdapter(adapter);
    }*/

    private void showCustomDialog(){
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.clock_in_pop_second,null);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
