package com.development.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.views.model.HealthDocumentClass;

import java.util.List;

public class HealthDocumentAdpater extends RecyclerView.Adapter<HealthDocumentAdpater.MyViewHolder> {
    private List<HealthDocumentClass> documentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public HealthDocumentAdpater(Context context,List<HealthDocumentClass> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @Override
    public HealthDocumentAdpater.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.health_document_row, parent, false);

        return new HealthDocumentAdpater.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HealthDocumentAdpater.MyViewHolder holder, int position) {
        HealthDocumentClass document = documentList.get(position);
        holder.name.setText(document.getName());

    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }
}
