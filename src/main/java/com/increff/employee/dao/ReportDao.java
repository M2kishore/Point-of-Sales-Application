package com.increff.employee.dao;

import com.increff.employee.model.data.report.BrandReportData;
import com.increff.employee.model.data.report.InventoryReportData;
import com.increff.employee.model.data.report.SalesReportData;
import com.increff.employee.model.form.ReportDateForm;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ReportDao extends AbstractDao{
    private static String salesReport = "SELECT new com.increff.employee.model.data.report.SalesReportData(brandPojo.brand,brandPojo.category,SUM(orderItemPojo.quantity),SUM(orderItemPojo.sellingPrice)) " +
            "FROM OrderPojo orderPojo " +
            "INNER JOIN OrderItemPojo orderItemPojo ON orderPojo.id=orderItemPojo.orderId " +
            "INNER JOIN ProductPojo productPojo ON productPojo.id=orderItemPojo.productId " +
            "INNER JOIN BrandPojo brandPojo ON brandPojo.id=productPojo.brandCategory " +
            "WHERE brandPojo.brand LIKE :brand " +
            "AND brandPojo.category LIKE :category " +
            "AND orderPojo.date BETWEEN :startDate AND :endDate " +
            "GROUP BY brandPojo.brand,brandPojo.category";

    private static String brandReport = "SELECT new com.increff.employee.model.data.report.BrandReportData(brandPojo.brand,SUM(orderItemPojo.quantity),SUM(orderItemPojo.sellingPrice)) " +
            "FROM OrderPojo orderPojo " +
            "INNER JOIN OrderItemPojo orderItemPojo ON orderPojo.id=orderItemPojo.orderId " +
            "INNER JOIN ProductPojo productPojo ON productPojo.id=orderItemPojo.productId " +
            "INNER JOIN BrandPojo brandPojo ON brandPojo.id=productPojo.brandCategory " +
            "WHERE brandPojo.brand LIKE :brandString " +
            "AND brandPojo.category LIKE :categoryString " +
            "AND orderPojo.date BETWEEN :startDate AND :endDate " +
            "GROUP BY brandPojo.brand";

    private static String inventoryReport = "SELECT new com.increff.employee.model.data.report.InventoryReportData(brandPojo.brand,brandPojo.category,SUM(inventoryPojo.quantity)) " +
            "FROM ProductPojo productPojo " +
            "INNER JOIN BrandPojo brandPojo ON brandPojo.id=productPojo.brandCategory " +
            "INNER JOIN InventoryPojo inventoryPojo ON inventoryPojo.id=productPojo.id " +
            "WHERE brandPojo.brand LIKE :brand " +
            "AND brandPojo.category LIKE :category " +
            "GROUP BY brandPojo.brand,brandPojo.category";

    public List<SalesReportData> getSalesReport(ReportDateForm reportDateForm, String brand, String category){
        TypedQuery<SalesReportData> query = getQuery(salesReport,SalesReportData.class);
        query.setParameter("startDate", reportDateForm.getStartDate());
        query.setParameter("endDate", reportDateForm.getEndDate());
        query.setParameter("brand", "%"+brand+"%");
        query.setParameter("category", "%"+category+"%");
        return query.getResultList();
    }
    public List<BrandReportData> getBrandReport(ReportDateForm reportDateForm, String brand, String category){
        TypedQuery<BrandReportData> query = getQuery(brandReport, BrandReportData.class);
        query.setParameter("startDate", reportDateForm.getStartDate());
        query.setParameter("endDate", reportDateForm.getEndDate());
        query.setParameter("brandString", "%"+brand+"%");
        query.setParameter("categoryString", "%"+category+"%");
        return query.getResultList();
    }
    public List<InventoryReportData> getInventoryReport(String brand, String category){
        TypedQuery<InventoryReportData> query = getQuery(inventoryReport, InventoryReportData.class);
        query.setParameter("brand", "%"+brand+"%");
        query.setParameter("category", "%"+category+"%");
        return query.getResultList();
    }
}
