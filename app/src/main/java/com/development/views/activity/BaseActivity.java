package com.development.views.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.development.allanproject.R;

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

    public void showAgeDialog(){

    }
}
