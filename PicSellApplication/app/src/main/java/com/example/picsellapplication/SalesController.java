package com.example.picsellapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SalesController#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalesController extends Fragment {
    SalesModel model;

    TextView tvStartDate, tvEndDate, tvResult;
    ImageView imgStartDate, imgEndDate;
    View.OnClickListener clicked;

    private Calendar today = Calendar.getInstance();
    private Calendar tempCalendar = Calendar.getInstance();
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();

    private int flagView; // 1 if startDate is clicked, 0 if endDate is clicked
    private int[] reportType = new int[]{-1,-1,-1};
    // [0] report type chosen in dropdown
    // [1] report type of start date
    // [2] report type of end date
    // all report type should have the same value in order to be correct

    RecyclerView recycler_view;
    SalesTableAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SalesController() {}
    public SalesController(Context context){
        model = new SalesModel(context);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SalesView.
     */
    // TODO: Rename and change types and number of parameters
    public static SalesController newInstance(String param1, String param2) {
        SalesController fragment = new SalesController();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView;
        layoutView = inflater.inflate(R.layout.fragment_sales_view, container, false);
        Spinner dropdown = (Spinner) layoutView.findViewById(R.id.spinnerReport);
        String[] typeOfReport = new String[]{"Day", "Month", "Year"};

        //create an adapter to describe how the typeOfReport are displayed
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, typeOfReport);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                reportType[0] = position;

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                reportType[0] = 0; // default is day report type
            }
        });

        clicked = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.tvStartDate || view.getId() == R.id.imgStartDate) {
                    flagView = 1;
                    if (reportType[0] == 0) {
                        getDatePicker();
                        reportType[1] = 0;
                    } else if (reportType[0] == 1) {
                        getMonthPicker();
                        reportType[1] = 1;
                    } else if (reportType[0] == 2) {
                        getYearPicker();
                        reportType[1] = 2;
                    }
                    tvStartDate.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                        @Override
                        public void afterTextChanged(Editable editable) {
                            tvEndDate.setOnClickListener(clicked);
                            imgEndDate.setOnClickListener(clicked);
                        }
                    });
                } else if (view.getId() == R.id.tvEndDate || view.getId() == R.id.imgEndDate) {
                    flagView = 0;
                    if (reportType[0] == 0) {
                        getDatePicker();
                        reportType[2] = 0;
                    } else if (reportType[0] == 1) {
                        getMonthPicker();
                        reportType[2] = 1;
                    } else if (reportType[0] == 2) {
                        getYearPicker();
                        reportType[2] = 2;
                    }
                    tvEndDate.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                        @Override
                        public void afterTextChanged(Editable editable) {
                            setRecyclerView();

                            if (!verifyReportType())
                                Toast.makeText(getActivity(), "There's something wrong with the report format entered.", Toast.LENGTH_SHORT).show();
                            else if(!verifyDate())
                                Toast.makeText(getActivity(), "Invalid date range", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };

        recycler_view = (RecyclerView) layoutView.findViewById(R.id.recycler_view);
        imgStartDate = (ImageView) layoutView.findViewById(R.id.imgStartDate);
        imgStartDate.setOnClickListener(clicked);
        tvStartDate = (TextView) layoutView.findViewById((R.id.tvStartDate));
        tvStartDate.setOnClickListener(clicked);
        tvEndDate = (TextView) layoutView.findViewById((R.id.tvEndDate));
        imgEndDate = (ImageView) layoutView.findViewById(R.id.imgEndDate);
        tvResult = (TextView) layoutView.findViewById(R.id.tvResult);

        return layoutView;
    }

    // Dialog functions
    private void getDatePicker(){
        int thisYear = today.get(Calendar.YEAR);
        int thisMonth = today.get(Calendar.MONTH);
        int thisDayOfMonth = today.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dateOfMonth) {
                tempCalendar.set(year, month, dateOfMonth);
                updateLabel(0);
            }
        }, thisYear, thisMonth, thisDayOfMonth); // default values

        if(flagView == 1)
            datePicker.setMessage("Start Date");

        else if (flagView == 0)
            datePicker.setMessage("End Date");
