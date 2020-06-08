package com.development.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.views.model.MyDocumentClass;

import java.util.List;

public class MyDocumentAdapter  extends RecyclerView.Adapter<MyDocumentAdapter.MyViewHolder> {

    private List<MyDocumentClass> documentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);

        }
    }


    public MyDocumentAdapter(Context context,List<MyDocumentClass> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @Override
    public MyDocumentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_document_row, parent, false);

        return new MyDocumentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyDocumentAdapter.MyViewHolder holder, int position) {
        MyDocumentClass listData = documentList.get(position);
        holder.name.setText(listData.getName());

    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }
}
