package com.increff.employee.service;

import com.increff.employee.dao.*;
import com.increff.employee.model.data.report.BrandReportData;
import com.increff.employee.model.data.report.InventoryReportData;
import com.increff.employee.model.data.report.SalesReportData;
import com.increff.employee.model.form.ReportDateForm;
import com.increff.employee.pojo.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReportServiceTest extends AbstractUnitTest{
    @Autowired
    private ReportService reportService;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Test
    public void testSalesReport(){
        setup();
        Date today = new Date();
        ReportDateForm reportDateForm = new ReportDateForm();
        Date startDate = new Date();
        startDate.setDate(today.getDate()-1);
        Date endDate = new Date();
        endDate.setDate(today.getDate()+1);
        reportDateForm.setStartDate(startDate.getTime());
        reportDateForm.setEndDate(endDate.getTime());
        //checking size
        List<SalesReportData> salesReportDataList = reportService.getSalesReport(reportDateForm,"","");
        assertEquals(2,salesReportDataList.size());
        //checking actual data
        SalesReportData testSalesReportData1 = new SalesReportData("nike","shoe",10,200);
        SalesReportData testSalesReportData2 = new SalesReportData("puma","shoe",6,400);
        SalesReportData data1 = salesReportDataList.get(0);
        SalesReportData data2 = salesReportDataList.get(1);

        assertEquals(testSalesReportData1.getBrand(),data1.getBrand());
        assertEquals(testSalesReportData1.getCategory(),data1.getCategory());
        assertEquals(testSalesReportData1.getQuantity(),data1.getQuantity());
        assertEquals(testSalesReportData1.getRevenue(),data1.getRevenue(),0.01);

        assertEquals(testSalesReportData2.getBrand(),data2.getBrand());
        assertEquals(testSalesReportData2.getCategory(),data2.getCategory());
        assertEquals(testSalesReportData2.getQuantity(),data2.getQuantity());
        assertEquals(testSalesReportData2.getRevenue(),data2.getRevenue(),0.01);

        //checking size
        List<SalesReportData> salesReportDataList1 = reportService.getSalesReport(reportDateForm,"","");
        assertEquals(2,salesReportDataList1.size());
        //checking actual data
        data1 = salesReportDataList.get(0);

        assertEquals(testSalesReportData1.getBrand(),data1.getBrand());
        assertEquals(testSalesReportData1.getCategory(),data1.getCategory());
        assertEquals(testSalesReportData1.getQuantity(),data1.getQuantity());
        assertEquals(testSalesReportData1.getRevenue(),data1.getRevenue(),0.01);

    }
    @Test
    public void testBrandReport(){
        setup();
        Date today = new Date();
        ReportDateForm reportDateForm = new ReportDateForm();
        Date startDate = new Date();
        startDate.setDate(today.getDate()-1);
        Date endDate = new Date();
        endDate.setDate(today.getDate()+1);
        reportDateForm.setStartDate(startDate.getTime());
        reportDateForm.setEndDate(endDate.getTime());
        //checking size
        List<BrandReportData> brandReportDataList = reportService.getBrandReport(reportDateForm,"","");
        assertEquals(2,brandReportDataList.size());
        //checking actual data
        BrandReportData testBrandReportData1 = new BrandReportData("nike",10,200);
        BrandReportData testBrandReportData2 = new BrandReportData("puma",6,400);
        BrandReportData data1 = brandReportDataList.get(0);
        BrandReportData data2 = brandReportDataList.get(1);

        assertEquals(testBrandReportData1.getBrand(),data1.getBrand());
        assertEquals(testBrandReportData1.getQuantity(),data1.getQuantity());
        assertEquals(testBrandReportData1.getRevenue(),data1.getRevenue(),0.01);

        assertEquals(testBrandReportData2.getBrand(),data2.getBrand());
        assertEquals(testBrandReportData2.getQuantity(),data2.getQuantity());
        assertEquals(testBrandReportData2.getRevenue(),data2.getRevenue(),0.01);

        //check filter
        List<BrandReportData> brandReportDataList1 = reportService.getBrandReport(reportDateForm,"n","");
        assertEquals(1,brandReportDataList1.size());
        //checking actual data
        data1 = brandReportDataList1.get(0);
        assertEquals(testBrandReportData1.getBrand(),data1.getBrand());
        assertEquals(testBrandReportData1.getQuantity(),data1.getQuantity());
        assertEquals(testBrandReportData1.getRevenue(),data1.getRevenue(),0.01);

    }

    @Test
    public void testInventoryReport(){
        setup();
        //checking size
        List<InventoryReportData> inventoryReportDataList = reportService.getInventoryReport("","");
        assertEquals(2,inventoryReportDataList.size());
        //checking actual data
        InventoryReportData testInventoryReportData1 = new InventoryReportData("nike","shoe",5);
        InventoryReportData testInventoryReportData2 = new InventoryReportData("puma","shoe",5);
        InventoryReportData data1 = inventoryReportDataList.get(0);
        InventoryReportData data2 = inventoryReportDataList.get(1);

        assertEquals(testInventoryReportData1.getBrand(),data1.getBrand());
        assertEquals(testInventoryReportData1.getCategory(),data1.getCategory());
        assertEquals(testInventoryReportData1.getQuantity(),data1.getQuantity());

        assertEquals(testInventoryReportData2.getBrand(),data2.getBrand());
        assertEquals(testInventoryReportData2.getCategory(),data2.getCategory());
        assertEquals(testInventoryReportData2.getQuantity(),data2.getQuantity());

        //check filter
        List<InventoryReportData> inventoryReportDataList1 = reportService.getInventoryReport("n","");
        assertEquals(1,inventoryReportDataList1.size());
        //checking actual data
        data1 = inventoryReportDataList1.get(0);
        assertEquals(testInventoryReportData1.getBrand(),data1.getBrand());
        assertEquals(testInventoryReportData1.getCategory(),data1.getCategory());
        assertEquals(testInventoryReportData1.getQuantity(),data1.getQuantity());

    }

    private void setup(){
        BrandPojo brandPojo1 = new BrandPojo();
        brandPojo1.setBrand("nike");
        brandPojo1.setCategory("shoe");
        brandDao.insert(brandPojo1);
        BrandPojo brandPojo2 = new BrandPojo();
        brandPojo2.setBrand("puma");
        brandPojo2.setCategory("shoe");
        brandDao.insert(brandPojo2);

        ProductPojo productPojo1 = new ProductPojo();
        productPojo1.setName("sparkle");
        productPojo1.setBarcode("qwe123");
        productPojo1.setBrandCategory(brandPojo1.getId());
        productPojo1.setMrp(100.25);
        productDao.insert(productPojo1);
        ProductPojo productPojo2 = new ProductPojo();
        productPojo2.setName("dirt");
        productPojo2.setBarcode("asd123");
        productPojo2.setBrandCategory(brandPojo2.getId());
        productPojo2.setMrp(200.50);
        productDao.insert(productPojo2);

        InventoryPojo inventoryPojo1 = new InventoryPojo();
        inventoryPojo1.setId(productPojo1.getId());
        inventoryPojo1.setQuantity(5);
        inventoryDao.insert(inventoryPojo1);
        InventoryPojo inventoryPojo2 = new InventoryPojo();
        inventoryPojo2.setId(productPojo2.getId());
        inventoryPojo2.setQuantity(5);
        inventoryDao.insert(inventoryPojo2);

        OrderPojo orderPojo1 = new OrderPojo();
        orderPojo1.setDate(new Date());
        int orderId1 = orderDao.insert(orderPojo1);
        OrderPojo orderPojo2 = new OrderPojo();
        orderPojo2.setDate(new Date());
        int orderId2 = orderDao.insert(orderPojo2);

        OrderItemPojo orderItemPojo1 = new OrderItemPojo();
        orderItemPojo1.setOrderId(orderId1);
        orderItemPojo1.setProductId(productPojo1.getId());
        orderItemPojo1.setSellingPrice(100);
        orderItemPojo1.setQuantity(5);
        orderItemDao.insert(orderItemPojo1);
        OrderItemPojo orderItemPojo2 = new OrderItemPojo();
        orderItemPojo2.setOrderId(orderId1);
        orderItemPojo2.setProductId(productPojo2.getId());
        orderItemPojo2.setSellingPrice(200);
        orderItemPojo2.setQuantity(3);
        orderItemDao.insert(orderItemPojo2);
        OrderItemPojo orderItemPojo3 = new OrderItemPojo();
        orderItemPojo3.setOrderId(orderId2);
        orderItemPojo3.setProductId(productPojo1.getId());
        orderItemPojo3.setSellingPrice(100);
        orderItemPojo3.setQuantity(5);
        orderItemDao.insert(orderItemPojo3);
        OrderItemPojo orderItemPojo4 = new OrderItemPojo();
        orderItemPojo4.setOrderId(orderId2);
        orderItemPojo4.setProductId(productPojo2.getId());
        orderItemPojo4.setSellingPrice(200);
        orderItemPojo4.setQuantity(3);
        orderItemDao.insert(orderItemPojo4);
    }
}
