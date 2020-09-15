package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.appointmentModel.AppointmentDetail;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDateAdapter extends RecyclerView.Adapter<AppointmentDateAdapter.MyViewHolder> {
    private ArrayList<AppointmentDetail> dateList;
    private Context context;

    private int row_index=-1;
    public ImageView thumbnail;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date,month,day;
        public RelativeLayout viewBackground, viewForeground;
        public CardView cardView;
        public MyViewHolder(View view) {
            super(view);
            date = (TextView)view.findViewById(R.id.date);
            month = (TextView)view.findViewById(R.id.month);
            day = (TextView)view.findViewById(R.id.week_day);
            cardView = (CardView)view.findViewById(R.id.cardView);

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
       String appoint_date = appointmentDetail.getDate();
       try {
           String[] parts = appoint_date.split("-");
           holder.date.setText(parts[0]);
           holder.month.setText(parts[1]);
           holder.day.setText(parts[2]);
       }catch (Exception e){
           e.printStackTrace();
       }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                row_index=position;
                notifyDataSetChanged();
            }
        });


        if(row_index == position){
            holder.date.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }else{
            holder.date.setTextColor(context.getResources().getColor(R.color.time_color));
        }


    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }
    
}
