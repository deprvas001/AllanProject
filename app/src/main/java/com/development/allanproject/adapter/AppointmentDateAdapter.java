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
import java.util.List;

public class AppointmentDateAdapter extends RecyclerView.Adapter<AppointmentDateAdapter.MyViewHolder> {
    private ArrayList<AppointmentDetail> dateList;
    private Context context;
    public ImageView thumbnail;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date,month,year;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView)view.findViewById(R.id.date);
            month = (TextView)view.findViewById(R.id.month);
            year = (TextView)view.findViewById(R.id.year);

        }
    }


    public AppointmentDateAdapter(Context context,ArrayList<AppointmentDetail> dateList) {
        this.context = context;
        this.dateList = dateList;
    }

    @Override
    public AppointmentDateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.date_layout_row, parent, false);

        return new AppointmentDateAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AppointmentDateAdapter.MyViewHolder holder, int position) {
       AppointmentDetail  appointmentDetail = dateList.get(position);
        holder.date.setText(appointmentDetail.getDate());
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }
    
}
