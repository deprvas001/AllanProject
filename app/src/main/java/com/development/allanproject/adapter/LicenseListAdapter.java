package com.development.allanproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.license.Data;
import com.development.allanproject.views.activity.ui.addlicenese.licenseList.EditLicenseScreen;

import java.util.List;

public class LicenseListAdapter extends RecyclerView.Adapter<LicenseListAdapter.MyViewHolder> {

    LicenseCallBack licenseCallBack;

    public interface LicenseCallBack{
        void listenerMethod(Data data);
    }
    private List<Data> licenseList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView license,state,license_no,expiry_date,compat, status;
        public ImageView edit,delete;
        public MyViewHolder(View view) {
            super(view);
            license = (TextView)view.findViewById(R.id.license);
            state = (TextView)view.findViewById(R.id.state);
            license_no = (TextView)view.findViewById(R.id.license_no);
            expiry_date = (TextView)view.findViewById(R.id.expiry_date);
            compat =  (TextView)view.findViewById(R.id.compat);
            status = (TextView)view.findViewById(R.id.status);
            edit = (ImageView)view.findViewById(R.id.edit);
            delete = (ImageView)view.findViewById(R.id.delete);
        }
    }


    public LicenseListAdapter(Context context, List<Data> licenseList, LicenseCallBack licenseCallBack) {
        this.context = context;
        this.licenseList = licenseList;
        this.licenseCallBack = licenseCallBack;
    }

    @Override
    public LicenseListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.license_list_row, parent, false);

        return new LicenseListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LicenseListAdapter.MyViewHolder holder, int position) {
        Data listData = licenseList.get(position);
        holder.license.setText(String.valueOf(listData.getLicence_id()));
        holder.state.setText(listData.getState());
        holder.license_no.setText(listData.getLicence_no());
        if(listData.getExpiration_date()!=null){
             holder.expiry_date.setText(listData.getExpiration_date());
         }
        holder.compat.setText(listData.getLicence_compact());
        holder.status.setText(listData.getVerified_status());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new  Intent(context, EditLicenseScreen.class);
                intent.putExtra("select_license", listData);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                licenseCallBack.listenerMethod(listData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return licenseList.size();
    }

}
