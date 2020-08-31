package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.FormsClass;

import java.util.List;

public class FormsAdapter extends RecyclerView.Adapter<FormsAdapter.MyViewHolder> {
    private List<FormsClass> documentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public FormsAdapter(Context context,List<FormsClass> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @Override
    public FormsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forms_row, parent, false);

        return new FormsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FormsAdapter.MyViewHolder holder, int position) {
        FormsClass listData = documentList.get(position);
        holder.name.setText(listData.getName());

    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }
}
