package com.development.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.views.model.TrainingClass;

import java.util.List;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.MyViewHolder> {
    private List<TrainingClass> bannerList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.training_value);

        }
    }


    public TrainingAdapter(Context context,List<TrainingClass> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
    }

    @Override
    public TrainingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.training_item_row, parent, false);

        return new TrainingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrainingAdapter.MyViewHolder holder, int position) {
        TrainingClass listData = bannerList.get(position);
        holder.name.setText(listData.getText());

    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }
}
