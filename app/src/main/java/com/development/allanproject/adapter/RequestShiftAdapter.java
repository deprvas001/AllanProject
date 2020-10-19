package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.openshiftModel.GetOpenShiftList;

import java.util.List;

public class RequestShiftAdapter extends RecyclerView.Adapter<RequestShiftAdapter.MyViewHolder> {

    private List<GetOpenShiftList> documentList;
    private Context context;
   RequestShiftListItemAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date,address;
        public CardView cardView;
        public RecyclerView recyclerView;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView)view.findViewById(R.id.date);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        }
    }


    public RequestShiftAdapter(Context context,List<GetOpenShiftList> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @Override
    public RequestShiftAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.open_shift_row, parent, false);

        return new RequestShiftAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RequestShiftAdapter.MyViewHolder holder, int position) {
        GetOpenShiftList document = documentList.get(position);
        holder.date.setText(document.getDate());

        adapter = new RequestShiftListItemAdapter(context, document.getArray());
        mLayoutManager = new LinearLayoutManager(context);
        holder.recyclerView.setLayoutManager(mLayoutManager);
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.recyclerView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }
}
