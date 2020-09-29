package com.development.allanproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.editProfile.EditData;
import com.development.allanproject.views.activity.ui.addexperience.workExperienceList.WorkExperienceList;
import com.development.allanproject.views.activity.ui.awards.AwardScreen;
import com.development.allanproject.views.activity.ui.backgroundinformation.BackgroundInformationScreen;
import com.development.allanproject.views.activity.ui.editPersonalInfo.EditPersonalInfo;
import com.development.allanproject.views.activity.ui.education.EducationList;
import com.development.allanproject.views.activity.ui.language.LanguageScreen;
import com.development.allanproject.views.activity.ui.professionalDetails.ProfessionalDetails;
import com.development.allanproject.views.activity.ui.research.ResearchScreen;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.util.List;

public class EditProfileAdapter extends RecyclerView.Adapter<EditProfileAdapter.MyViewHolder>  {
    private List<EditData> profileList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,complete,edit;
        public ImageView delete;
        public ProgressBar progressBar;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            complete = (TextView)view.findViewById(R.id.complete);
            edit = (TextView)view.findViewById(R.id.edit) ;

            progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);

        }
    }


    public EditProfileAdapter(Context context,List<EditData> profileList) {
        this.context = context;
        this.profileList = profileList;
    }

    @Override
    public EditProfileAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.edit_profile_row, parent, false);

        return new EditProfileAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EditProfileAdapter.MyViewHolder holder, int position) {
        EditData editData = profileList.get(position);

        holder.name.setText(editData.getName());
        holder.complete.setText(Math.round(editData.getPercentage()*100)+"% Completed");
        holder.progressBar.setProgressTintList(ColorStateList.valueOf((Color.parseColor(editData.getColor_code()))));
        holder.progressBar.setProgress(Math.round(editData.getPercentage()*100));

//        holder.complete.setText(editData.getPercentage());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editData.getStep_no().equals("31")){
                    context.startActivity(new Intent(context, ResearchScreen.class));
                }else if(editData.getStep_no().equals("30")){
                    context.startActivity(new Intent(context, AwardScreen.class));
                }else if(editData.getStep_no().equals("29")){
                    context.startActivity(new Intent(context, LanguageScreen.class));
                }else if(editData.getStep_no().equals("28")){
                    context.startActivity(new Intent(context, BackgroundInformationScreen.class));
                }
                else if(editData.getStep_no().equals("15")){
                    context.startActivity(new Intent(context, ProfessionalDetails.class));
                } else if(editData.getStep_no().equals("14")){
                    context.startActivity(new Intent(context, EducationList.class));
                } else if(editData.getStep_no().equals("13")){

                }
                else if(editData.getStep_no().equals("7")){
                    context.startActivity(new Intent(context, WorkExperienceList.class));
                }else if(editData.getStep_no().equals("2")){
                    context.startActivity(new Intent(context, EditPersonalInfo.class));
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }
}
