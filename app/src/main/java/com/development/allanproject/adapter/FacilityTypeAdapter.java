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
import com.development.allanproject.model.FacilityTypeModel;
import com.development.allanproject.model.commonapi.FacilityPreference;
import com.development.allanproject.util.Util;

import java.util.ArrayList;
import java.util.List;

public class FacilityTypeAdapter extends RecyclerView.Adapter<FacilityTypeAdapter.MyViewHolder> {

    private List<FacilityPreference> facilityList;
    private Context context;
    public static ArrayList<Integer> selectItem = new ArrayList<Integer>();

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


    public FacilityTypeAdapter(Context context,List<FacilityPreference> facilityList) {
        this.context = context;
        this.facilityList = facilityList;
    }

    @Override
    public FacilityTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facility_type_row, parent, false);

        return new FacilityTypeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FacilityTypeAdapter.MyViewHolder holder, int position) {
        FacilityPreference document = facilityList.get(position);
        holder.name.setText(document.getName());

        Util.loadImage(holder.icon,document.getIcon(),
                Util.getCircularDrawable(context));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(b){
                   selectItem.add(document.getId());
                   Toast.makeText(context, "checkced", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(context, "Uncheckced", Toast.LENGTH_SHORT).show();
                   // selectItem.remove(document.getId());
                    if(selectItem.contains(document.getId())){
                       int index = selectItem.indexOf(document.getId());
                        selectItem.remove(index);
                    }
//                   if(selectItem.contains(document.getId())){
//                       selectItem.remove(document.getId());
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