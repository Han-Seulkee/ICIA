package com.hsk.spring_project;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hsk.beans.AuthBean;
import com.hsk.beans.ProBean;
import com.hsk.beans.ProMembersBean;
import com.hsk.services.*;

@Controller
public class HomeController {
	@Autowired
	private Authentication auth;
	@Autowired
	private Project project;
	@Autowired
	private DashBoard dash;
	@Autowired
	private Notice notice;
	@Autowired
	private Management manage;
	@Autowired
	private MyPage mypage;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		
		return "landing";
	}
	
	@RequestMapping(value = "/Landing", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest req, ModelAndView mav, @ModelAttribute AuthBean authBean) {
		authBean.setPrivateIp(req.getRemoteAddr());
		mav.addObject(authBean);
		System.out.println("pub: "+authBean.getPublicIp());
		System.out.println("pri: "+authBean.getPrivateIp());
		
		this.auth.backController(-1, mav);
		return mav;
	}
	
	@RequestMapping(value = "/Access", method = RequestMethod.POST)
	public ModelAndView access(ModelAndView mav, @ModelAttribute AuthBean authBean, HttpServletRequest req) {
		authBean.setPrivateIp(req.getRemoteAddr());
		mav.addObject(authBean);
		this.auth.backController(1, mav);
		
		return mav;
	}
	
	@RequestMapping(value = "/isMember", method = RequestMethod.POST)
	public ModelAndView isMember(ModelAndView mav, @ModelAttribute ProMembersBean pb) {
		mav.addObject(pb);
		this.dash.backController(1, mav);
		
		return mav;
	}
	
	@RequestMapping(value = "/MoveReg", method = RequestMethod.POST)
	public ModelAndView moveReg(ModelAndView mav, @ModelAttribute AuthBean authBean) {
		mav.addObject(authBean);
		this.auth.backController(0, mav);
		
		return mav;
	}
	
	@RequestMapping(value = "/SignUp", method = RequestMethod.POST)
	public ModelAndView reg(ModelAndView mav, @ModelAttribute AuthBean authBean, HttpServletRequest req) {
		authBean.setPrivateIp(req.getRemoteAddr());
		mav.addObject(authBean);
		this.auth.backController(2, mav);
		
		return mav;
	}
	
	@RequestMapping(value = "/Cancel", method = RequestMethod.POST)
	public String cancel() {
		return "index";
	}
	
	@RequestMapping(value = "/AccessOut", method = RequestMethod.POST)
	public ModelAndView logout(ModelAndView mav, @ModelAttribute AuthBean authBean) {
		mav.addObject(authBean);
		this.auth.backController(3, mav);
		
		return mav;
	}
	
	@RequestMapping(value = "/Project", method = RequestMethod.POST)
	public ModelAndView m0(ModelAndView mav) {
		this.project.backController(0, mav);
		
		return mav;
	}
	
	@RequestMapping(value = "/SendMail", method = RequestMethod.POST)
	public ModelAndView sendEmail(ModelAndView mav, @ModelAttribute ProBean pro) {
		mav.addObject(pro);
		this.project.backController(1, mav);
		
		return mav;
	}
	
	@RequestMapping(value = "/DashBoard", method = RequestMethod.POST)
	public ModelAndView m1(ModelAndView mav) {
		this.dash.backController(0, mav);
		return mav;
	}
	
	@RequestMapping(value = "/Notice", method = RequestMethod.POST)
	public ModelAndView m2(ModelAndView mav) {
		this.notice.backController(0, mav);
		
		return mav;
	}
	
	@RequestMapping(value = "/Management", method = RequestMethod.POST)
	public ModelAndView m3(ModelAndView mav) {
		this.manage.backController(0, mav);
		
		return mav;
	}
	
	@RequestMapping(value = "/MyPage", method = RequestMethod.POST)
	public ModelAndView m4(ModelAndView mav) {
		this.mypage.backController(0, mav);
		
		return mav;
	}
	
}
