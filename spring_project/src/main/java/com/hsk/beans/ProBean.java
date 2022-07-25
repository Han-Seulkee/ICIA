package com.hsk.beans;

import java.util.List;

import lombok.Data;

@Data
public class ProBean {
	private String proCode;
	private String proName;
	private String proComments;
	private String proStart;
	private String proEnd;
	private String proVisible;
	private List<ProMembersBean> proMembers;
}
