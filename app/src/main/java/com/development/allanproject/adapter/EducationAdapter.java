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
import com.development.allanproject.model.education.Data;
import com.development.allanproject.views.activity.ui.education.AddEducationScreen;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.MyViewHolder> {

    EducationCallBack educationCallBack;

    public interface EducationCallBack{
        void listenerMethod(Data data);
    }

    private ArrayList<Data> educationList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, program, status;
        public ImageView delete,edit;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            program = (TextView)view.findViewById(R.id.program);
            status = (TextView)view.findViewById(R.id.status);
            delete = (ImageView)view.findViewById(R.id.delete);
            edit = (ImageView)view.findViewById(R.id.edit);

        }
    }


    public EducationAdapter(Context context,ArrayList<Data> educationList,EducationCallBack educationCallBack) {
        this.context = context;
        this.educationList = educationList;
        this.educationCallBack = educationCallBack;
    }

    @Override
    public EducationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.education_row, parent, false);

        return new EducationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EducationAdapter.MyViewHolder holder, int position) {
        Data listData = educationList.get(position);
        holder.name.setText(listData.getInstitution());
        holder.program.setText(listData.getDegree());
        holder.status.setText(listData.getVerified_status());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                educationCallBack.listenerMethod(listData);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddEducationScreen.class);
                intent.putExtra("item", listData);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return educationList.size();
    }
}
