package com.increff.employee.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SiteUiController extends AbstractUiController {

	// WEBSITE PAGES
	@RequestMapping(value = "")
	public ModelAndView index() {
		return modelAndView("index.html");
	}

	@RequestMapping(value = "/site/login")
	public ModelAndView login() {
		return modelAndView("login.html");
	}

	@RequestMapping(value = "/site/logout")
	public ModelAndView logout() {
		return modelAndView("logout.html");
	}

	@RequestMapping(value = "/site/pricing")
	public ModelAndView pricing() {
		return modelAndView("pricing.html");
	}

	@RequestMapping(value = "/site/features")
	public ModelAndView features() {
		return modelAndView("features.html");
	}

}
