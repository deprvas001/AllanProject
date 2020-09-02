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
import com.development.allanproject.model.license.LicenseData;

import java.util.List;

public class AddLicenseAdapter extends RecyclerView.Adapter<AddLicenseAdapter.MyViewHolder> {

    private List<LicenseData> documentList;
    private Context context;
    public ImageView thumbnail;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,state,compat;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            state = (TextView)view.findViewById(R.id.state);
            compat = (TextView)view.findViewById(R.id.compat);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);

        }
    }


    public AddLicenseAdapter(Context context,List<LicenseData> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @Override
    public AddLicenseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_license_row, parent, false);

        return new AddLicenseAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddLicenseAdapter.MyViewHolder holder, int position) {
        LicenseData document = documentList.get(position);
        holder.name.setText(document.getLicense());
        holder.state.setText(document.getState());
        holder.compat.setText(document.getCompat());
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

    public void restoreItem(LicenseData item, int position) {
        documentList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
