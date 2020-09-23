package com.development.allanproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.downloadfile.DownloadTask;
import com.development.allanproject.model.form.FormData;
import com.development.allanproject.util.Util;
import com.development.allanproject.views.activity.ui.adddoucment.AddDocumentDetail;

import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.MyViewHolder> {
    FormAdapter.DocumentCallBack callBack;

    public interface DocumentCallBack{
        void listenerMethod(FormData data);
    }
    private List<FormData> documentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, document_type,status, view_pdf,download,upload;
        public ImageView logo;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            logo = (ImageView)view.findViewById(R.id.logo);
            view_pdf = (TextView)view.findViewById(R.id.view);
            download = (TextView)view.findViewById(R.id.download);
            upload = (TextView)view.findViewById(R.id.upload);
        }
    }


    public FormAdapter(Context context, List<FormData> documentList, DocumentCallBack callBack) {
        this.context = context;
        this.documentList = documentList;
        this.callBack = callBack;
    }

    @Override
    public FormAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forms_row, parent, false);

        return new FormAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FormAdapter.MyViewHolder holder, int position) {
        FormData listData = documentList.get(position);
        holder.name.setText(listData.getName());

        if(listData.getIcon()!=null){
            Util.loadImage(holder.logo, listData.getIcon(),
                    Util.getCircularDrawable(context));
        }

        holder.view_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listData.getForm_url_view()!=null){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listData.getForm_url_view()));
                    context.startActivity(browserIntent);
                }

            }
        });

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listData.getForm_url_view()!=null){
                    new DownloadTask(context, listData.getForm_url_view());
                }
            }
        });

        holder.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.listenerMethod(listData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }
}
