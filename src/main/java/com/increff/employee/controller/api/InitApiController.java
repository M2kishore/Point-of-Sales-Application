package com.increff.employee.controller.api;

import java.util.List;

import com.increff.employee.controller.ui.AbstractUiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.increff.employee.model.data.InfoData;
import com.increff.employee.model.form.UserForm;
import com.increff.employee.pojo.UserPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.UserService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(path = "/site/init")
public class InitApiController extends AbstractUiController {

	@Autowired
	private UserService service;
	@Autowired
	private InfoData info;

	@ApiOperation(value = "Initializes application")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public ModelAndView showPage(UserForm form) throws ApiException {
		info.setMessage("");
		return modelAndView("init.html");
	}

	@ApiOperation(value = "Initializes application")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public ModelAndView initSite(UserForm form) throws ApiException {
		List<UserPojo> list = service.getAll();
		if (list.size() > 0) {
			info.setMessage("Application already initialized. Please use existing credentials");
		} else {
			form.setRole("admin");
			UserPojo p = convert(form);
			service.add(p);
			info.setMessage("Application initialized");
		}
		return modelAndView("init.html");

	}

	private static UserPojo convert(UserForm f) {
		UserPojo p = new UserPojo();
		p.setEmail(f.getEmail());
		p.setRole(f.getRole());
		p.setPassword(f.getPassword());
		return p;
	}

}
