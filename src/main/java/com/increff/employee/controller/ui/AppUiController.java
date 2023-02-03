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
	@RequestMapping(value = "/ui/brand")
	public ModelAndView brand() {
		return modelAndView("brand.html");
	}
	@RequestMapping(value = "/ui/product")
	public ModelAndView product() {
		return modelAndView("product.html");
	}
	@RequestMapping(value = "/ui/inventory")
	public ModelAndView inventory() {
		return modelAndView("inventory.html");
	}
	@RequestMapping(value = "/ui/order/create")
	public ModelAndView addOrder() {
		return modelAndView("addOrder.html");
	}
	@RequestMapping(value = "/ui/order")
	public ModelAndView order() {
		return modelAndView("order.html");
	}
	@RequestMapping(value = "/ui/report")
	public ModelAndView report() {
		return modelAndView("report.html");
	}
	@RequestMapping(value = "/ui/scheduler")
	public ModelAndView scheduler() {
		return modelAndView("scheduler.html");
	}
}
