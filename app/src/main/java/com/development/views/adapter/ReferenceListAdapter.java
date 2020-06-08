package com.development.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.views.model.ReferenceListClass;

import java.util.List;

public class ReferenceListAdapter extends RecyclerView.Adapter<ReferenceListAdapter.MyViewHolder> {
    private List<ReferenceListClass> referenceList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public ReferenceListAdapter(Context context,List<ReferenceListClass> referenceList) {
        this.context = context;
        this.referenceList = referenceList;
    }

    @Override
    public ReferenceListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reference_list_row, parent, false);

        return new ReferenceListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReferenceListAdapter.MyViewHolder holder, int position) {
        ReferenceListClass listData = referenceList.get(position);
        holder.name.setText(listData.getName());

    }

    @Override
    public int getItemCount() {
        return referenceList.size();
    }
}
