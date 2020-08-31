package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.AwardClass;

import java.util.List;

public class AwardAdapter  extends RecyclerView.Adapter<AwardAdapter.MyViewHolder> {
    private List<AwardClass> awardList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public AwardAdapter(Context context,List<AwardClass> awardList) {
        this.context = context;
        this.awardList = awardList;
    }

    @Override
    public AwardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.award_screen_row, parent, false);

        return new AwardAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AwardAdapter.MyViewHolder holder, int position) {
        AwardClass document = awardList.get(position);
        holder.name.setText(document.getName());

    }

    @Override
    public int getItemCount() {
        return awardList.size();
    }
}
