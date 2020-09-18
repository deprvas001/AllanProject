package com.development.allanproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.certificate.CertificateData;
import com.development.allanproject.model.certificate.Data;
import com.development.allanproject.views.activity.ui.addcertifictate.certificateList.UpadateCertificate;
import com.development.allanproject.views.activity.ui.addlicenese.licenseList.EditLicenseScreen;

import java.util.List;

public class CertificateListAdapter extends RecyclerView.Adapter<CertificateListAdapter.MyViewHolder> {

    CertificateCallBack callBack;

    public interface CertificateCallBack{
        void listenerMethod(CertificateData data);
    }
    private List<CertificateData> licenseList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView certificate,issue,expiry_date,compat, status;
        public ImageView edit,delete;
        public MyViewHolder(View view) {
            super(view);
            certificate = (TextView)view.findViewById(R.id.certificate);
            issue = (TextView)view.findViewById(R.id.issue);
            expiry_date = (TextView)view.findViewById(R.id.expiry_date);
            status = (TextView)view.findViewById(R.id.status);
            edit = (ImageView)view.findViewById(R.id.edit);
            delete = (ImageView)view.findViewById(R.id.delete);
        }
    }


    public CertificateListAdapter(Context context, List<CertificateData> licenseList, CertificateCallBack callBack) {
        this.context = context;
        this.licenseList = licenseList;
        this.callBack = callBack;
    }

    @Override
    public CertificateListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.certificate_list_row, parent, false);

        return new CertificateListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CertificateListAdapter.MyViewHolder holder, int position) {
        CertificateData listData = licenseList.get(position);
        holder.certificate.setText(listData.getCertificate_id().toString());
        if(listData.getIssued_from()!=null){
            holder.issue.setText(listData.getIssued_from());
        }

        if(listData.getEnd_date()!=null){
            holder.expiry_date.setText(listData.getEnd_date());
        }

        if(listData.getVerified_status()!=null){
            holder.status.setText(listData.getVerified_status());
        }

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new  Intent(context, UpadateCertificate.class);
                intent.putExtra("select_certificate", listData);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.listenerMethod(listData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return licenseList.size();
    }
}
