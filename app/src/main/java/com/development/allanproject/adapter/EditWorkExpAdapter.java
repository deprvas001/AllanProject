package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.EditWorkExpClass;

import java.util.List;

public class EditWorkExpAdapter extends RecyclerView.Adapter<EditWorkExpAdapter.MyViewHolder> {

    private List<EditWorkExpClass> experienceList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public EditWorkExpAdapter(Context context,List<EditWorkExpClass> experienceList) {
        this.context = context;
        this.experienceList = experienceList;
    }

    @Override
    public EditWorkExpAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.edit_work_exp_row, parent, false);

        return new EditWorkExpAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EditWorkExpAdapter.MyViewHolder holder, int position) {
        EditWorkExpClass listData = experienceList.get(position);
        holder.name.setText(listData.getName());

    }

    @Override
    public int getItemCount() {
        return experienceList.size();
    }
}
