package com.development.allanproject.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.experience.PositionData;
import com.development.allanproject.model.license.LicenseData;

import java.util.List;

public class AddPositionAdapter extends RecyclerView.Adapter<AddPositionAdapter.MyViewHolder> {

    private List<PositionData> documentList;
    private Context context;
    public ImageView thumbnail;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, state;
        public EditText position, specaility, unit, erm;
        public ImageView delete, addMore;
        public Spinner yearSpinner,monthSpinner;
        public CheckBox checkBox;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            delete = (ImageView) view.findViewById(R.id.delete);
            addMore = (ImageView) view.findViewById(R.id.add_more);
            position = (EditText) view.findViewById(R.id.position);
            specaility = (EditText) view.findViewById(R.id.specaility);
            unit = (EditText) view.findViewById(R.id.unit);
            erm = (EditText) view.findViewById(R.id.emr);
            checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            yearSpinner = (Spinner)view.findViewById(R.id.year_spinner);
            monthSpinner = (Spinner)view.findViewById(R.id.month_spinner);


        }
    }


    public AddPositionAdapter(Context context, List<PositionData> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @Override
    public AddPositionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.position_layout_item, parent, false);

        return new AddPositionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddPositionAdapter.MyViewHolder holder, int position) {
        documentList.get(position).setPosition(holder.position.getText().toString());
        documentList.get(position).setSpeciality(holder.specaility.getText().toString());
        documentList.get(position).setUnit(holder.unit.getText().toString());
        documentList.get(position).setCharting_technology(holder.erm.getText().toString());
        documentList.get(position).setCharge_experience(holder.checkBox.isChecked());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    documentList.get(position).setCharge_experience(holder.checkBox.isChecked());
            }
        });

       holder.position.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                documentList.get(position).setPosition(editable.toString());
            }
        });

        holder.unit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                documentList.get(position).setUnit(editable.toString());
            }
        });

        holder.specaility.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                documentList.get(position).setSpeciality(editable.toString());
            }
        });

        holder.erm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                documentList.get(position).setCharting_technology(editable.toString());
            }
        });


        holder.yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                documentList.get(position).setPosition_year(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        holder.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                documentList.get(position).setPosition_month(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        holder.addMore.setVisibility(View.GONE);
        holder.delete.setVisibility(View.VISIBLE);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  int index =  documentList.indexOf(document);
                if(position >=0){
                    documentList.remove(position);
                    notifyDataSetChanged();
                }
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

    public void restoreItem(PositionData item, int position) {
        documentList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
