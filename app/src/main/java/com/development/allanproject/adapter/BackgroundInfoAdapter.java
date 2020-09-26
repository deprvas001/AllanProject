package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.BackgroundInformationClass;
import com.development.allanproject.model.backinformation.BackgroundData;
import com.development.allanproject.model.backinformation.GetBackgroundData;

import java.util.List;

public class BackgroundInfoAdapter extends  RecyclerView.Adapter<BackgroundInfoAdapter.MyViewHolder> {
    private List<BackgroundData> infoList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView question,label, sign;
        public EditText editText;
        public ImageView info;
        public RadioGroup optionGroup;
        public LinearLayout amount_layout;


        public MyViewHolder(View view) {
            super(view);
            question = (TextView) view.findViewById(R.id.package_name);
            optionGroup = (RadioGroup) view.findViewById(R.id.option_grp);
            label = (TextView)view.findViewById(R.id.label);
            editText = (EditText)view.findViewById(R.id.edit_dollar);
            sign = (TextView)view.findViewById(R.id.sign);
            info = (ImageView)view.findViewById(R.id.info);
            amount_layout = (LinearLayout)view.findViewById(R.id.amount_layout);

        }
    }


    public BackgroundInfoAdapter(Context context,List<BackgroundData> infoList) {
        this.context = context;
        this.infoList = infoList;
    }

    @Override
    public BackgroundInfoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tax_holding_row, parent, false);

        return new BackgroundInfoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BackgroundInfoAdapter.MyViewHolder holder, int position) {
        BackgroundData data = infoList.get(position);
       // holder.name.setText(document.getName());

    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }
}
