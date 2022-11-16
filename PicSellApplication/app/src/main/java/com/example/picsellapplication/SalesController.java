package com.example.rd_picsell;

import java.util.Calendar;

public class SalesController {
    private boolean dateStatus;
    private boolean reportTypeStatus; // true if correct, false if wrong


    public SalesController(){
        dateStatus = false;
        reportTypeStatus = false;
    }

    public boolean verifyReportType(int[] reportType){
        // reportType[0] is the report type chosen from the dropdown
        // reportType[1] is the report type chosen from the startDate
        // reportType[2] is the report type chosen from the endDate

        if(reportType[0] == reportType[1] && reportType[0] == reportType [2])
            reportTypeStatus = true;
        else
            reportTypeStatus = false;

        return  reportTypeStatus;
    }

    public boolean verifyDate(Calendar startDate, Calendar endDate){

        if(endDate.getTimeInMillis() >= startDate.getTimeInMillis())
            dateStatus = true;
        else
            dateStatus = false;

        return dateStatus;
    }

    public boolean getDateStatus(){
        return dateStatus;
    }

    public boolean getReportTypeStatus(){
        return reportTypeStatus;
    }
}
