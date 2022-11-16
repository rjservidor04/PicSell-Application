package com.example.rd_picsell;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SalesView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalesView extends Fragment {

    TextView tvStartDate, tvEndDate, tvResult;
    ImageView imgStartDate, imgEndDate;
    View.OnClickListener clicked;

    TableLayout tblSalesData;
    TableRow tr;

    private Calendar currentCalendar = Calendar.getInstance();
    private int thisYear = currentCalendar.get(Calendar.YEAR);
    private int thisMonth = currentCalendar.get(Calendar.MONTH);
    private int thisDayOfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH);

    private Calendar startCalendar = Calendar.getInstance();
    private Calendar endCalendar = Calendar.getInstance();

    private int flagView; // 1 if startDate is clicked, 0 if endDate is clicked
    private int[] reportType = new int[]{-1,-1,-1};

    // [0] report type chosen in dropdown
    // [1] report type of start date
    // [2] report type of end date
    // all report type should have the same value in order to be correct



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SalesView() {
        // Required empty public constructor
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
    public static SalesView newInstance(String param1, String param2) {
        SalesView fragment = new SalesView();
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
        View layoutView ;
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

                if(view.getId() == R.id.tvStartDate || view.getId() == R.id.imgStartDate){
                    flagView = 1;
                    if(reportType[0] == 0){
                        getDatePicker();
                        reportType[1] = 0;
                    }
                    else if(reportType[0] == 1){
                        getMonthPicker();
                        reportType[1] = 1;
                    }
                    else if(reportType[0] == 2){
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
                }
                else if(view.getId() == R.id.tvEndDate || view.getId() == R.id.imgEndDate){
                    flagView = 0;
                    if(reportType[0] == 0){
                        getDatePicker();
                        reportType[2] = 0;
                    }
                    else if(reportType[0] == 1){
                        getMonthPicker();
                        reportType[2] = 1;
                    }
                    else if(reportType[0] == 2){
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
                            SalesController salesController = new SalesController();
                            if(salesController.verifyReportType(reportType)){
                                if(salesController.verifyDate(startCalendar, endCalendar))
                                    tvResult.setText("IT WORKS");
                                else
                                    Toast.makeText(getActivity(), "Invalid date range", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(getActivity(), "There's something wrong with the report format entered.", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        };

        tblSalesData = (TableLayout) layoutView.findViewById(R.id.tbl_sales_data);
        tr = (TableRow) layoutView.findViewById(R.id.trRowData);

        imgStartDate = (ImageView) layoutView.findViewById(R.id.imgStartDate);
        imgStartDate.setOnClickListener(clicked);
        tvStartDate = (TextView) layoutView.findViewById((R.id.tvStartDate));
        tvStartDate.setOnClickListener(clicked);

        tvEndDate = (TextView) layoutView.findViewById((R.id.tvEndDate));
        imgEndDate =  (ImageView) layoutView.findViewById(R.id.imgEndDate);
        tvResult = (TextView) layoutView.findViewById(R.id.tvResult);

        return layoutView;
    }

    private void updateLabel(int type){
        String myFormat ="MM/dd/yyyy";

        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.TAIWAN);
        int year, month, dateOfMonth;

        year = currentCalendar.get(Calendar.YEAR);
        month = currentCalendar.get(Calendar.MONTH);
        dateOfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH);

        if(flagView == 1){
            startCalendar.set(year,month,dateOfMonth);
            if(type == 0){
                tvStartDate.setText(dateFormat.format(startCalendar.getTime()));
            }
            else if(type == 1){
                tvStartDate.setText(getMonthValue(month)+ " " + year);
            }
            else if(type == 2){
                tvStartDate.setText(year+"");
            }
        }
        else if(flagView == 0){
            endCalendar.set(year,month,dateOfMonth);
            if(type == 0){
                tvEndDate.setText(dateFormat.format(endCalendar.getTime()));
            }
            else if(type == 1){
                tvEndDate.setText(getMonthValue(month)+ " " + year);
            }
            else if(type == 2){
                tvEndDate.setText("" + year);
            }
        }
    }
    public void displayReport(){
        String[] d1 = {"Piattos", "2", "15", "30"};
        String[] d2 = {"Nova", "3", "15", "45"};
        String[] d3 = {"Mentos", "5", "3", "15"};
        String[] d4 = {"Judge", "1", "10", "10"};
        String[] d5 = {"Pancit Canton", "1", "15", "15"};
        String[][] data = {d1,d2,d3,d4,d5};

//        tblSalesData.



    }

    private void getDatePicker(){
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dateOfMonth) {
                currentCalendar.set(year, month, dateOfMonth);
              //  updateLabel("date");
                updateLabel(0);
            }
        }, thisYear, thisMonth, thisDayOfMonth);
        if(flagView == 1){
            datePicker.setMessage("Start Date");
        }
        else if (flagView == 0){
            datePicker.setMessage("End Date");
        }
        datePicker.show();

    }

    private void getMonthPicker(){
        MonthYearDialog builder = new MonthYearDialog();
        builder.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dateOfMonth) {
                currentCalendar.set(year, month, 0);
                //updateLabel("month");
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
                currentCalendar.set(year, 0, 0);
            //    updateLabel("year");
                updateLabel(2);
            }
        }, flagView);
        builder.show(getFragmentManager(), "YearPickerDialog");
    }

    public String getMonthValue(int value){
        String[] months = new String[]{"Jan", "Feb", "Mar", "April", "May", "June", "July",
                                      "Aug", "Sept", "Oct", "Nov", "Dec"};
        return months[value];
    }

    public Calendar getStartDate(){
        return startCalendar;
    }

    public Calendar getEndDate(){
        return endCalendar;
    }

    public int[] getReportType(){
        return reportType;
    }

}