package com.development.allanproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.missedShift.MissedShiftDetail;
import com.development.allanproject.model.preferedfacility.FacilityData;
import com.development.allanproject.util.Util;
import com.development.allanproject.views.activity.ui.facilityprofile.FacilityProfileScreen;

import java.util.List;

public class MissedShiftAdapter extends RecyclerView.Adapter<MissedShiftAdapter.MyViewHolder>  {

    private List<MissedShiftDetail> preferredList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date,address,price,time,type,timefrom,checkin,checkout;
        public CardView cardView;
        public RatingBar ratingBar;
        public Button update;
        public ImageView imageView,type_image,fav;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            type = (TextView)view.findViewById(R.id.type);
            type_image = (ImageView)view.findViewById(R.id.type_image);
            imageView = (ImageView)view.findViewById(R.id.imageView);
            cardView = (CardView)view.findViewById(R.id.cardView);
            date = (TextView)view.findViewById(R.id.date);
            timefrom = (TextView)view.findViewById(R.id.time_from);
            checkin = (TextView)view.findViewById(R.id.check_in);
            checkout = (TextView)view.findViewById(R.id.check_out);
            update = (Button)view.findViewById(R.id.update);
        }
    }


    public MissedShiftAdapter(Context context,List<MissedShiftDetail> preferredList) {
        this.context = context;
        this.preferredList = preferredList;
    }

    @Override
    public MissedShiftAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.missed_clock_item, parent, false);

        return new MissedShiftAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MissedShiftAdapter.MyViewHolder holder, int position) {
        MissedShiftDetail data = preferredList.get(position);
        holder.name.setText(data.getName());
        holder.date.setText(data.getStart_date());
        holder.timefrom.setText(data.getBreaktime());
        holder.checkin.setText(data.getClock_in_time());
        holder.checkout.setText(data.getClock_out_time());
        holder.type.setText(data.getType());

        if(data.getWaiting_for_approval()){
            holder.update.setText("Waiting For Approval");
        }

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

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, FacilityProfileScreen.class);
//                intent.putExtra("facility_id", data.getId());
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return preferredList.size();
    }
}
