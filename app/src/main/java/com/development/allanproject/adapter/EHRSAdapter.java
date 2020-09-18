package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.ehrs.EHRSData;
import com.development.allanproject.model.speciality.Data;

import java.util.List;

public class EHRSAdapter extends RecyclerView.Adapter<EHRSAdapter.MyViewHolder> {
    EHRSCallBack callBack;

    public interface EHRSCallBack{
        void listenerMethod(EHRSData data);
    }
   
    private List<EHRSData> documentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, delete;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            delete = (TextView)view.findViewById(R.id.delete);
        }
    }


    public EHRSAdapter(Context context,List<EHRSData> documentList,EHRSCallBack callBack) {
        this.context = context;
        this.documentList = documentList;
        this.callBack = callBack;
    }

    @Override
    public EHRSAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.speciality_row, parent, false);

        return new EHRSAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EHRSAdapter.MyViewHolder holder, int position) {
        EHRSData document = documentList.get(position);
        holder.name.setText(document.getName());

        holder.delete.setOnClickListener(new View.OnClickListener() {
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
