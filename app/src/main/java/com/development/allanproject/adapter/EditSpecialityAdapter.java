package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.CertificateClass;
import com.development.allanproject.model.speciality.Data;

import java.util.List;

public class EditSpecialityAdapter extends RecyclerView.Adapter<EditSpecialityAdapter.MyViewHolder> {
    SpecialityCallBack callBack;

    public interface SpecialityCallBack{
        void listenerMethod(Data data);
    }

    private List<Data> documentList;
    private Context context;
    public ImageView thumbnail;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,experience,delete;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            experience = (TextView)view.findViewById(R.id.experience);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
            delete = (TextView) view.findViewById(R.id.delete);
        }
    }


    public EditSpecialityAdapter(Context context,List<Data> documentList, SpecialityCallBack callBack) {
        this.context = context;
        this.documentList = documentList;
        this.callBack = callBack;
    }

    @Override
    public EditSpecialityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.speciality_row, parent, false);

        return new EditSpecialityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EditSpecialityAdapter.MyViewHolder holder, int position) {
        Data listData = documentList.get(position);
        holder.name.setText(listData.getName());
        holder.experience.setText(listData.getExp_years());

        holder.delete.setOnClickListener(new View.OnClickListener() {
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

    public void removeItem(int position) {
        documentList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Data item, int position) {
        documentList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
