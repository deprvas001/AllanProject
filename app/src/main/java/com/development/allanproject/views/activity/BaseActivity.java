package com.development.allanproject.views.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.development.allanproject.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseActivity extends AppCompatActivity {

    private  String date="";
    ProgressDialog progressDialog;

    public void showProgressDialog(String message) {
        if(progressDialog == null)
            progressDialog = new ProgressDialog(this);

        if (!progressDialog.isShowing()) {
            progressDialog.setMessage(message);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (progressDialog !=null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showAlertDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(context.getString(R.string.app_name))
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public String getDatetime() {
        Calendar c = Calendar.getInstance();
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss SSSZ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String timezone = sdf.format(c.getTime());
        return timezone;
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public String setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog mDatePickerDialog  = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy");
                //  sd.setTimeZone(TimeZone.getTimeZone("UTC"));
                final Date startDate = newDate.getTime();
                date = sd1.format(startDate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        mDatePickerDialog.show();
        return  date;

    }

    public void showMessageDialog(Context context, int status){
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("Error "+String.valueOf(status))
                .setMessage("Something went wrong!")
                .setCancelable(false)
                .setAnimation(R.raw.work_progress)
                .setNegativeButton("Ok", MaterialDialog.NO_ICON, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Retry",R.drawable.retry_icon, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    public void showCustomDialog(Context context, int status){
        //then we will inflate the custom alert dialog xml that we created
        StringBuilder message = new StringBuilder();
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null);

        //Now we need an AlertDialog.Builder object
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);

        TextView code = (TextView)dialogView.findViewById(R.id.error_code);
        message.append("Error Code " + String.valueOf(status));
        if(status == 409){
           message.append("\n Email Already Exist");
        }
        code.setText(message);
       /* Button ok =(Button)dialogView.findViewById(R.id.buttonOk);
        Button cancel = (Button)dialogView.findViewById(R.id.button_cancel);*/
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

    /*    ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//* Intent i = new Intent(DayCareBanner.this, DayCareBanner.class);
                startActivity(i);*//*
                alertDialog.dismiss();
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//* alertDialog.dismiss();*//*
                showCareActivity();
            }
        });*/


        //finally creating the alert dialog and displaying it

    }
}
