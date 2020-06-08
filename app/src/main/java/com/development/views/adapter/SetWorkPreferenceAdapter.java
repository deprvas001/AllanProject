package com.development.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.views.model.SetWorkPreferenceClass;

import java.util.List;

public class SetWorkPreferenceAdapter  extends RecyclerView.Adapter<SetWorkPreferenceAdapter.MyViewHolder> {

    private List<SetWorkPreferenceClass> preferenceList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public SetWorkPreferenceAdapter(Context context,List<SetWorkPreferenceClass> preferenceList) {
        this.context = context;
        this.preferenceList = preferenceList;
    }

    @Override
    public SetWorkPreferenceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.work_preference_row, parent, false);

        return new SetWorkPreferenceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SetWorkPreferenceAdapter.MyViewHolder holder, int position) {
        SetWorkPreferenceClass listData = preferenceList.get(position);
        holder.name.setText(listData.getName());

    }

    @Override
    public int getItemCount() {
        return preferenceList.size();
    }
}
