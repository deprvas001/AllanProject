package com.development.allanproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.Faq;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyViewHolder> {
    private List<Faq> faqList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;
        public ImageView content_visiblity;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            content_visiblity = (ImageView)view.findViewById(R.id.context_visibilty);

        }
    }


    public FaqAdapter(List<Faq> faqList) {
        this.faqList = faqList;
    }

    @Override
    public FaqAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faq_layout, parent, false);

        return new FaqAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FaqAdapter.MyViewHolder holder, int position) {
        Faq faq = faqList.get(position);
        holder.title.setText(faq.getTitle());
        holder.description.setText(faq.getDescription());

        holder.content_visiblity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkVisibility(holder.description);
            }
        });

    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    private void checkVisibility(View view){
        if(view.getVisibility() ==View.VISIBLE){
            view.setVisibility(View.GONE);
        }else{
            view.setVisibility(View.VISIBLE);
        }
    }
}
