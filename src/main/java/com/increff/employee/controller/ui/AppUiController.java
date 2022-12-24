package com.increff.employee.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUiController extends AbstractUiController {

	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		return modelAndView("home.html");
	}

	@RequestMapping(value = "/ui/employee")
	public ModelAndView employee() {
		return modelAndView("employee.html");
	}

	@RequestMapping(value = "/ui/admin")
	public ModelAndView admin() {
		return modelAndView("user.html");
	}

}
