package com.increff.employee.dao;

import com.increff.employee.model.form.ReportDateForm;
import com.increff.employee.pojo.OrderPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao{
    private static String filter_date = "SELECT orderPojo FROM OrderPojo AS orderPojo WHERE orderPojo.date BETWEEN :startDate AND :endDate ";
    @Transactional
    public int insert(OrderPojo orderPojo){
        entityManager().persist(orderPojo);
        entityManager().flush();
        return orderPojo.getId();
    }

    public List<OrderPojo> filterId(ReportDateForm reportDateForm){
        TypedQuery<OrderPojo> query = getQuery(filter_date,OrderPojo.class);
        query.setParameter("startDate", reportDateForm.getStartDate());
        query.setParameter("endDate", reportDateForm.getEndDate());
        return query.getResultList();
    }
}
