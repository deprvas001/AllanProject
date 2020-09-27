package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.research.ResearchData;

import java.util.ArrayList;
import java.util.List;

public class ResearchAdapter extends RecyclerView.Adapter<ResearchAdapter.MyViewHolder> {
    private ArrayList<ResearchData> awardList;
    private Context context;

    ResearchCallBack callBack;

    public interface   ResearchCallBack {
        void listenerMethod(ResearchData data);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,address, startDate,endDate;
        public ImageView delete;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            address = (TextView)view.findViewById(R.id.address);
            startDate = (TextView)view.findViewById(R.id.start_date);
            endDate = (TextView)view.findViewById(R.id.end_date);
            delete = (ImageView) view.findViewById(R.id.delete);


        }
    }


    public ResearchAdapter(Context context,ArrayList<ResearchData> awardList, ResearchCallBack callBack) {
        this.context = context;
        this.awardList = awardList;
        this.callBack = callBack;
    }

    @Override
    public ResearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.research_layout_row, parent, false);

        return new ResearchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResearchAdapter.MyViewHolder holder, int position) {
        ResearchData data = awardList.get(position);
        holder.name.setText(data.getOrganization());
        holder.address.setText(data.getAddress());
        holder.endDate.setText(data.getEnd_date());
        holder.startDate.setText(data.getStart_date());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.listenerMethod(data);
            }
        });


    }

    @Override
    public int getItemCount() {
        return awardList.size();
    }
}
