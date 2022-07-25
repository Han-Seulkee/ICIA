<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main</title>
<script src="/resources/js/common.js"></script>
<script src="/resources/js/main.js"></script>
<script src="https://use.fontawesome.com/releases/v6.1.1/js/all.js"></script>
<style>@import url("/resources/css/main.css");</style>
<style>@import url("/resources/css/common.css");</style>
<style>@import url("/resources/css/slide.css");</style>

<script>
function init() {
	let clientData = "pmbCode="+"${accessInfo.pmbCode}";
	postAjaxJson("ShowProject", clientData, "setView");
}

function newProjectBtn() {
	let projectField = document.getElementById("projectField");
	
	let regPro = document.createElement("i");
	regPro.setAttribute("class","fa-solid fa-circle-plus");
	regPro.setAttribute("id","newPro");
	regPro.setAttribute("onclick","movePage('Project')");
	
	projectField.appendChild(regPro);
}

function setView(ajaxData) {
	const proList = JSON.parse(ajaxData);
	console.log(proList[0].type);
	
	if(proList[0].type) {
		let proInfo = document.getElementById("projectField");
		
		for(idx=0;idx<proList.length;idx++) {
			pi = "<div class='pj' >";
			pi += "<div class='items' >"+proList[idx].proName+"</div>";
			pi += "<div class='items'>"+proList[idx].director+"</div>";
			pi += "<div class='items'>"+proList[idx].period+"</div>";
			pi += "<input type='button' value='Member' class='items small-btn' onclick='movePage(\"Management\")' />";
			pi += "<input type='button' value='Job' class='items small-btn' onclick='movePage(\"Management\")' />";
			pi += "<input type='button' value='Progress' class='items small-btn' onclick='' /></div>";
			hidden = proList[idx].proCode+":"+proList[idx].dirCode;
			let div = createDiv(null,pi,hidden,"slide");

			proInfo.appendChild(div);
		}
		
	} else {
		newProjectBtn();
	}
	
	document.body.appendChild(getJs());
}

function getJs() {
	let slideJs = document.createElement("script");
	slideJs.setAttribute("src","resources/js/slide.js");
	
	return slideJs;
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
		<div id="projects">
			<button id="prev" class="long-btn">◀</button>
			<div id="projectField">
			</div>
			<button id="next" class="long-btn">▶</button>
		</div>
		<div id="invitations">
			${receivedInfo}
			${sendInfo}
		</div>
	</div>
	
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
	<form name="serverCall" method="post"></form>
	
</body>
</html>