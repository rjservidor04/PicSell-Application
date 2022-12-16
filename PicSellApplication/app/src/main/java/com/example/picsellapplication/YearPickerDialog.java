package com.example.picsellapplication;

import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;

public class YearPickerDialog extends DialogFragment {
    private DatePickerDialog.OnDateSetListener listener;
    private int flagView;

    public void setListener(DatePickerDialog.OnDateSetListener listener, int flagView){
        this.listener = listener;
        this.flagView = flagView;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        NumberPicker yearPicker;
        final Calendar calendar = Calendar.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.dialog_year_picker, null);

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvDateType);
        TextView tvHeader = (TextView) dialog.findViewById(R.id.tvSelectedYear);
        yearPicker = (NumberPicker) dialog.findViewById(R.id.yearPicker);

        String[] months = getResources().getStringArray(R.array.Month);

        if(flagView == 1)
            tvTitle.setText("Start Date");
        else
            tvTitle.setText("End Date");

        int year = calendar.get(Calendar.YEAR);
        yearPicker.setMinValue(2000);
        yearPicker.setMaxValue(2099);
        yearPicker.setValue(year);

        tvHeader.setText(year+"");

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                tvHeader.setText(newVal+"");
            }
        });
        builder.setView(dialog).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onDateSet(null, yearPicker.getValue(),0,0);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                YearPickerDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}