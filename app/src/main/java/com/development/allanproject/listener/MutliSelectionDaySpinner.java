package com.development.allanproject.listener;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;

import com.development.allanproject.model.preferenceModel.Day;

import java.util.ArrayList;
import java.util.Arrays;

public class MutliSelectionDaySpinner extends AppCompatSpinner implements
        DialogInterface.OnMultiChoiceClickListener {

    ArrayList<Day> facilitiyTypes = null;
    boolean[] selection = null;
    ArrayAdapter adapter;

    public MutliSelectionDaySpinner(Context context) {
        super(context);

        adapter = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(adapter);
    }

    public MutliSelectionDaySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        adapter = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(adapter);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (selection != null && which < selection.length) {
            selection[which] = isChecked;

            adapter.clear();
            adapter.add(buildSelectedFacilitiyTypeString());
        } else {
            throw new IllegalArgumentException(
                    "Argument 'which' is out of bounds.");
        }
    }

    @Override
    public boolean performClick() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Day");
        String[] FacilitiyTypeNames = new String[facilitiyTypes.size()];

        for (int i = 0; i < facilitiyTypes.size(); i++) {
            FacilitiyTypeNames[i] = facilitiyTypes.get(i).getName();
        }

        builder.setMultiChoiceItems(FacilitiyTypeNames, selection, this);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
                // Do nothing
            }
        });

        builder.show();

        return true;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(ArrayList<Day> Day) {
        this.facilitiyTypes = Day;
        selection = new boolean[this.facilitiyTypes.size()];
        adapter.clear();
        adapter.add("");

        Arrays.fill(selection, false);
    }

    public void setSelection(ArrayList<Day> selection) {
        for (int i = 0; i < this.selection.length; i++) {
            this.selection[i] = selection.get(i).getValue();
        }

//        for (FacilitiyType sel : selection) {
//            for (int j = 0; j < facilitiyTypes.size(); ++j) {
//                if (sel.getValue()) {
//                    Log.i("Test",String.valueOf(facilitiyTypes.get(j).getValue()));
//                    this.selection[j] = true;
//                }
//            }
//        }

        adapter.clear();
        adapter.add(buildSelectedFacilitiyTypeString());
    }

    private String buildSelectedFacilitiyTypeString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < facilitiyTypes.size(); ++i) {
            if (selection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }

                foundOne = true;

                sb.append(facilitiyTypes.get(i).getName());
            }
        }

        return sb.toString();
    }

    public ArrayList<Day> getSelectedFacilitiyTypes() {
        ArrayList<Day> selectedFacilitiyTypes = new ArrayList<>();

        for (int i = 0; i < facilitiyTypes.size(); ++i) {
            if (selection[i]) {
                selectedFacilitiyTypes.add(facilitiyTypes.get(i));
            }
        }

        return selectedFacilitiyTypes;
    }
}
