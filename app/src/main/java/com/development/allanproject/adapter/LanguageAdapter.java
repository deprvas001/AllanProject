package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.LanguageClass;
import com.development.allanproject.model.lanugage.LanguageData;
import com.development.allanproject.model.speciality.Data;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.MyViewHolder> {
    LanguageCallBack callBack;

    public interface  LanguageCallBack{
        void listenerMethod(LanguageData data);
    }

    private List<LanguageData> languageList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,proficency;
        public ImageView delete;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            proficency = (TextView)view.findViewById(R.id.proficency);
            delete = (ImageView) view.findViewById(R.id.delete);

        }
    }


    public LanguageAdapter(Context context, List<LanguageData> languageList, LanguageCallBack callBack) {
        this.context = context;
        this.languageList = languageList;
        this.callBack = callBack;
    }

    @Override
    public LanguageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_language_row, parent, false);

        return new LanguageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LanguageAdapter.MyViewHolder holder, int position) {
        LanguageData data = languageList.get(position);
        holder.name.setText(data.getLanguage());
        holder.proficency.setText(data.getProficiency());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.listenerMethod(data);
            }
        });

    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

}
