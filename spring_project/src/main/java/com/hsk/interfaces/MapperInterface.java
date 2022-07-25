package com.hsk.interfaces;

import java.util.List;

import com.hsk.beans.AulBean;
import com.hsk.beans.AuthBean;
import com.hsk.beans.ProBean;
import com.hsk.beans.ProInfoBean;
import com.hsk.beans.ProMembersBean;

public interface MapperInterface {
	public String isMember(AuthBean auth);
	public String getPmbCode();
	public List<AuthBean> getLevelList();
	public List<AuthBean> getClassList();
	public int insPmb(AuthBean auth);
	public int insAsl(AuthBean auth);
	public AuthBean getAccessInfo(AuthBean auth);
	public String isAccess(AuthBean auth);
	public String getProCode(String pmbCode);
	public int insPro(ProBean pro);
	public AuthBean getMembers(AuthBean auth);
	public int insPrm(ProBean pro);
	public int insAul(ProBean pro);
	public AulBean isPrm(ProMembersBean p);
	public List<AulBean> receivedInvitationInfo(AuthBean auth);
	public List<AulBean> sendInvitationInfo(AuthBean auth);
	public int updPrm(ProMembersBean p);
	public int updAul(AulBean aul);
	public ProInfoBean getMyProject(ProInfoBean pi);
}
