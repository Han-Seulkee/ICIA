package com.hsk.interfaces;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public interface ServiceRule {
	public void backController(int serviceCode, ModelAndView mav);
	public void backController(int serviceCode, Model model);
}
