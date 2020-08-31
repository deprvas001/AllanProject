package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.CertificateClass;

import java.util.List;

public class CertificateAdapter extends RecyclerView.Adapter<CertificateAdapter.MyViewHolder> {

    private List<CertificateClass> documentList;
    private Context context;
    public ImageView thumbnail;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,experience;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            experience = (TextView)view.findViewById(R.id.experience);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);

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
        holder.experience.setText(document.getExperience());
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public void removeItem(int position) {
        documentList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(CertificateClass item, int position) {
        documentList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
