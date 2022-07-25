<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Landing</title>
<script src="/resources/js/common.js"></script>
<script>
	function init() {
		getAjaxJson("https://api.ipify.org", "format=json", "callServer");
	}
	function callServer(ajaxData) {
		jsonData = JSON.parse(ajaxData);
		const pubIp = ("publicIp="+jsonData.ip);
		location.href = "http://192.168.55.80/Landing?"+pubIp;
		//alert(pubIp);
	}
</script>
</head>
<body onload="init()">

</body>
</html>