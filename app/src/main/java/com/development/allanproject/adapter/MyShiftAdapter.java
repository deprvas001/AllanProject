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
import com.development.allanproject.model.myshift.ShiftItem;
import com.development.allanproject.util.Util;
import com.development.allanproject.views.activity.ui.openShift.ViewOpenShift;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyShiftAdapter extends RecyclerView.Adapter<MyShiftAdapter.MyViewHolder> {
    private ArrayList<ShiftItem> shiftList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, address, price, time, type, event_name,
                shift_name, event_date, event_time;
        public CardView cardView, cardEvent;
        public ImageView imageView, type_image;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
            event_name = (TextView) view.findViewById(R.id.event_name);
            shift_name = (TextView) view.findViewById(R.id.shift_name);
            event_date = (TextView) view.findViewById(R.id.event_date);
            event_time = (TextView) view.findViewById(R.id.event_time);
            cardEvent = (CardView) view.findViewById(R.id.card_event);
            address = (TextView) view.findViewById(R.id.address);
            time = (TextView) view.findViewById(R.id.time);
            type = (TextView) view.findViewById(R.id.type);
            date = (TextView) view.findViewById(R.id.date);
            type_image = (ImageView) view.findViewById(R.id.type_image);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            cardView = (CardView) view.findViewById(R.id.cardView);
        }
    }


    public MyShiftAdapter(Context context, ArrayList<ShiftItem> shiftList) {
        this.context = context;
        this.shiftList = shiftList;
    }

    @Override
    public MyShiftAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_shift_row, parent, false);

        return new MyShiftAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyShiftAdapter.MyViewHolder holder, int position) {
        ShiftItem data = shiftList.get(position);
        if (data.getCategory().equals("event")) {
            holder.cardView.setVisibility(View.GONE);
            holder.cardEvent.setVisibility(View.VISIBLE);
        } else {
            holder.cardView.setVisibility(View.VISIBLE);
            holder.cardEvent.setVisibility(View.GONE);
        }
        holder.event_name.setText(data.getFacility_name());
        holder.shift_name.setText(data.getName());
        holder.event_date.setText(data.getDate());
        holder.event_time.setText(data.getTime());

        holder.name.setText(data.getFacility_name());
        holder.address.setText(data.getAddress());
        holder.time.setText(data.getTime());
        holder.date.setText(data.getStart_date());
        holder.price.setText(data.getPrice());
        holder.type.setText(data.getType());
        if (data.getIcon() != null) {
            Util.loadImage(
                    holder.imageView, data.getIcon(),
                    Util.getCircularDrawable(context)
            );
        }
        if (data.getType_icon() != null) {
            Util.loadImage(
                    holder.type_image, data.getType_icon(),
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
