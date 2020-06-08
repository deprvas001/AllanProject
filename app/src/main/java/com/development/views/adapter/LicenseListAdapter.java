package com.development.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.views.model.LicenseListClass;

import java.util.List;

public class LicenseListAdapter extends RecyclerView.Adapter<LicenseListAdapter.MyViewHolder> {
    private List<LicenseListClass> licenseList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public LicenseListAdapter(Context context,List<LicenseListClass> licenseList) {
        this.context = context;
        this.licenseList = licenseList;
    }

    @Override
    public LicenseListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.license_list_row, parent, false);

        return new LicenseListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LicenseListAdapter.MyViewHolder holder, int position) {
        LicenseListClass listData = licenseList.get(position);
        holder.name.setText(listData.getName());

    }

    @Override
    public int getItemCount() {
        return licenseList.size();
    }

}
