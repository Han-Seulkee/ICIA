package com.hsk.spring_project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsk.beans.AuthBean;
import com.hsk.beans.ProBean;
import com.hsk.beans.ProInfoBean;
import com.hsk.services.DashBoard;
import com.hsk.services.Project;

@RestController
public class APIController {
	@Autowired
	private Project project;
	@Autowired
	private DashBoard dash;
	
	@SuppressWarnings("unchecked")
	@PostMapping("/RegProject")
	 public List<AuthBean> regProject(Model model, @ModelAttribute ProBean pro) {
		System.out.println(pro.getProName());
		model.addAttribute(pro);
		project.backController(0, model);
		
		return (List<AuthBean>)model.getAttribute("MemberList");
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/ShowProject")
	public List<ProInfoBean> showProject(Model model, @ModelAttribute ProInfoBean pi) {
		model.addAttribute(pi);
		dash.backController(0, model);
		
		return (List<ProInfoBean>)model.getAttribute("ProjectList");
	}
	
}