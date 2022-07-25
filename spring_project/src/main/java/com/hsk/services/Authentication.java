package com.hsk.services;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.hsk.beans.AulBean;
import com.hsk.beans.AuthBean;
import com.hsk.beans.ProBean;
import com.hsk.beans.ProMembersBean;
import com.hsk.interfaces.ServiceRule;
import com.hsk.utils.Encryption;
import com.hsk.utils.ProjectUtils;

@Service
public class Authentication implements ServiceRule {
	@Autowired
	private Encryption enc;
	@Autowired
	private SqlSessionTemplate session;
	@Autowired
	private ProjectUtils pu;
	@Autowired
	private DashBoard dashBoard;
	
	public Authentication() {
	}

	public void backController(int serviceCode, ModelAndView mav) {
		switch(serviceCode) {
		case -1: this.isFirstPage(mav); break;
		case 0: this.joinFormCtl(mav); break;
		case 1: this.accessCtl(mav); break;
		case 2: this.joinCtl(mav); break;
		case 3: this.logoutCtl(mav); break;
		default:
		}
	}
	
	public void backController(int serviceCode, Model model) {
		
	}
	
	/* 첫 페이지 전달 */
	private void isFirstPage(ModelAndView mav) {
		try {
			if(this.pu.getAttribute("accessInfo") != null) {
				/* DB로그인 기록 확인 */
				AuthBean a = (AuthBean)this.session.selectList("getAccessInfo", this.pu.getAttribute("accessInfo")).get(0);
				a.setPmbName(this.enc.aesDecode(a.getPmbName(), a.getPmbCode()));
				a.setPmbEmail(this.enc.aesDecode(a.getPmbEmail(), a.getPmbCode()));
				this.pu.setAttribute("accessInfo", a);
				this.dashBoard.backController(0, mav);
			} else {
				mav.setViewName("index");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* 회원가입 */
	@Transactional(propagation=Propagation.REQUIRED)
	private void joinCtl(ModelAndView mav) {
		String page = "signup", message="회원 가입 실패";
		AuthBean auth = ((AuthBean)mav.getModel().get("authBean"));
		
		/* 1. 유효한 pmbCode 생성: 저장되어있는 pmbCode조회 */
		auth.setPmbCode(this.session.selectOne("getPmbCode"));
		
		/* 2. DATA ENCRYPTION: password >> shadow 	name >> des */
		try {
			auth.setPmbName(this.enc.aesEncode(auth.getPmbName(), auth.getPmbCode()));
			auth.setPmbPassword(this.enc.encode(auth.getPmbPassword()));
			auth.setPmbEmail(this.enc.aesEncode(auth.getPmbEmail(), auth.getPmbCode()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* 3. PMB insert >> 결과 확인 */
		/* 4. 가입성공 >> index.jsp <<message	가입실패 >> signup.jsp << message */
		if(this.convertToBool(this.session.insert("insPmb", auth))) {
			page = "index";
			message = "회원 가입 성공\\n회원코드: "+auth.getPmbCode();
		}
		
		mav.addObject("message",message);
		mav.setViewName(page);
	}
	
	/* 회원가입 폼 제어 */
	@Transactional(readOnly = true) //method안에 select만 있는 경우
	private void joinFormCtl(ModelAndView mav) {
		String page = "signup";
		
		/* 마지막 회원코드 검색+1 */
		mav.addObject("pmbCode", this.session.selectOne("getPmbCode"));
		/* MemberLevelCode + Name 검색 >> bean >> List */
		/* MemberClassCode + Name 검색 >> bean >> List */
		mav.addObject("selectData", 
					  this.makeSelecetHtml(this.session.selectList("getLevelList"), true, "pmbLevel")
					+ this.makeSelecetHtml(this.session.selectList("getClassList"), false, "pmbClass"));
		
		mav.setViewName(page);
	}
	
	/* select 만들기 */
	private String makeSelecetHtml(List<AuthBean> list, boolean type, String objName) {
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='"+objName+"' class='box'>");
		sb.append("<option disabled selected>"+objName.substring(3)+" 선택</option>");
		for(AuthBean auth:list) {
			sb.append("<option value='"+(type?auth.getPmbLevel():auth.getPmbClass())+"'>"+(type?auth.getPmbLevelName():auth.getPmbClassName())+"</option>");
		}
		sb.append("</select>");
		
		return sb.toString();
	}
	
	/* 로그인 제어 */
	private void accessCtl(ModelAndView mav) {
		AuthBean auth = (AuthBean)mav.getModel().get("authBean");
		try {
			if(this.pu.getAttribute("accessInfo") != null) {
			/* 세션 O: insAsl안함 */
				AuthBean a = (AuthBean) this.session.selectList("getAccessInfo", auth).get(0);
				a.setPmbName(this.enc.aesDecode(a.getPmbName(), a.getPmbCode()));
				a.setPmbEmail(this.enc.aesDecode(a.getPmbEmail(), a.getPmbCode()));
				this.pu.setAttribute("accessInfo", a);
				
				this.dashBoard.backController(0, mav);
			} else {
			/* 세션 X: insAsl해야함 */
				this.insAccessCtl(mav);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* 로그인 기록 */
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	private void insAccessCtl(ModelAndView mav) {
		AuthBean auth = (AuthBean)mav.getModel().get("authBean");
		String page = "index";
		
		/* 입력한 코드 존재하면 비밀번호 가져옴 */
		/* 입력한 비밀번호가 맞으면 true */
		String encPassword = this.session.selectOne("isMember", auth);
		Boolean isMatche = this.enc.matches(auth.getPmbPassword(), encPassword);
				
		/* 코드, 비밀번호 맞음: ip정보 가져오기 */
		if(isMatche) {
			String ipInfo = this.session.selectOne("isAccess", auth);
			if(ipInfo != null) { /* ip정보가 있으면 로그인 되어있는것임 -> 강제 로그아웃 */
				auth.setAslAction(-1);
				this.session.insert("insAsl", auth);
			}
			auth.setAslAction(1);
			if(this.convertToBool(this.session.insert("insAsl",auth))) {
				AuthBean a = (AuthBean)this.session.selectList("getAccessInfo", auth).get(0);
				/* Session에 로그인 정보 생성 */
				try {
					a.setPmbName(this.enc.aesDecode(a.getPmbName(), a.getPmbCode()));
					a.setPmbEmail(this.enc.aesDecode(a.getPmbEmail(), a.getPmbCode()));
					this.pu.setAttribute("accessInfo", a);
					
					this.dashBoard.backController(0, mav);
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
						| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
						| BadPaddingException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			mav.setViewName(page);
		}
		
	}
	
	/* 로그아웃 제어 */
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	private void logoutCtl(ModelAndView mav) {
		String page = "main";
		AuthBean auth = (AuthBean)mav.getModel().get("authBean");
		try {
			AuthBean ab = ((AuthBean)this.pu.getAttribute("accessInfo"));
			if (ab != null) {
				auth.setPublicIp(((String)this.session.selectOne("isAccess", auth)).split(":")[0]);
				auth.setPrivateIp(((String)this.session.selectOne("isAccess", auth)).split(":")[1]);
				
				String ipInfo = (String)this.session.selectOne("isAccess", auth);
				if (ipInfo != null) {
					auth.setAslAction(-1);
					if (this.convertToBool(this.session.insert("insAsl", auth))) {
						this.pu.removeAttribute("accessInfo");
						page = "redirect:/";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mav.setViewName(page);
	}
	
	private boolean convertToBool(int value) {
		return value == 0 ? false : true;
	}
}

/* Transactional
 * - method
 * 	--> readOnly로 설정한 경우 해당 메서드에 dml구문
 * - class
 * - interface
 * 
 * --readOnly Select -> 최적화된 런타임 보장
 * 
 * *** Transaction Propagation >> 전파
 *  -- REQUIRED >> 부모 트랜잭션이 존재할 경우 부모 트랜잭션에 합류, 존재하지 않으면 새로운 트랜잭션 생성
 *  			>> 부모객체(TABLE)와 자식객체(TABLE) 사이의 데이터 일관성을 유지
 *  -- REQUIRED_NEW >> 무조건 새로운 트랜잭션 생성
 *  -- MANDATORY >> 무조건 부모트랜잭션에 합류
 *  -- SUPPORTS >> 트랜잭션이 불필요 하지만 부모트랜잭션이 있다면 합류
 *  -- NESTED >> 부모트랜잭션이 존재할 경우 부모트랜잭션에 중첩, 존재하지 않으면 새로운 트랜잭션 생성
 *  -- NEVER >> 트랜잭션을 사용하지 않음
 *  
 * *** Isolation
 * 	-- READ_UNCOMMITED	(LEVEL 0)
 * 	-- READ_COMMITED	(LEVEL 1) >> DEFAULT
 * 	-- REPEATABLE_READ	(LEVEL 2) //다른 트랜잭션에 대해 commit한것만 볼수있음
 * 	-- SERIALIZABLE		(LEVEL 3) //전체 테이블 lock - 조심해야함
 * 
 * */
