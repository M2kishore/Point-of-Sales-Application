package com.increff.employee.controller.api;

import com.increff.employee.model.data.report.BrandReportData;
import com.increff.employee.model.data.report.InventoryReportData;
import com.increff.employee.model.data.report.SalesReportData;
import com.increff.employee.model.form.ReportDateForm;
import com.increff.employee.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class ReportApiController {
    @Autowired
    private ReportService reportService;

    @ApiOperation(value = "Get Sales Report")
    @RequestMapping(path = "/api/report/sales", method = RequestMethod.POST)
    public List<SalesReportData> getSalesReport(@RequestBody ReportDateForm reportDateForm, @RequestParam String brand, @RequestParam String category){
        return reportService.getSalesReport(reportDateForm,brand,category);
    }
    @ApiOperation(value = "Get Brand Report")
    @RequestMapping(path = "/api/report/brand", method = RequestMethod.POST)
    public List<BrandReportData> getBrandReport(@RequestBody ReportDateForm reportDateForm, @RequestParam String brand, @RequestParam String category){
        return reportService.getBrandReport(reportDateForm,brand,category);
    }
    @ApiOperation(value = "Get Inventory Report")
    @RequestMapping(path = "/api/report/inventory", method = RequestMethod.POST)
    public List<InventoryReportData> getInventoryReport(@RequestParam String brand, @RequestParam String category){
        return reportService.getInventoryReport(brand,category);
    }
}
