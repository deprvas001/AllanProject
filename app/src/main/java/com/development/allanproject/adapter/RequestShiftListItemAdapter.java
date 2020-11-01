package com.development.allanproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.healthDocument.HealthDocumentData;
import com.development.allanproject.model.openshiftModel.DateShiftData;
import com.development.allanproject.util.Util;
import com.development.allanproject.views.activity.ui.openShift.ViewOpenShift;

import java.util.ArrayList;

public class RequestShiftListItemAdapter extends RecyclerView.Adapter<RequestShiftListItemAdapter.MyViewHolder> {

    RequestShiftListItemAdapter.RequestShiftCallBack callBack;
    public static String button_type;

    public interface RequestShiftCallBack{
        void listenerMethod(DateShiftData data);
    }

    private ArrayList<DateShiftData> shiftList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date,address,price,time,type,mile;
        public CardView cardView;
        public ImageView imageView,type_image;
        public Button accept,down;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            price = (TextView)view.findViewById(R.id.price);
            address = (TextView)view.findViewById(R.id.address);
            time = (TextView)view.findViewById(R.id.time);
//            mile = (TextView)view.findViewById(R.id.mile);
            type = (TextView)view.findViewById(R.id.type);
            type_image = (ImageView)view.findViewById(R.id.type_image);
            imageView = (ImageView)view.findViewById(R.id.imageView);
            cardView = (CardView)view.findViewById(R.id.cardView);
            accept = (Button)view.findViewById(R.id.accept);
            down = (Button)view.findViewById(R.id.down);

        }
    }


    public RequestShiftListItemAdapter(Context context, ArrayList<DateShiftData> shiftList, RequestShiftCallBack callBack) {
        this.context = context;
        this.shiftList = shiftList;
        this.callBack = callBack;
    }

    @Override
    public RequestShiftListItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_shift_item, parent, false);

        return new RequestShiftListItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RequestShiftListItemAdapter.MyViewHolder holder, int position) {
        DateShiftData data = shiftList.get(position);
        holder.name.setText(data.getFacility_name());
        holder.address.setText(data.getAddress());
        holder.time.setText(data.getTime());
        holder.price.setText(data.getPrice());
        holder.type.setText(data.getType());
//        holder.mile.setText(data.getDistance());
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

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_type ="accept";
                callBack.listenerMethod(data);
            }
        });

        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_type = "turn_down";
                callBack.listenerMethod(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shiftList.size();
    }
}
