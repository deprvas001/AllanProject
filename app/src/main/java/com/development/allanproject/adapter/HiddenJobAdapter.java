package com.development.allanproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.hiddenjobs.GetHiddenShift;
import com.development.allanproject.util.Util;
import com.development.allanproject.views.activity.ViewOfferedShift;
import com.development.allanproject.model.HiddenJobClass;
import com.development.allanproject.views.activity.ui.openShift.ViewOpenShift;

import java.util.List;

public class HiddenJobAdapter extends RecyclerView.Adapter<HiddenJobAdapter.MyViewHolder> {

    private List<GetHiddenShift> shiftList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date,address,price,time,type,mile;
        public CardView cardView;
        public ImageView imageView,type_image;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            price = (TextView)view.findViewById(R.id.price);
            address = (TextView)view.findViewById(R.id.address);
            time = (TextView)view.findViewById(R.id.time);
            mile = (TextView)view.findViewById(R.id.mile);
            type = (TextView)view.findViewById(R.id.type);
            type_image = (ImageView)view.findViewById(R.id.type_image);
            imageView = (ImageView)view.findViewById(R.id.imageView);
            cardView = (CardView)view.findViewById(R.id.cardView);

        }
    }


    public HiddenJobAdapter(Context context,List<GetHiddenShift> shiftList) {
        this.context = context;
        this.shiftList = shiftList;
    }

    @Override
    public HiddenJobAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hidden_job_riow, parent, false);

        return new HiddenJobAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HiddenJobAdapter.MyViewHolder holder, int position) {
        GetHiddenShift data = shiftList.get(position);
        holder.name.setText(data.getName());
        holder.address.setText(data.getAddress());
        holder.time.setText(data.getStart_date());
        holder.price.setText(data.getPrice());
        holder.type.setText(data.getType());
        if(data.getIcon() !=null){
            Util.loadImage(
                    holder.imageView,data.getIcon(),
                    Util.getCircularDrawable(context)
            );
        }
        if(data.getType_icon() !=null){
            Util.loadImage(
                    holder.type_image,data.getType_icon(),
                    Util.getCircularDrawable(context)
            );
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewOpenShift.class);
                intent.putExtra("shift_id", data.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shiftList.size();
    }
}
