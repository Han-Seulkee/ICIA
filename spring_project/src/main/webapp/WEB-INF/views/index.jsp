<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Access Page</title>
<script src="/resources/js/common.js"></script>
<style>@import url("/resources/css/common.css");</style>
<script>
function init() {
	getAjaxJson("https://api.ipify.org", "format=json", "setPublicIp");
	lightBoxCtl('PMS Access', true);
	let cbody = document.getElementById("cbody");
	let cfoot = document.getElementById("cfoot");
	
	let box = [];
	box.push(createInput("text",null,"pmbCode","회원코드","box"));
	box.push(createInput("password",null,"pmbPassword","패스워드","box"));
	box.push(createInput("hidden",null,"publicIp",null,null)); //publicIp input
	
	let accessBtn = createInput("button","Access",null,null,"s_btn");
	let joinBtn = createInput("button","Sign Up",null,null,"s_btn");
	accessBtn.setAttribute("onclick","accessCtl()");
	joinBtn.setAttribute("onclick","regCtl()");
	
	for(idx=0;idx<box.length;idx++){
		cbody.appendChild(box[idx]);
	}
	cfoot.appendChild(accessBtn);
	cfoot.appendChild(joinBtn);
	
	const message = "${message}";
	if(message != "") alert(message);
}
function accessCtl() {
	//let pubIp = createInput("hidden",null,"publicIp",null,null);
	//pubIp.setAttribute("value", jsonData.ip);
	document.getElementsByName("publicIp")[0].setAttribute("value", jsonData.ip); //서버로 전송할 value 설정
	
	const accessInfo = [];
	accessInfo.push(document.getElementsByName("pmbCode")[0]);
	accessInfo.push(document.getElementsByName("pmbPassword")[0]);
	
	//유효성 검사
	if(accessInfo[0].value.length == 0) {
		accessInfo[0].focus();
		return;
	}
	if(accessInfo[1].value.length == 0) {
		accessInfo[1].focus();
		return;
	}
	/* Server 요청: form개체에 해당개체를 자식으로 편입 */
	let form = document.getElementsByName("serverCall")[0];
	form.action = "Access";
	form.method = "post";
	
	const dataDiv = document.getElementById("canvas");
	//dataDiv.appendChild(pubIp);
	form.appendChild(dataDiv);
	form.submit();
}
function regCtl() {
	let form = document.getElementsByName("serverCall")[0];
	form.action = "MoveReg";
	form.method = "post";
	form.submit();
}

</script>
</head>
<body onload="init()">
	<div id="canvas" class="canvas">
		<div id="light-box" class="light-box">
			<div id="image-zone" class="lightbox image"></div>
			<div id="content-zone" class="lightbox content">
				<div id="cheader"></div>
				<div id="cbody"></div>
				<div id="cfoot"></div>
			</div>
		</div>	
	</div>
	<form name="serverCall"></form>
</body>
</html>