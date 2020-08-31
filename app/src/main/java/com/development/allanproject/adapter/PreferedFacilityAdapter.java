package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.PreferredFacilityClass;

import java.util.List;

public class PreferedFacilityAdapter extends RecyclerView.Adapter<PreferedFacilityAdapter.MyViewHolder> {

    private List<PreferredFacilityClass> preferredList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public PreferedFacilityAdapter(Context context,List<PreferredFacilityClass> preferredList) {
        this.context = context;
        this.preferredList = preferredList;
    }

    @Override
    public PreferedFacilityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.preferred_facility_row, parent, false);

        return new PreferedFacilityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PreferedFacilityAdapter.MyViewHolder holder, int position) {
        PreferredFacilityClass document = preferredList.get(position);
        holder.name.setText(document.getName());

    }

    @Override
    public int getItemCount() {
        return preferredList.size();
    }
}
