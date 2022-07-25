package com.hsk.beans;

import java.util.List;

import lombok.Data;

@Data
public class AuthBean {
	private String message;
	private String publicIp;
	private String privateIp;
	private String pmbCode;
	private String pmbPassword;
	private String pmbName;
	private String pmbEmail;
	private String pmbLevel;
	private String pmbLevelName;
	private String pmbClass;
	private String pmbClassName;
	private int aslAction;
	private String aslDate;
}
