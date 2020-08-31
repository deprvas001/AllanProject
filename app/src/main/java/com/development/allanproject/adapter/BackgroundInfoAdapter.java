package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.BackgroundInformationClass;

import java.util.List;

public class BackgroundInfoAdapter extends  RecyclerView.Adapter<BackgroundInfoAdapter.MyViewHolder> {
    private List<BackgroundInformationClass> infoList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public BackgroundInfoAdapter(Context context,List<BackgroundInformationClass> infoList) {
        this.context = context;
        this.infoList = infoList;
    }

    @Override
    public BackgroundInfoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.background_information_row, parent, false);

        return new BackgroundInfoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BackgroundInfoAdapter.MyViewHolder holder, int position) {
        BackgroundInformationClass document = infoList.get(position);
        holder.name.setText(document.getName());

    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }
}
