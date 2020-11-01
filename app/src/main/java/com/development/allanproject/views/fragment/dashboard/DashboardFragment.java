package com.development.allanproject.views.fragment.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.databinding.FragmentDashboardBinding;
import com.development.allanproject.adapter.DashBoardAdapter;
import com.development.allanproject.model.DashBoardClass;
import com.development.allanproject.util.dashboardListener.DashboardListener;
import com.development.allanproject.views.activity.ui.dashboard.DashboardViewModel;

import org.kodein.di.KodeinAware;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment  {
    FragmentDashboardBinding dashboardBinding;
    private DashboardViewModel dashboardViewModel;
    DashBoardAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);

        return dashboardBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView();

    }

    private void setRecyclerView() {

        List<DashBoardClass> dataList = new ArrayList<>();


        DashBoardClass language = new DashBoardClass();
        language.setName("Shifts Taken");
        language.setValue("5");
        dataList.add(language);

        language = new DashBoardClass();
        language.setName("Hours Worked");
        language.setValue("200 hr.");
        dataList.add(language);

        language = new DashBoardClass();
        language.setName("Earnings");
        language.setValue("$300");
        dataList.add(language);

        language = new DashBoardClass();
        language.setName("Last week’s earnings");
        language.setValue("$400");
        dataList.add(language);



        adapter = new DashBoardAdapter(getActivity(), dataList);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        dashboardBinding.recyclerViewLifeTime.setLayoutManager(mLayoutManager);
        dashboardBinding.recyclerViewLifeTime.setItemAnimator(new DefaultItemAnimator());
        dashboardBinding.recyclerViewLifeTime.setAdapter(adapter);
    }
}
