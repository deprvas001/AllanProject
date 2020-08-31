package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.LanguageClass;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.MyViewHolder> {
    private List<LanguageClass> languageList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public LanguageAdapter(Context context,List<LanguageClass> languageList) {
        this.context = context;
        this.languageList = languageList;
    }

    @Override
    public LanguageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_language_row, parent, false);

        return new LanguageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LanguageAdapter.MyViewHolder holder, int position) {
        LanguageClass document = languageList.get(position);
        holder.name.setText(document.getName());

    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

}
