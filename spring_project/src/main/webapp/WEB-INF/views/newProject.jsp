<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Project</title>
<script src="https://use.fontawesome.com/releases/v6.1.1/js/all.js"></script>
<script src="/resources/js/common.js"></script>
<style>@import url("/resources/css/common.css");</style>
<style>@import url("/resources/css/main.css");</style>
<script>
function init() {
	document.getElementById("contents").style.display = "none";
	document.getElementById("memberInfo").innerText="";
	document.getElementById("prmMemberInfo").innerText="";
	document.getElementById("cfoot").innerHTML="";
	document.getElementById("cbody").innerHTML="";
	
	lightBoxCtl('New Project', true);
	let cfoot = document.getElementById("cfoot");
	let cbody = document.getElementById("cbody");
	
	let o = createInput("button", "프로젝트 등록", null, null, "s_btn");
	let x = createInput("button", "등록 취소", null, null, "s_btn");
	o.addEventListener("click", regProject);
	x.addEventListener("click", cancel);
	cfoot.appendChild(o);
	cfoot.appendChild(x);
	
	let box = [];
	box.push(createInput("text",null,"proInfo","Project Name","box"));
	box.push(createTextArea("proInfo", 3, 30, "box"));
	box.push(createInput("date", null, "proInfo", "프로젝트 시작", "box"));
	box.push(createInput("date", null, "proInfo", "프로젝트 종료", "box"));
	box.push(createInput("button", "공개", "proInfo", null, "togglebtn"));
	box[4].addEventListener("click", toggle);
	
	for(idx=0;idx<box.length;idx++){
		cbody.appendChild(box[idx]);
	}
}

function toggle() {
	let visible = document.getElementsByName("proInfo")[4];
	if(visible.value == "공개") {
		visible.value = "비공개";
		visible.style.backgroundColor = "gray";
	} else {
		visible.value = "공개";
		visible.style.backgroundColor = "#5D8C95";
	}
}

function regProject() {
	const dataName = ["proName", "proComments", "proStart", "proEnd", "proVisible"];
	
	let formData = document.getElementsByName("proInfo");
	let clientData = "";
	
	for(idx=0;idx<formData.length;idx++) {
		clientData += (idx==0?"":"&");
		clientData += (dataName[idx] + "=" + formData[idx].value);
	}
	postAjaxJson("RegProject", clientData, "callBack");
}

function callBack(ajaxData) {
	const memberList = JSON.parse(ajaxData); //parsing하면 배열로 인식
	console.log(memberList[0]);
	console.log(memberList[0].message);
	if(memberList[0].message != "프로젝트 생성 실패" && memberList.length!=1) {
		document.getElementById("contents").style.display = "flex";
		/*등록정보 복사*/
		const formData = document.getElementsByName("proInfo");
		let projectInfo = document.getElementById("projectInfo");
		let child = projectInfo.childNodes;
		
		child[3].innerText = "Code: "+memberList[0].message;
		child[5].innerText = "Name: "+formData[0].value;
		child[11].innerText = "Project Comment: "+formData[1].value;
		child[7].innerText = "Peoriod: "+formData[2].value + '~' + formData[3].value;
		child[9].innerText = "Share: "+formData[4].value;
		
		/*전체 멤버 출력*/
		let memInfo = document.getElementById("memberInfo");
		
		for(idx=0;idx<memberList.length;idx++) {
			mi = "<span class='s_text'>"+memberList[idx].pmbClassName+"</span></br>";
			mi += "<span class='n_text'>"+memberList[idx].pmbName+"["+memberList[idx].pmbLevelName+"]</span></br>";
			hidden = memberList[idx].pmbCode+":"+memberList[idx].pmbEmail;
			let div = createDiv("memInfo",mi,hidden,"m_box");
			
			div.addEventListener("dblclick", function() {
				movePrm(this);
			});
			memInfo.appendChild(div);
		}
		
		/*프로젝트 등록 창 초기화*/
		cancelProject();
	} else if (memberList.length==1) {
		let form = document.getElementsByName("serverCall")[0];
		form.action = "SendMail";
		form.method = "post";
		form.appendChild(createHidden("proCode", memberList[0].message));
		
		form.submit();
	} else {
		alert(memberList[0].message);
	}
}

