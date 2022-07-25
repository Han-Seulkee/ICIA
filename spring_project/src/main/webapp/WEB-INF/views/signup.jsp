<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="/resources/js/common.js"></script>
<style>@import url("/resources/css/common.css");</style>
<script>
function init() {
	getAjaxJson("https://api.ipify.org", "format=json", "setPublicIp");
	lightBoxCtl('PMS Sign Up', true);
	let body = document.getElementById("cbody");
	
	let box = [];
	box.push(createInput("text", "${pmbCode}", "pmbCode", "PMB Code", "box"));
	box[0].setAttribute("readOnly", true);
	box.push(createInput("password", null, "pmbPassword", "PMB Password", "box"));
	box.push(createInput("text", null, "pmbName", "PMB Name", "box"));
	box.push(createInput("text", null, "pmbEmail", "PMB E-Mail", "box"));
	
	box.push(createInput("hidden",null,"publicIp",null,null)); //publicIp input
	
	for(idx=0;idx<box.length;idx++){
		body.appendChild(box[idx]);
	}
	
	let origin = body.innerHTML;
	body.innerHTML = origin+"${selectData}";
	
	box.push(createInput("button","Join",null,null,"btn"));
	box.push(createInput("button","Cancel",null,null,"btn"));
	box[5].setAttribute("onclick","regCtl()");
	box[6].setAttribute("onclick","cancel()");
	
	body.appendChild(box[5]);
	body.appendChild(box[6]);
	
	
	let lightBox = document.querySelector(".light-box");
	lightBox.className = "light-box-big";
}
function regCtl() {
	document.getElementsByName("publicIp")[0].setAttribute("value", jsonData.ip); //서버로 전송할 value 설정
	/* HTML Object 연결: canvas, 회원코드, 패스워드, 회원성명, 등급, 반, form */
	let info = [];
	info.push(document.getElementsByName("pmbCode")[0]);
	info.push(document.getElementsByName("pmbPassword")[0]);
	info.push(document.getElementsByName("pmbName")[0]);
	info.push(document.getElementsByName("pmbLevel")[0]);
	info.push(document.getElementsByName("pmbClass")[0]);
	/* 유효성 검사 */
	/* 1. 패스워드 유효성 검사 >> 영문자 대소문자, 숫자, 특수문자 >> 3개이상의 타입을 사용하였는지
							>> 전체 문자 수 6개 이상 */
	if(isCharCheck(info[1].value, true)) {
		if(!isCharLengthCheck(info[1].value, 6)) {
			alert("패스워드는 6자 이상이어야 함");
			info[1].value = "";
			info[1].focus();
			return;
		}
	} else {
		alert("영문 대소문자, 숫자, 특수문자 중 3개 이상이 포함되어야 함");
		info[1].value = "";
		info[1].focus();
		return;
	}
	/* 2. 문자 수가 2~5범위, 한글 체크 */
	if(isCharCheck(info[2].value, false)) {
		if(!isCharLengthCheck(info[2].value,2,5)){
			alert("성명은 2글자 ~ 5글자 범위여야 함");
			info[2].focus();
			return;
		}
	} else {
		alert("성명은 한글로만 입력하셔야 함");
		info[2].focus();
		return;
	}
	
	/* 3. Level, Class가 선택됐는지 검사 */
	if(info[3].selectedIndex <= 0) {
		alert("회원레벨을 선택하세요");
		info[3].focus();
		return;
	}
	if(info[4].selectedIndex <= 0) {
		alert("반을 선택하세요");
		info[4].focus();
		return;
	}
	
	const canvas = document.getElementById("canvas");
	let form = document.getElementsByName("serverCall")[0];
	
	form.action = "SignUp";
	form.method = "post";
	form.appendChild(canvas);
	form.submit();
}

function cancel() {
	let result = confirm("초기화 하시겠습니까?");
	if(result){
		const accessInfo = [];
		accessInfo.push(document.getElementsByName("pmbPassword")[0]);
		accessInfo.push(document.getElementsByName("pmbName")[0]);

		document.getElementsByName("pmbLevel")[0].selectedIndex=0;
		document.getElementsByName("pmbClass")[0].selectedIndex=0;
		
		for(idx=0;idx<accessInfo.length;idx++){
			accessInfo[idx].value = "";
		}
	}
}
</script>
<style>

</style>
</head>
<body onload="init()">
	<div id="canvas" class="canvas">
		<div id="light-box" class="light-box">
			<div id="image-zone" class="lightbox image"></div>
			<div id="content-zone" class="lightbox content">
				<div id="cheader"></div>
				<div id="cbody"></div>
				<div id="cfoot">${message}</div>
			</div>
		</div>	
	</div>
	<form name="serverCall"></form>
</body>
</html>