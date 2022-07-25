<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Info</title>
<script src="https://use.fontawesome.com/releases/v6.1.1/js/all.js"></script>
<script src="/resources/js/common.js"></script>
<style>@import url("/resources/css/common.css");</style>

</head>
<body>
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

	</div>
	
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
	<form name="serverCall" method="post"></form>
</body>
</html>