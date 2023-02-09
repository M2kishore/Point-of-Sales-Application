package com.increff.employee.service;

import com.increff.employee.dao.ReportDao;
import com.increff.employee.model.data.report.BrandReportData;
import com.increff.employee.model.data.report.InventoryReportData;
import com.increff.employee.model.data.report.SalesReportData;
import com.increff.employee.model.form.ReportDateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportDao reportDao;

    @Transactional
    public List<SalesReportData> getSalesReport(ReportDateForm reportDateForm, String brand, String category) {
        return reportDao.getSalesReport(reportDateForm, brand, category);
    }

    public List<BrandReportData> getBrandReport(ReportDateForm reportDateForm, String brand, String category) {
        return  reportDao.getBrandReport(reportDateForm,brand,category);
    }
    public List<InventoryReportData> getInventoryReport(String brand, String category){
        return reportDao.getInventoryReport(brand,category);
    }
}