package com.development.allanproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.EditWorkExpClass;
import com.development.allanproject.model.commonapi.FacilityType;
import com.development.allanproject.model.experience.Data;
import com.development.allanproject.views.activity.ui.addexperience.editExperience.EditExperience;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EditWorkExpAdapter extends RecyclerView.Adapter<EditWorkExpAdapter.MyViewHolder> {
    ExperienceCallBack experienceCallBack;

    public interface ExperienceCallBack{
        void listenerMethod(Data data);
    }

    private ArrayList<Data> experienceList;
    private ArrayList<FacilityType> facilityList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView status, facility_type,facility_name,exp;
        public ImageView delete,edit;
        public MyViewHolder(View view) {
            super(view);
            status = (TextView)view.findViewById(R.id.status);
            facility_type = (TextView)view.findViewById(R.id.facility_type);
            facility_name = (TextView)view.findViewById(R.id.facility_name);
            exp = (TextView)view.findViewById(R.id.total_exp);
            delete = (ImageView) view.findViewById(R.id.delete);
            edit = (ImageView)view.findViewById(R.id.edit);

        }
    }


    public EditWorkExpAdapter(Context context,ArrayList<Data> experienceList,
                              ArrayList<FacilityType> facilityList, ExperienceCallBack experienceCallBack) {
        this.context = context;
        this.experienceList = experienceList;
        this.facilityList = facilityList;
        this.experienceCallBack = experienceCallBack;
    }

    @Override
    public EditWorkExpAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.edit_work_exp_row, parent, false);

        return new EditWorkExpAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EditWorkExpAdapter.MyViewHolder holder, int position) {
        Data listData = experienceList.get(position);
        if(facilityList.size()>0){
            for(FacilityType facility: facilityList){
             if(facility.getId() == listData.getFacility_id()){
                 holder.facility_type.setText("Facility Type: "+facility.getName());
             }
            }
        }
        holder.status.setText("Status: " +listData.getVerified_status());

        holder.facility_name.setText("Facility Name: "+listData.getFacility_name());
        if(listData.getEnd_date()!=null){
            String date = calculateExperience(listData.getEnd_date(),listData.getStart_date());
            holder.exp.setText("Total Exp.: "+date);
        }else if (listData.getStart_date()!=null){
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(date);
            String experience = calculateExperience(strDate,listData.getStart_date());
            holder.exp.setText("Total Exp.: "+experience);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                experienceCallBack.listenerMethod(listData);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditExperience.class);
                intent.putExtra("work_exp", listData);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return experienceList.size();
    }

    private String calculateExperience(String enddate, String startdate){
        String total_experience="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date end_date = sdf.parse(enddate);
            Date start_date = sdf.parse(startdate);

            long difference_In_Time = end_date.getTime() - start_date.getTime();

            long difference_In_Years
                    = (difference_In_Time
                    / (1000l * 60 * 60 * 24 * 365));

            long difference_In_Days
                    = (difference_In_Time
                    / (1000 * 60 * 60 * 24))
                    % 365;

            total_experience= String.valueOf((difference_In_Years)+" Year " + difference_In_Days+" Days" );


        } catch (ParseException e) {
            e.printStackTrace();
        }
         return total_experience;
    }
}
