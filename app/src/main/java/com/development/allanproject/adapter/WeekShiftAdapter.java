package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.WeekShiftClass;

import java.util.List;

public class WeekShiftAdapter extends  RecyclerView.Adapter<WeekShiftAdapter.MyViewHolder> {

    private List<WeekShiftClass> weekList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public WeekShiftAdapter(Context context,List<WeekShiftClass> weekList) {
        this.context = context;
        this.weekList = weekList;
    }

    @Override
    public WeekShiftAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.week_shift_item, parent, false);

        return new WeekShiftAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeekShiftAdapter.MyViewHolder holder, int position) {
        WeekShiftClass listData = weekList.get(position);
        holder.name.setText(listData.getName());

    }

    @Override
    public int getItemCount() {
        return weekList.size();
    }
}
