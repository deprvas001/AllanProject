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
import com.development.allanproject.model.adddocumentModel.MyDocumentData;
import com.development.allanproject.model.socialsecurity.SecurityData;
import com.development.allanproject.util.Util;
import com.development.allanproject.views.activity.ui.adddoucment.AddDocumentDetail;
import com.development.allanproject.views.activity.ui.socialsecurity.SocialSecurityScreen;

import java.util.List;

public class SecurityAdapter extends RecyclerView.Adapter<SecurityAdapter.MyViewHolder> {
  DocumentCallBack callBack;

    public interface DocumentCallBack{
        void listenerMethod(SecurityData data);
    }
    private List<SecurityData> documentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, document_type,status;
        public ImageView delete, edit,upload_front,upload_back;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.doc_name);
            document_type = (TextView)view.findViewById(R.id.document_type);
            status = (TextView)view.findViewById(R.id.status);
            delete = (ImageView)view.findViewById(R.id.delete);
            edit = (ImageView)view.findViewById(R.id.edit);
            upload_front = (ImageView)view.findViewById(R.id.upload_front);
            upload_back = (ImageView)view.findViewById(R.id.upload_back);
        }
    }


    public SecurityAdapter(Context context, List<SecurityData> documentList, SecurityAdapter.DocumentCallBack callBack) {
        this.context = context;
        this.documentList = documentList;
        this.callBack = callBack;
    }

    @Override
    public SecurityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.security_item_row, parent, false);

        return new SecurityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SecurityAdapter.MyViewHolder holder, int position) {
        SecurityData listData = documentList.get(position);
        holder.name.setText(listData.getName());
     //   holder.document_type.setText(listData.getDoc_type());
        holder.status.setText(listData.getVerified_status());

        if(listData.getDoc_front()!=null){
            Util.loadImage(holder.upload_front, listData.getDoc_front(),
                    Util.getCircularDrawable(context));
        }

        if(listData.getDoc_back()!=null){
            Util.loadImage(holder.upload_back, listData.getDoc_back(),
                    Util.getCircularDrawable(context));
        }

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new  Intent(context, SocialSecurityScreen.class);
                intent.putExtra("select_doc", listData);
                context.startActivity(intent);
            }
        });

        holder.delete.setVisibility(View.GONE);
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
}
