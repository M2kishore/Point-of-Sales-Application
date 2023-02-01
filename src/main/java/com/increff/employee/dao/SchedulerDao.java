package com.increff.employee.dao;
import com.increff.employee.pojo.SchedulerPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class SchedulerDao extends AbstractDao{
    private static String select_all = "select schedulerPojo from SchedulerPojo schedulerPojo";
    @Transactional
    public void insert(SchedulerPojo schedulerPojo) {
        entityManager().persist(schedulerPojo);
    }
    public List<SchedulerPojo> selectAll() {
        TypedQuery<SchedulerPojo> query = getQuery(select_all, SchedulerPojo.class);
        return query.getResultList();
    }
}
