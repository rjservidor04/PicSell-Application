package com.example.rd_picsell;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class MonthYearDialog extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;
    private int flagView;

    public void setListener(DatePickerDialog.OnDateSetListener listener, int flagView){
        this.listener = listener;
        this.flagView = flagView;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.activity_month_year_dialog, null);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        TextView tvHeader = (TextView) dialog.findViewById(R.id.tvHeader);
        NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.datePicker_month);
        NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.datePicker_year);

        String[] months = getResources().getStringArray(R.array.Month);

        if(flagView == 1)
            tvTitle.setText("Start Date");
        else
            tvTitle.setText("End Date");

        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(11);
        monthPicker.setValue(calendar.get(Calendar.MONTH));
        monthPicker.setDisplayedValues(months);

        int year = calendar.get(Calendar.YEAR);
        yearPicker.setMinValue(2000);
        yearPicker.setMaxValue(2099);
        yearPicker.setValue(year);

        tvHeader.setText(getMonthValue(monthPicker.getValue()) + " " + yearPicker.getValue());

        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                tvHeader.setText(getMonthValue(newVal) + " " + yearPicker.getValue());
            }
        });
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                tvHeader.setText(getMonthValue(monthPicker.getValue()) + " " + newVal);
            }
        });
        builder.setView(dialog).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue()+1, 0);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MonthYearDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    public String getMonthValue(int value){
        String[] month = getResources().getStringArray(R.array.Month);
        return month[value];
    }
}