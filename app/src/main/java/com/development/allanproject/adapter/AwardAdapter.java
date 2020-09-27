package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.AwardClass;
import com.development.allanproject.model.award.AwardData;
import com.development.allanproject.model.lanugage.LanguageData;

import java.util.List;

public class AwardAdapter  extends RecyclerView.Adapter<AwardAdapter.MyViewHolder> {
    private List<AwardData> awardList;
    private Context context;

   AwardCallBack callBack;

    public interface   AwardCallBack{
        void listenerMethod(AwardData data);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,org, date;
        public ImageView delete;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            org = (TextView)view.findViewById(R.id.organization);
            date = (TextView)view.findViewById(R.id.date);
            delete = (ImageView) view.findViewById(R.id.delete);


        }
    }


    public AwardAdapter(Context context,List<AwardData> awardList, AwardCallBack callBack) {
        this.context = context;
        this.awardList = awardList;
        this.callBack = callBack;
    }

    @Override
    public AwardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.award_screen_row, parent, false);

        return new AwardAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AwardAdapter.MyViewHolder holder, int position) {
        AwardData award = awardList.get(position);
        holder.name.setText(award.getAward());
        holder.org.setText(award.getOrganization());
        holder.date.setText(award.getAward_date());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.listenerMethod(award);
            }
        });


    }

    @Override
    public int getItemCount() {
        return awardList.size();
    }
}
