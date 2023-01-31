package com.increff.employee.dao;

import com.increff.employee.pojo.EmployeePojo;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class EmployeeDaoTest extends AbstractUnitTest {
    @Autowired
    EmployeeDao employeeDao;
    @Test
    public void testAdd() throws ApiException {
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setName("muniskanth");
        employeePojo.setAge(51);

        employeeDao.insert(employeePojo);
    }
    @Test
    public void testSelect() throws ApiException {
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setName("name");
        employeePojo.setAge(51);

        employeeDao.insert(employeePojo);
        int id = employeeDao.selectAll().get(0).getId();

        EmployeePojo addedEmployee = employeeDao.select(id);
        assertEquals("name",addedEmployee.getName());
        assertEquals(51,addedEmployee.getAge());
    }
    @Test
    public void testGetAll() throws ApiException {
        EmployeePojo employeePojo1 = new EmployeePojo();
        employeePojo1.setName("name");
        employeePojo1.setAge(51);

        employeeDao.insert(employeePojo1);

        EmployeePojo employeePojo2 = new EmployeePojo();
        employeePojo2.setName("name");
        employeePojo2.setAge(51);

        employeeDao.insert(employeePojo2);

        List<EmployeePojo> employeePojoList = employeeDao.selectAll();
        assertEquals(2,employeePojoList.size());
    }

    @Test
    public void testDelete() throws ApiException{
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setName("name");
        employeePojo.setAge(51);

        employeeDao.insert(employeePojo);
        employeeDao.delete(employeeDao.selectAll().get(0).getId());

        assertEquals(0,employeeDao.selectAll().size());
    }
    @Test
    public void testUpdate() throws ApiException {
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setName("name");
        employeePojo.setAge(51);

        employeeDao.insert(employeePojo);

        int id = employeeDao.selectAll().get(0).getId();
        EmployeePojo addedPojo = employeeDao.select(id);

        addedPojo.setName("newname");
        employeeDao.update(addedPojo);
        EmployeePojo updatedPojo = employeeDao.select(id);
        assertEquals(addedPojo.getName(), updatedPojo.getName());
    }
}
