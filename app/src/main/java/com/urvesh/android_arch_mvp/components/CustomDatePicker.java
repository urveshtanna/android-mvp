package com.urvesh.android_arch_mvp.components;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.urvesh.android_arch_mvp.R;
import com.urvesh.android_arch_mvp.domain.utils.DateFormater;
import com.urvesh.android_arch_mvp.tools.HelperClass;
import com.urvesh.android_arch_mvp.tools.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Urvesh on 04-10-2016.
 */


public class CustomDatePicker extends TextView implements DatePickerDialog.OnDateSetListener{

    private Context mContext;

    public CustomDatePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context,attrs, defStyle);
        mContext = context;

    }

    public CustomDatePicker(Context context, AttributeSet attrs) {
        super(context,attrs);
        mContext = context;
        setAttributes();
    }

    public CustomDatePicker(Context context) {
        super(context);
        mContext = context;
        setAttributes();
    }

    private void setAttributes() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog() {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog dp = new DatePickerDialog(mContext, R.style.DatePickerTheme,this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)+2);
        dp.show();
    }


    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        //it is for current date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
        Date vCurrentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(vCurrentDate);
        cal.add(Calendar.DATE, 0);
        vCurrentDate = cal.getTime();
        String newstring = sdf.format(vCurrentDate);

        int month=monthOfYear+1;
        String DatePickerDate=String.valueOf(dayOfMonth+"-"+month+"-"+year);


        try {
            if (sdf.parse(DatePickerDate).before(sdf.parse(newstring))) {

                HelperClass.showMessage(mContext, mContext.getString(R.string.message_error_date_is_already_passed));
            }
            else {
                setText(DateFormater.convertDateStringFormat(String.format("%s-%s-%s", dayOfMonth, monthOfYear + 1, year),"dd-MM-yyyy","dd-MM-yyyy"));
            }
        }
        catch(Exception e) {
            Logger.exception(getClass().getName(),e);
        }
    }
}


