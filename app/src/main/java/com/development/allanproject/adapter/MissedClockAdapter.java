package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.MissedClockClass;

import java.util.List;

public class MissedClockAdapter extends RecyclerView.Adapter<MissedClockAdapter.MyViewHolder> {

    private List<MissedClockClass> missedList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public MissedClockAdapter(Context context,List<MissedClockClass> missedList) {
        this.context = context;
        this.missedList = missedList;
    }

    @Override
    public MissedClockAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.missed_clock_item, parent, false);

        return new MissedClockAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MissedClockAdapter.MyViewHolder holder, int position) {
        MissedClockClass listData = missedList.get(position);
        holder.name.setText(listData.getName());

    }

    @Override
    public int getItemCount() {
        return missedList.size();
    }
}
