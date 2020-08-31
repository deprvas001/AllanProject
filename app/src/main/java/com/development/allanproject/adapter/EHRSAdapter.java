package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.EHRSClass;

import java.util.List;

public class EHRSAdapter extends RecyclerView.Adapter<EHRSAdapter.MyViewHolder> {
    private List<EHRSClass> documentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public EHRSAdapter(Context context,List<EHRSClass> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @Override
    public EHRSAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_certificate_row, parent, false);

        return new EHRSAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EHRSAdapter.MyViewHolder holder, int position) {
        EHRSClass document = documentList.get(position);
        holder.name.setText(document.getName());

    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }
}