function cancelProject() {
	let canvas = document.getElementById("canvas");
	const dataName = ["proName", "proComments", "proStart", "proEnd", "proVisible"];
	let data = document.getElementsByName("proInfo");
	
	for(idx=0;idx<data.length;idx++) {
		data.value = "";
	}
	
	cancel();
}

function cancel() {
	canvas.style.display = "none";
}

function movePrm(obj) {
	alert("Data: "+obj.getAttribute("value"));
	
	let mem = document.getElementById("memberInfo");
	let prm = document.getElementById("prmMemberInfo");
	
	let sendBtn = document.getElementById("sendBtn");
	
	if(obj.className=="m_box"){
		prm.appendChild(obj);
		obj.className="m_box invite";
		alert(prm.childNodes.length);
	} else {
		mem.appendChild(obj);
		obj.className="m_box";
	}
	
	sendBtn.style.display = (prm.childNodes.length>0)?"block":"none";
}

function sendEmail() {
	let form = document.getElementsByName("serverCall")[0];
	form.action = "SendMail";
	form.method = "post";
	
	/*proCode가져오기*/
	form.appendChild(createHidden("proCode", document.getElementById("projectInfo").childNodes[3].innerText.substring(6)));
	
	/*proMember정보 가져오기*/
	const prm = document.getElementById("prmMemberInfo").childNodes;
	for(idx=0;idx<prm.length;idx++) {
		const info = prm[idx].getAttribute("value").split(":");
		form.appendChild(createHidden("proMembers["+idx+"].pmbCode", info[0]));
		form.appendChild(createHidden("proMembers["+idx+"].pmbEmail", info[1]));
		form.appendChild(createHidden("proMembers["+idx+"].proPosition", "MB"));
		form.appendChild(createHidden("proMembers["+idx+"].proAccept", "ST"));
	}
	
	form.submit();
}

function sendEmailResult(ajaxData) {
	const proList = JSON.parse(ajaxData);
}

</script>

</head>
<body onload="init()">
	<div id="header">
		<i class="fa-solid fa-circle-plus" id="plus" onclick="movePage('Project')"></i>
		<div id="notice">공지사항</div>
		<div id="topSum" value="0" onclick="showInfo(this)">로그인 정보</div>
			<div id="infoBox">
				<input type="button" value="로그아웃" class="small-btn" onClick="accessOut()" />
				<div>마지막 로그인 시간: ${accessInfo.aslDate}</div>
				<input type="hidden" name="pmbCode" value="${accessInfo.pmbCode}" />
				<div>회원성명: ${accessInfo.pmbName}</div>
				<div>회원등급: ${accessInfo.pmbLevelName}</div>
				<div>클래스명: ${accessInfo.pmbClassName}</div>
				<div>이메일: ${accessInfo.pmbEmail}</div>
		</div>
	</div>
	
	<div id="side" name="movepage">
		<div class="menubox" onclick="movePage('DashBoard')"><i class="fa-solid fa-clipboard"></i></div>
		<div class="menubox" onclick="movePage('Notice')"><i class="fa-solid fa-bell"></i></div>
		<div class="menubox" onclick="movePage('Management')"><i class="fa-solid fa-bars-progress"></i></div>
		<div class="menubox" onclick="movePage('MyPage')"><i class="fa-solid fa-id-badge"></i></div>
	</div>
	
	<div id="contents">
		<div id="projectInfo" class="infoBox">
			<div class="box item title">New Project Information</div>
			<div class="box"></div>
			<div class="box"></div>
			<div class="box"></div>
			<div class="box"></div>
			<div class="box textareabox"></div>
		</div>
		<div class="infoBox">
			<div class="box item title">초대 가능한 멤버</div>
			<div id="memberInfo"></div>
		</div>
		<div class="infoBox">
			<div class="box item title">초대 예정 멤버</div>
			<div id="prmMemberInfo"></div>
			<div id="sendBtn" style="display:none;"><input type="button" class="small-btn" value="SEND EMAIL" onclick="sendEmail()"/></div>
		</div>
	</div>
	
	<div id="canvas" class="canvas">
		<div id="light-box" class="light-box-big">
			<div id="image-zone" class="lightbox image"></div>
			<div id="content-zone" class="lightbox content">
				<div id="cheader"></div>
				<div id="cbody"></div>
				<div id="cfoot"></div>
			</div>
		</div>	
	</div>
	<form name="serverCall" method="post"></form>
</body>
</html>