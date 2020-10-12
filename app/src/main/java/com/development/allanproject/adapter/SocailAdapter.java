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
import com.development.allanproject.model.facilityprofileModel.Social;
import com.development.allanproject.util.Util;

import java.util.List;

public class SocailAdapter extends RecyclerView.Adapter<SocailAdapter.MyViewHolder> {
    
    SocailAdapter.EHRSCallBack callBack;

    public interface EHRSCallBack{
        void listenerMethod(Social data);
    }

    private List<Social> documentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, delete;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView)view.findViewById(R.id.image1);
        }
    }


    public SocailAdapter(Context context, List<Social> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @Override
    public SocailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.social_item_row, parent, false);

        return new SocailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SocailAdapter.MyViewHolder holder, int position) {
        Social document = documentList.get(position);

        Util.loadImage(
               holder.image,document.getValue(),
                Util.getCircularDrawable(context)
        );

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(document.getValue());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }
}
