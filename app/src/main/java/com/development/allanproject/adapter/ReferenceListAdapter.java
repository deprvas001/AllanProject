package com.development.allanproject.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.ReferenceListClass;
import com.development.allanproject.model.commonapi.FacilityType;
import com.development.allanproject.model.ehrs.EHRSData;
import com.development.allanproject.model.reference.ReferenceData;

import org.w3c.dom.Text;

import java.util.List;

public class ReferenceListAdapter extends RecyclerView.Adapter<ReferenceListAdapter.MyViewHolder> {
   ReferenceCallBack callBack;

    public interface ReferenceCallBack {
        void listenerMethod(ReferenceData data);
    }

    private List<ReferenceData> referenceList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView facility_name, job_title,reference_name,mobile,email;

         public ImageView update;

        public MyViewHolder(View view) {
            super(view);
            facility_name = (TextView)view.findViewById(R.id.fac_name);
            job_title = (TextView)view.findViewById(R.id.job_title);
            reference_name = (TextView)view.findViewById(R.id.reference_name);
            mobile = (TextView)view.findViewById(R.id.mobile);
            email = (TextView)view.findViewById(R.id.email) ;
            update = (ImageView) view.findViewById(R.id.update);
        }
    }


    public ReferenceListAdapter(Context context, List<ReferenceData> referenceList,ReferenceCallBack callBack) {
        this.context = context;
        this.referenceList = referenceList;
        this.callBack = callBack;
    }

    @Override
    public ReferenceListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reference_list_row, parent, false);

        return new ReferenceListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReferenceListAdapter.MyViewHolder holder, int position) {
        ReferenceData listData = referenceList.get(position);
        holder.facility_name.setText(listData.getFacility_name());
        holder.job_title.setText(listData.getJob_title());
        holder.reference_name.setText(listData.getName());
        holder.mobile.setText(listData.getPhone());
        holder.email.setText(listData.getEmail());

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               callBack.listenerMethod(listData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return referenceList.size();
    }

//    public void showDialog(Activity activity, String msg){
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.request_preference_dialog_layout);
//
//
//    }
}
