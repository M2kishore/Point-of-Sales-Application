package com.increff.employee.model.form;

import com.increff.employee.util.DateUtil;

import java.util.Date;

public class ReportDateForm {
    private long startDate;
    private long endDate;

    public Date getStartDate() {
        return DateUtil.MillisecondToDate(startDate);
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return DateUtil.MillisecondToDate(endDate);
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
}
