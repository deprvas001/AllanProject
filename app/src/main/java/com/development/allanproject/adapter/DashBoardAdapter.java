package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.DashBoardClass;

import java.util.List;

public class DashBoardAdapter  extends RecyclerView.Adapter<DashBoardAdapter.MyViewHolder> {

    private List<DashBoardClass> dataList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,value;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            value = (TextView)view.findViewById(R.id.value);

        }
    }


    public DashBoardAdapter(Context context,List<DashBoardClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public DashBoardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_row, parent, false);

        return new DashBoardAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DashBoardAdapter.MyViewHolder holder, int position) {
        DashBoardClass document = dataList.get(position);
        holder.name.setText(document.getName());
        holder.value.setText(document.getValue());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
