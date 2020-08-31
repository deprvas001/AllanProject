package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.HiddenJobClass;

import java.util.List;

public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.MyViewHolder> {

    private List<HiddenJobClass> documentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public ShiftAdapter(Context context,List<HiddenJobClass> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @Override
    public ShiftAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.today_shift_row, parent, false);

        return new ShiftAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShiftAdapter.MyViewHolder holder, int position) {
        HiddenJobClass document = documentList.get(position);
        holder.name.setText(document.getName());

    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }
}
