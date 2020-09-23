package com.development.allanproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.taxholding.TaxData;

import java.util.List;

public class TaxHoldingAdapter extends
        RecyclerView.Adapter<TaxHoldingAdapter.ViewHolder> {

    private List<TaxData> packageList;
    private Context context;

    private RadioGroup lastCheckedRadioGroup = null;

    public TaxHoldingAdapter(List<TaxData> packageListIn
            , Context ctx) {
        packageList = packageListIn;
        context = ctx;
    }

    @Override
    public TaxHoldingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tax_holding_row, parent, false);

        TaxHoldingAdapter.ViewHolder viewHolder =
                new TaxHoldingAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TaxHoldingAdapter.ViewHolder holder,
                                 int position) {
        TaxData taxData = packageList.get(position);
        holder.question.setText(taxData.getQuestion());
        int id = (position+1)*100;
        for(int i =0 ;i<taxData.getRadio().size();i++){
            RadioButton rb = new RadioButton(TaxHoldingAdapter.this.context);
            rb.setId(taxData.getRadio().get(i).getId());
            rb.setText(taxData.getRadio().get(i).getValue());
            holder.optionGroup.addView(rb);
        }

       holder.optionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //since only one package is allowed to be selected
                //this logic clears previous selection
                //it checks state of last radiogroup and
                // clears it if it meets conditions

                Toast.makeText(TaxHoldingAdapter.this.context,
                        "Radio button clicked " + radioGroup.getCheckedRadioButtonId(),
                        Toast.LENGTH_SHORT).show();

                packageList.get(position).getRadio().get(i).setStatus(true);

            }
        });

        if(taxData.getText().size()>0){
            holder.amount_layout.setVisibility(View.VISIBLE);
            for (int i = 0;i<taxData.getText().size();i++){
                holder.label.setText(taxData.getText().get(i).getLabel());
                holder.sign.setText(taxData.getText().get(i).getSign());
            }
        }
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView question,label, sign;
        public EditText editText;
        public RadioGroup optionGroup;
        public LinearLayout amount_layout;

        public ViewHolder(View view) {
            super(view);
            question = (TextView) view.findViewById(R.id.package_name);
            optionGroup = (RadioGroup) view.findViewById(R.id.option_grp);
            label = (TextView)view.findViewById(R.id.label);
            editText = (EditText)view.findViewById(R.id.edit_dollar);
            sign = (TextView)view.findViewById(R.id.sign);
            amount_layout = (LinearLayout)view.findViewById(R.id.amount_layout);
        }
    }
}
