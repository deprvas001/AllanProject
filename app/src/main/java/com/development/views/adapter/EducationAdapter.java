package com.development.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.views.model.EducationClass;
import com.development.views.model.TrainingClass;

import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.MyViewHolder> {

    private List<EducationClass> educationList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public EducationAdapter(Context context,List<EducationClass> educationList) {
        this.context = context;
        this.educationList = educationList;
    }

    @Override
    public EducationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.education_row, parent, false);

        return new EducationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EducationAdapter.MyViewHolder holder, int position) {
        EducationClass listData = educationList.get(position);
        holder.name.setText(listData.getName());

    }

    @Override
    public int getItemCount() {
        return educationList.size();
    }
}
