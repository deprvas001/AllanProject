package com.development.allanproject.adapter;

import android.content.Context;
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
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.util.List;

public class EditProfileAdapter extends RecyclerView.Adapter<EditProfileAdapter.MyViewHolder>  {
    private List<EditData> profileList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,complete;
        public ImageView delete;
        public ProgressBar progressBar;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            complete = (TextView)view.findViewById(R.id.complete);
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


    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }
}
