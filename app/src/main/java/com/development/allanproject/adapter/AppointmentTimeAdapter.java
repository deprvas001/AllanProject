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
import com.development.allanproject.model.appointmentModel.AppointmentDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppointmentTimeAdapter extends RecyclerView.Adapter<AppointmentTimeAdapter.MyViewHolder> {
    private ArrayList<HashMap<String,String>> dateList;
    private Context context;
    private int row_index=-1;
    public ImageView thumbnail;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date,month,year;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView)view.findViewById(R.id.time);
        }
    }


    public AppointmentTimeAdapter(Context context,ArrayList<HashMap<String,String>> dateList) {
        this.context = context;
        this.dateList = dateList;
    }

    @Override
    public AppointmentTimeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_slot_row, parent, false);

        return new AppointmentTimeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AppointmentTimeAdapter.MyViewHolder holder, int position) {
        HashMap<String,String> time_slot = dateList.get(position);
        for (Map.Entry mapElement : time_slot.entrySet()){
            holder.date.setText(mapElement.getValue().toString());
        }
        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                row_index=position;
                notifyDataSetChanged();
            }
        });

        if(row_index == position){
            holder.date.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        }else{
            holder.date.setTextColor(context.getResources().getColor(R.color.time_color));
        }


        /*holder.date.setText(appointmentDetail.getDate());*/
       // holder.date.setText(time_slot.);
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }
}
