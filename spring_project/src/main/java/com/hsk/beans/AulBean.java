package com.hsk.beans;

import lombok.Data;

@Data
public class AulBean {
	private String sender;
	private String senderName;
	private String receiver;
	private String receiverName;
	private String inviteDate;
	private String expireDate;
	private String authResult;
	private String authResultName;
}
