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
import com.development.allanproject.model.form.FormData;
import com.development.allanproject.views.activity.ui.adddoucment.AddDocumentDetail;

import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.MyViewHolder> {
//    FormAdapter.DocumentCallBack callBack;
//
//    public interface DocumentCallBack{
//        void listenerMethod(FormData data);
//    }
    private List<FormData> documentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, document_type,status;
        public ImageView logo;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            logo = (ImageView)view.findViewById(R.id.logo);
        }
    }


    public FormAdapter(Context context, List<FormData> documentList) {
        this.context = context;
        this.documentList = documentList;
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

        

    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }
}
