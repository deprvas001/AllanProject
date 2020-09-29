package com.development.allanproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.development.allanproject.R;
import com.development.allanproject.model.taxholding.RadioSelectionOption;
import com.development.allanproject.model.taxholding.TaxData;

import java.util.List;

import static java.security.AccessController.getContext;

public class TaxHoldingAdapter extends
        RecyclerView.Adapter<TaxHoldingAdapter.ViewHolder> {

    private List<TaxData> packageList;
    private Context context;
    private String info;

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
            if(taxData.getRadio().get(i).getStatus()){
                rb.setChecked(true);
            }else{
                rb.setChecked(false);
            }
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

                for(int j=0; j<  packageList.get(position).getRadio().size();j++) {
                    if(packageList.get(position).getRadio().get(j).getId() == radioGroup.getCheckedRadioButtonId()){
                        packageList.get(position).getRadio().get(j).setStatus(true);
                    }else{
                        packageList.get(position).getRadio().get(j).setStatus(false);
                    }
                }

            }
        });

        if(taxData.getText().size()>0){
            holder.amount_layout.setVisibility(View.VISIBLE);
            for (int i = 0;i<taxData.getText().size();i++){
                holder.info.setVisibility(View.VISIBLE);
                info = taxData.getText().get(i).getInfo();
                holder.label.setText(taxData.getText().get(i).getLabel());
                holder.sign.setText(taxData.getText().get(i).getSign());
                holder.editText.setText(taxData.getText().get(i).getValue());
            }
        }



        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                packageList.get(position).getText().get(0).setValue(editable.toString());
            }
        });

        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     showCustomDialog();
            }
        });
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView question,label, sign;
        public EditText editText;
        public ImageView info;
        public RadioGroup optionGroup;
        public LinearLayout amount_layout;

        public ViewHolder(View view) {
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

    private void showCustomDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("Information");
        alertDialog.setMessage(info);

        alertDialog.setPositiveButton(
                "Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do the stuff..
                    }
                }
        );

        alertDialog.show();
    }

}
