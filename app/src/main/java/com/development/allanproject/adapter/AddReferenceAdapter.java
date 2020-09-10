package com.development.allanproject.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.experience.ReferenceData;

import java.util.List;

public class AddReferenceAdapter extends RecyclerView.Adapter<AddReferenceAdapter.MyViewHolder> {

    private List<ReferenceData> documentList;
    private Context context;
    public ImageView thumbnail;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView state,compat;
        public ImageView delete,addMore;
        public EditText facilityName,name,title,type,manageer, mobile,email;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            delete = (ImageView)view.findViewById(R.id.delete);
            addMore = (ImageView)view.findViewById(R.id.add_more);
            name = (EditText)view.findViewById(R.id.name);
            facilityName = (EditText)view.findViewById(R.id.facility_name);
            title = (EditText)view.findViewById(R.id.title);
            manageer = (EditText)view.findViewById(R.id.title_manager);
            mobile = (EditText)view.findViewById(R.id.mobile);
            email = (EditText)view.findViewById(R.id.email);
        }
    }


    public AddReferenceAdapter(Context context,List<ReferenceData> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @Override
    public AddReferenceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_reference_layout, parent, false);

        return new AddReferenceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddReferenceAdapter.MyViewHolder holder, int position) {
        ReferenceData document = documentList.get(position);
        holder.addMore.setVisibility(View.GONE);
        holder.delete.setVisibility(View.VISIBLE);
        documentList.get(position).setStd_code("+91");
        holder.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                documentList.get(position).setName(editable.toString());
            }
        });

        holder.facilityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                documentList.get(position).setFacility_name(editable.toString());
            }
        });

        holder.title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                documentList.get(position).setJob_title(editable.toString());
            }
        });

        holder.manageer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                documentList.get(position).setJob_type(editable.toString());
            }
        });

        holder.mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                documentList.get(position).setPhone(editable.toString());
            }
        });

        holder.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                documentList.get(position).setEmail(editable.toString());
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  int index =  documentList.indexOf(document);
                if(documentList.size()>=0)
                documentList.remove(position);
                notifyDataSetChanged();
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

    public void restoreItem(ReferenceData item, int position) {
        documentList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
