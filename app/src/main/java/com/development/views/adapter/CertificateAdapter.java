package com.development.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.views.model.CertificateClass;

import java.util.List;

public class CertificateAdapter extends RecyclerView.Adapter<CertificateAdapter.MyViewHolder> {

    private List<CertificateClass> documentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public CertificateAdapter(Context context,List<CertificateClass> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @Override
    public CertificateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_certificate_row, parent, false);

        return new CertificateAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CertificateAdapter.MyViewHolder holder, int position) {
        CertificateClass document = documentList.get(position);
        holder.name.setText(document.getName());

    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }
}
