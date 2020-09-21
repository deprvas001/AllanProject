package com.development.allanproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.adddocumentModel.MyDocumentData;
import com.development.allanproject.model.certificate.CertificateData;
import com.development.allanproject.util.Util;
import com.development.allanproject.views.activity.ui.addcertifictate.certificateList.UpadateCertificate;
import com.development.allanproject.views.activity.ui.adddoucment.AddDocumentDetail;

import java.util.List;

public class MyDocumentAdapter  extends RecyclerView.Adapter<MyDocumentAdapter.MyViewHolder> {

    DocumentCallBack callBack;

    public interface DocumentCallBack{
        void listenerMethod(MyDocumentData data);
    }
    private List<MyDocumentData> documentList;
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


    public MyDocumentAdapter(Context context,List<MyDocumentData> documentList,DocumentCallBack callBack) {
        this.context = context;
        this.documentList = documentList;
        this.callBack = callBack;
    }

    @Override
    public MyDocumentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_list_item, parent, false);

        return new MyDocumentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyDocumentAdapter.MyViewHolder holder, int position) {
        MyDocumentData listData = documentList.get(position);
        holder.name.setText(listData.getName());
        holder.document_type.setText(listData.getDoc_type());
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
                Intent intent =new  Intent(context, AddDocumentDetail.class);
                intent.putExtra("select_doc", listData);
                context.startActivity(intent);
            }
        });

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
