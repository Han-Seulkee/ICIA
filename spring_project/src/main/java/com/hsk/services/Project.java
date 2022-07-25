package com.hsk.services;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
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
public class Project implements ServiceRule {
	@Autowired
	private SqlSessionTemplate session;
	@Autowired
	private ProjectUtils pu;
	@Autowired
	private Encryption enc;
	@Autowired
	private JavaMailSenderImpl mail;
	@Autowired
	private DashBoard dashBoard;

	public Project() {
	}

	public void backController(int serviceCode, ModelAndView mav) {
		try {
			if (this.pu.getAttribute("accessInfo") != null) {
				switch (serviceCode) {
				case 0:
					this.entrance(mav);
					break;
				case 1:
					this.regProjectMemCtl(mav);
					break;
				default:
				}
			} else {
				mav.setViewName("index");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void entrance(ModelAndView mav) {

		mav.setViewName("newProject");
	}

	public void backController(int serviceCode, Model model) {
		try {
			if (this.pu.getAttribute("accessInfo") != null) {
				switch (serviceCode) {
				case 0:
					this.regProject(model);
					break;
				// case 1: this.regProjectMemCtl(model); break;
				default:
				}
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void regProjectMemCtl(ModelAndView mav) {
		//int code = (int)((Math.random()*10000)%10);
		try {
			ProBean pro = (ProBean) mav.getModel().get("proBean");
			AuthBean auth = (AuthBean) this.pu.getAttribute("accessInfo");
			
			/* 프로젝트 생성자 정보 */
			if(pro.getProMembers() == null) {
				System.out.println("null");
				
				List<ProMembersBean> empty = new ArrayList<ProMembersBean>();
				ProMembersBean pm = new ProMembersBean();
				pm.setPmbCode(auth.getPmbCode());
				pm.setPmbEmail(auth.getPmbEmail());
				pm.setProAccept("AC");
				pm.setProPosition("MG");
				empty.add(pm);
				pro.setProMembers(empty);
			} else {
				ProMembersBean pm = new ProMembersBean();
				pm.setPmbCode(auth.getPmbCode());
				pm.setPmbEmail(auth.getPmbEmail());
				pm.setProAccept("AC");
				pm.setProPosition("MG");
				pro.getProMembers().add(pm);
			}

			/* PROJECTMEMBERS에 데이터 INS */
			int result = this.session.insert("insPrm", pro);

			/* SEND MAIL: subject, content, sender, receiver */
			String subject = "[초대장] 프로젝트 참여 초대"; // 제목
			String content = ""; // 내용
			String sender = "trsg_h@naver.com"; // 보내는사람

			// javax.mail context-support
			MimeMessage mime = mail.createMimeMessage();
			MimeMessageHelper mailHelper = new MimeMessageHelper(mime, "UTF-8");
			AulBean log = null;
			for (int idx = 0; idx < result; idx++) {
				if(!pro.getProMembers().get(idx).getPmbCode().equals(auth.getPmbCode())) {
					content = this.makeHtml(this.enc.aesEncode((pro.getProCode()),
							pro.getProMembers().get(idx).getPmbEmail()));
	
					mailHelper.setFrom(sender);
					mailHelper.setTo(pro.getProMembers().get(idx).getPmbEmail());
					mailHelper.setSubject(subject);
					mailHelper.setText(content, true);
	
					mail.send(mime);
					
					log = new AulBean();
					log.setSender(auth.getPmbCode());
					log.setReceiver(pro.getProMembers().get(idx).getPmbCode());
					log.setAuthResult("NA");
					this.session.insert("insAul", log);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.dashBoard.backController(0, mav);
	}
	
	private String makeHtml(String code) {
		StringBuffer sb = new StringBuffer();
		sb.append("<table>");
		sb.append("<tr>");
		sb.append("<td><h2>프로젝트 초대장</h2></td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td>프로젝트에 참가하려면 링크로 가서 로그인 후에 인증코드를 입력하셈</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td><a href='http://192.168.0.41'>http://192.168.0.41</a></td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td>" + code + "</td>");
		sb.append("</tr>");
		sb.append("</table>");

		return sb.toString();
	}

	private void regProject(Model model) {
		List<AuthBean> memberList = new ArrayList<AuthBean>();
		ProBean pro = (ProBean) model.getAttribute("proBean");
		AuthBean auth = null;
		try {
			auth = (AuthBean) this.pu.getAttribute("accessInfo");
			if (auth != null) {
				// 세션에서 정보 가져오기
				String pmbCode = auth.getPmbCode();
				pro.setProCode(this.session.selectOne("getProCode", pmbCode));
				pro.setProVisible(pro.getProVisible().equals("공개") ? "T" : "F");
				
				if (this.convertToBool(this.session.insert("insPro", pro))) {
					memberList = this.session.selectList("getMembers", auth);
					if(memberList.size()!=0) {
						memberList.get(0).setMessage(pro.getProCode());
						for (AuthBean ab : memberList) {
							ab.setPmbName(this.enc.aesDecode(ab.getPmbName(), ab.getPmbCode()));
							ab.setPmbEmail(this.enc.aesDecode(ab.getPmbEmail(), ab.getPmbCode()));
						}
					} else {
						memberList = new ArrayList<AuthBean>();
						auth = new AuthBean();
						memberList.add(auth);
						memberList.get(0).setMessage(pro.getProCode());
					}
				}

			} else {
				memberList = new ArrayList<AuthBean>();
				auth = new AuthBean();
				auth.setMessage("프로젝트 생성 실패");
				memberList.add(auth);
			}
			model.addAttribute("MemberList", memberList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean convertToBool(int value) {
		return value == 0 ? false : true;
	}

}
