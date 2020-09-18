package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.HealthDocumentClass;
import com.development.allanproject.model.certificate.CertificateData;
import com.development.allanproject.model.healthDocument.HealthDocumentData;
import com.development.allanproject.util.Util;

import java.util.List;

public class HealthDocumentAdpater extends RecyclerView.Adapter<HealthDocumentAdpater.MyViewHolder> {
    HealthDocCallBack callBack;

    public interface HealthDocCallBack{
        void listenerMethod(HealthDocumentData data);
    }

    private List<HealthDocumentData> documentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date;
        public ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            imageView = (ImageView)view.findViewById(R.id.imageView);
            date = (TextView)view.findViewById(R.id.date);

        }
    }


    public HealthDocumentAdpater(Context context,List<HealthDocumentData> documentList,HealthDocCallBack callBack) {
        this.context = context;
        this.documentList = documentList;
        this.callBack = callBack;
    }

    @Override
    public HealthDocumentAdpater.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.health_document_row, parent, false);

        return new HealthDocumentAdpater.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HealthDocumentAdpater.MyViewHolder holder, int position) {
        HealthDocumentData document = documentList.get(position);
        holder.name.setText(document.getName());

        if(document.getUpdated_at()!=null){
            holder.date.setText(document.getUpdated_at());
        }

        if(document.getDoc_url()!=null){
            Util.loadImage(holder.imageView,document.getDoc_url(),
                    Util.getCircularDrawable(context));
        }


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.listenerMethod(document);
            }
        });


    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }
}
