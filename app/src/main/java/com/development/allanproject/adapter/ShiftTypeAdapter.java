package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.commonapi.ShiftPreference;
import com.development.allanproject.model.commonapi.ShiftType;
import com.development.allanproject.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ShiftTypeAdapter extends RecyclerView.Adapter<ShiftTypeAdapter.MyViewHolder> {
    private List<ShiftType> facilityList;
    private Context context;
    public static ArrayList<Integer> selectShiftType = new ArrayList<Integer>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView icon;
        public CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            icon = (ImageView)view.findViewById(R.id.icon);
            checkBox = (CheckBox)view.findViewById(R.id.checkbox);
        }
    }


    public ShiftTypeAdapter(Context context,List<ShiftType> facilityList) {
        this.context = context;
        this.facilityList = facilityList;
    }

    @Override
    public ShiftTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.set_preference_row, parent, false);

        return new ShiftTypeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShiftTypeAdapter.MyViewHolder holder, int position) {
        ShiftType  document = facilityList.get(position);
        holder.name.setText(document.getName());

        Util.loadImage(holder.icon,document.getIcon(),
                Util.getCircularDrawable(context));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    selectShiftType.add(document.getId());
                    Toast.makeText(context, "checkced", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Uncheckced", Toast.LENGTH_SHORT).show();
                    // selectShiftType.remove(document.getId());
                    if(selectShiftType.contains(document.getId())){
                        int index = selectShiftType.indexOf(document.getId());
                        selectShiftType.remove(index);
                    }
//                   if(selectShiftType.contains(document.getId())){
//                       selectShiftType.remove(document.getId());
//                       Toast.makeText(context, "removed", Toast.LENGTH_SHORT).show();
//                   }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return facilityList.size();
    }
}
