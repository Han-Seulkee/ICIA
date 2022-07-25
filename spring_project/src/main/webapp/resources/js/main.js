function acceptCtl(inviteDate, proAc, expired) {
	//수락:AC 거절:RF
	console.log(proAc=="AC"?"수락":"거절");
	document.getElementById("cfoot").innerHTML="";
	document.getElementById("cbody").innerHTML="";
	
	lightBoxCtl('인증코드 입력', true);
	let cbody = document.getElementById("cbody");
	let cfoot = document.getElementById("cfoot");
	
	let code = createInput("text",null,"proCode","인증코드 입력","box");
	let btn = createInput("button","확인",null,null,"s_btn");
	let cBtn = createInput("button","취소",null,null,"s_btn");
	let accept = createHidden("proAccept",proAc);
	let invite = createHidden("inviteDate",inviteDate);
	
	btn.addEventListener("click", isMember);
	cBtn.addEventListener("click", cancel);
	
	if(expired) {
		alert("인증시간이 만료되었습니다.");
		cancel();
	}
	
	cbody.appendChild(code);
	cbody.appendChild(accept);
	cbody.appendChild(invite);
	
	cfoot.appendChild(btn);
	cfoot.appendChild(cBtn);
}

function cancel() {
	canvas.style.display = "none";
}

function isMember() {
	let form = document.getElementsByName("serverCall")[0];
	form.action = "isMember";
	form.method = "post";
	
	form.appendChild(document.getElementsByName("proCode")[0]);
	form.appendChild(document.getElementsByName("proAccept")[0]);
	form.appendChild(document.getElementsByName("inviteDate")[0]);
	
	console.log(form);
	form.submit();
}

/*function showL() {
	
}

function showR() {
	
}*/