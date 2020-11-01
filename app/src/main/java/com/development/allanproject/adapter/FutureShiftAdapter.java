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

public class FutureShiftAdapter  extends RecyclerView.Adapter<FutureShiftAdapter.MyViewHolder> {

    private List<GetOpenShiftList> documentList;
    private Context context;
    FutureShiftListItemAdapter adapter;
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


    public FutureShiftAdapter(Context context,List<GetOpenShiftList> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @Override
    public FutureShiftAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.open_shift_row, parent, false);

        return new FutureShiftAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FutureShiftAdapter.MyViewHolder holder, int position) {
        GetOpenShiftList document = documentList.get(position);
        holder.date.setText(document.getDate());

        adapter = new FutureShiftListItemAdapter(context, document.getArray());
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
