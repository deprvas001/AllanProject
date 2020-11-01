package com.development.allanproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.PreferredFacilityClass;
import com.development.allanproject.model.preferedfacility.FacilityData;
import com.development.allanproject.util.Util;
import com.development.allanproject.views.activity.ui.facilityprofile.FacilityProfileScreen;
import com.development.allanproject.views.activity.ui.openShift.ViewOpenShift;

import java.util.List;

public class PreferedFacilityAdapter extends RecyclerView.Adapter<PreferedFacilityAdapter.MyViewHolder> {

    private List<FacilityData> preferredList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date,address,price,time,type,mile;
        public CardView cardView;
        public RatingBar ratingBar;
        public ImageView imageView,type_image,fav;

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
            fav = (ImageView)view.findViewById(R.id.fav);
            ratingBar = (RatingBar)view.findViewById(R.id.rating);

        }
    }


    public PreferedFacilityAdapter(Context context,List<FacilityData> preferredList) {
        this.context = context;
        this.preferredList = preferredList;
    }

    @Override
    public PreferedFacilityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prefered_facility_row, parent, false);

        return new PreferedFacilityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PreferedFacilityAdapter.MyViewHolder holder, int position) {
        FacilityData data = preferredList.get(position);
        holder.name.setText(data.getName());
        holder.address.setText(data.getAddress());
        holder.ratingBar.setRating(data.getRating());

        if(data.getImg_url() !=null){
            Util.loadImage(
                    holder.imageView,data.getImg_url(),
                    Util.getCircularDrawable(context)
            );
        }

        if(data.isFav()){
            holder.fav.setColorFilter(ContextCompat.getColor(
                    context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);

        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FacilityProfileScreen.class);
                intent.putExtra("facility_id", data.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return preferredList.size();
    }
}
