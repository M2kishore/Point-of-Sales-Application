package com.increff.employee.service;

import com.increff.employee.pojo.EmployeePojo;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class EmployeeServiceTest extends AbstractUnitTest {
    @Autowired
    EmployeeService employeeService;

    @Test
    public void testAdd() throws ApiException {
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setName("muniskanth");
        employeePojo.setAge(51);

        employeeService.add(employeePojo);
    }
    @Test
    public void testAddEmpty() throws ApiException{
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setAge(51);

        try{
            employeeService.add(employeePojo);
        }catch (ApiException e){
            assertEquals("name cannot be empty",e.getMessage());
        }

    }
    @Test
    public void testGetSingle() throws ApiException {
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setName("name");
        employeePojo.setAge(51);

        employeeService.add(employeePojo);
        int id = employeeService.getAll().get(0).getId();

        EmployeePojo addedEmployee = employeeService.get(id);
        assertEquals("name",addedEmployee.getName());
        assertEquals(51,addedEmployee.getAge());
    }
    @Test
    public void testGetSingleNotPresent() throws ApiException{
        try {
            employeeService.get(1);
        }catch (ApiException e){
            assertEquals("Employee with given ID does not exit, id: 1",e.getMessage());
        }

    }
    @Test
    public void testGetAll() throws ApiException {
        EmployeePojo employeePojo1 = new EmployeePojo();
        employeePojo1.setName("name");
        employeePojo1.setAge(51);

        employeeService.add(employeePojo1);

        EmployeePojo employeePojo2 = new EmployeePojo();
        employeePojo2.setName("name");
        employeePojo2.setAge(51);

        employeeService.add(employeePojo2);

        List<EmployeePojo> employeePojoList = employeeService.getAll();
        assertEquals(2,employeePojoList.size());
    }

    @Test
    public void testDelete() throws ApiException{
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setName("name");
        employeePojo.setAge(51);

        employeeService.add(employeePojo);
        employeeService.delete(employeeService.getAll().get(0).getId());

        assertEquals(0,employeeService.getAll().size());
    }

    @Test
    public void testNormalize(){
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setName("Name ");
        employeePojo.setAge(51);

        employeeService.normalize(employeePojo);

        assertEquals("name",employeePojo.getName());
    }

    @Test
    public void testUpdate() throws ApiException{
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setName("name");
        employeePojo.setAge(51);

        employeeService.add(employeePojo);

        int id = employeeService.getAll().get(0).getId();
        EmployeePojo addedPojo = employeeService.get(id);

        addedPojo.setName("newname");
        employeeService.update(id,addedPojo);
        EmployeePojo updatedPojo = employeeService.get(id);
        assertEquals(addedPojo.getName(),updatedPojo.getName());
    }
}
