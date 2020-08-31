package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.FacilityTypeModel;

import java.util.List;

public class FacilityTypeAdapter extends RecyclerView.Adapter<FacilityTypeAdapter.MyViewHolder> {

    private List<FacilityTypeModel> facilityList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public FacilityTypeAdapter(Context context,List<FacilityTypeModel> facilityList) {
        this.context = context;
        this.facilityList = facilityList;
    }

    @Override
    public FacilityTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facility_type_row, parent, false);

        return new FacilityTypeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FacilityTypeAdapter.MyViewHolder holder, int position) {
        FacilityTypeModel document = facilityList.get(position);
        holder.name.setText(document.getName());

    }

    @Override
    public int getItemCount() {
        return facilityList.size();
    }
}
