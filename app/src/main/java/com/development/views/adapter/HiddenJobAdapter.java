package com.development.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.views.model.HiddenJobClass;

import java.util.List;

public class HiddenJobAdapter extends RecyclerView.Adapter<HiddenJobAdapter.MyViewHolder> {

    private List<HiddenJobClass> documentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public HiddenJobAdapter(Context context,List<HiddenJobClass> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @Override
    public HiddenJobAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hidden_job_riow, parent, false);

        return new HiddenJobAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HiddenJobAdapter.MyViewHolder holder, int position) {
        HiddenJobClass document = documentList.get(position);
        holder.name.setText(document.getName());

    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }
}
