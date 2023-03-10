package com.increff.employee.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.data.EmployeeData;
import com.increff.employee.model.form.EmployeeForm;
import com.increff.employee.pojo.EmployeePojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping(path = "/api/employee")
public class EmployeeApiController {

	@Autowired
	private EmployeeService employeeService;

	@ApiOperation(value = "Adds an employee")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public void add(@RequestBody EmployeeForm form) throws ApiException {
		EmployeePojo p = convert(form);
		employeeService.add(p);
	}

	
	@ApiOperation(value = "Deletes an employee")
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	// /api/1
	public void delete(@PathVariable int id) {
		employeeService.delete(id);
	}

	@ApiOperation(value = "Gets an employee by ID")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public EmployeeData get(@PathVariable int id) throws ApiException {
		EmployeePojo p = employeeService.get(id);
		return convert(p);
	}

	@ApiOperation(value = "Gets list of all employees")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<EmployeeData> getAll() {
		List<EmployeePojo> list = employeeService.getAll();
		List<EmployeeData> list2 = new ArrayList<EmployeeData>();
		for (EmployeePojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

	@ApiOperation(value = "Updates an employee")
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody EmployeeForm f) throws ApiException {
		EmployeePojo p = convert(f);
		employeeService.update(id, p);
	}
	

	private static EmployeeData convert(EmployeePojo p) {
		EmployeeData d = new EmployeeData();
		d.setAge(p.getAge());
		d.setName(p.getName());
		d.setId(p.getId());
		return d;
	}

	private static EmployeePojo convert(EmployeeForm f) {
		EmployeePojo p = new EmployeePojo();
		p.setAge(f.getAge());
		p.setName(f.getName());
		return p;
	}

}
