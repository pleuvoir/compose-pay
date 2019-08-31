package io.github.pleuvoir.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页
 */
@Controller
@RequestMapping("/home")
public class HomeController {
	
	@RequestMapping("home")
	public ModelAndView index(){
		ModelAndView v = new ModelAndView("home/index");
		return v;
	}
	
}