//        datePicker.getDatePicker().setMaxDate(today.getTimeInMillis());
        datePicker.show();
    }

    private void getMonthPicker(){
        MonthYearDialog builder = new MonthYearDialog();
        builder.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dateOfMonth) {
                tempCalendar.set(year, month, 1);
                updateLabel(1);
            }
        }, flagView);
        builder.show(getFragmentManager(), "MonthYearDialog");
    }

    private void getYearPicker(){
        YearPickerDialog builder = new YearPickerDialog();
        builder.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dateOfMonth) {
                tempCalendar.set(year, 0, 1);
                updateLabel(2);
            }
        }, flagView);
        builder.show(getFragmentManager(), "YearPickerDialog");
    }

    private void setRecyclerView(){
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SalesTableAdapter(getActivity(), displayReport());

        recycler_view.setAdapter(adapter);
    }
    // model-interaction functions
    public List<SalesModel> displayReport(){
        SalesModel thisModel = new SalesModel(getActivity());
        List<SalesModel> salesList = new ArrayList<SalesModel>();
        int start = getFormattedDate(startDate);
        int end = getFormattedDate(endDate);

        // if report type = month or year, add 1 to the endDate
        // when report type is either month or year, its value is the day before the month/year begins
        // so if month is December, its value would be similar to December 0.

        if(reportType[0] == 1 || reportType[2] == 1){
            int year = endDate.get(Calendar.YEAR);
            int month = endDate.get(Calendar.MONTH);
            endDate.set(year, month+1, 0);
            end = getFormattedDate(endDate);
        }
        else if(reportType[0] == 2 || reportType[2] == 2){
            int year = endDate.get(Calendar.YEAR);
            endDate.set(year+1, 0, 0);
            end = getFormattedDate(endDate);
        }

        double totalSales = 0;
        double totalCost = 0;

        Cursor cursor = thisModel.getSoldItems(start, end);
        if(cursor.moveToFirst()){
            do{
                String itemName = cursor.getString(0);
                int qty = cursor.getInt(1);
                double cost = cursor.getDouble(2);
                double price = cursor.getDouble(3);

                totalSales += price * qty;
                totalCost += cost * qty;

                SalesModel temp = new SalesModel(itemName, qty, cost, price);
                salesList.add(temp);
            }while(cursor.moveToNext());
        }

        return salesList;
    }

    public boolean verifyReportType(){
        // reportType[0] is the report type chosen from the dropdown
        // reportType[1] is the report type chosen from the startDate
        // reportType[2] is the report type chosen from the endDate

        if(reportType[0] == reportType[1] && reportType[0] == reportType [2])
            return true;

        return  false;
    }

    public boolean verifyDate(){
        long thisDay = today.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        long end = endDate.getTimeInMillis();

        if(start > end || start > thisDay)
            return false;

        return true;
    }

    //helper functions
    private void updateLabel(int type){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.TAIWAN);
        int year, month, dateOfMonth;
        year = tempCalendar.get(Calendar.YEAR);
        month = tempCalendar.get(Calendar.MONTH);
        dateOfMonth = tempCalendar.get(Calendar.DAY_OF_MONTH);

        if(flagView == 1){
            startDate.set(year,month,dateOfMonth);

            if(type == 0)
                tvStartDate.setText(dateFormat.format(startDate.getTime()));
            else if(type == 1)
                tvStartDate.setText(getMonthValue(month)+ " " + year);

            else if(type == 2)
                tvStartDate.setText(year+"");
        }
        else if(flagView == 0){ // if end date is selected, ari ta mag verify if sakto ba ang inputted dates
            endDate.set(year,month,dateOfMonth);
            if(type == 0)
                tvEndDate.setText(dateFormat.format(endDate.getTime()));

            else if(type == 1)
                tvEndDate.setText(getMonthValue(month)+ " " + year);

            else if(type == 2)
                tvEndDate.setText("" + year);
        }
    }

//    public boolean deleteSalesRecords(){
//        return model.deleteSalesRecords();
//    }
//    public boolean deleteItemRecords(){
//        return model.deleteItemRecords();
//    }
    
    public String getMonthValue(int value){
        String[] months = new String[]{"Jan", "Feb", "Mar", "April", "May", "June", "July",
                "Aug", "Sept", "Oct", "Nov", "Dec"};
        return months[value];
    }
    public int getFormattedDate(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long timeInMillis = calendar.getTimeInMillis();
        // 86400000 is the conversion of milliseconds to day, given time in milliseconds
        int dateInDays = (int) (timeInMillis / 86400000);

        return dateInDays;
    }
}