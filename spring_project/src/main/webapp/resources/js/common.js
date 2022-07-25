let jsonData;

function accessOut(){
  	let form = document.getElementsByName("serverCall")[0];
  	let pmbCode = document.getElementsByName("pmbCode")[0];
  	form.appendChild(pmbCode);
  	form.setAttribute("action", "AccessOut");
  	form.submit();
}

function movePage(action) {
	let form = document.getElementsByName("serverCall")[0];
  	let page = document.getElementsByName("movepage")[0];
  	form.appendChild(page);
  	form.setAttribute("action", action);
  	form.submit();
}

function lightBoxCtl(title, dis){
	let canvas = document.getElementById("canvas");
	let header = document.getElementById("cheader");
	header.innerText = title;
	canvas.style.display = dis? "block":"none";
}

function createInput(typeName, value, objName, placeholder, className) {
	let input = document.createElement("input");
	if (typeName != null)input.setAttribute("type", typeName);
	if (value != null)input.setAttribute("value", value);
	if (objName != null)input.setAttribute("name", objName);
	if (placeholder != null)input.setAttribute("placeholder", placeholder);
	if (name != null)input.setAttribute("class", className);
	
	return input;
}

function createTextArea(objName, row, col, className) {
	let text = document.createElement("textarea");
	if (objName != null) text.setAttribute("name", objName);
	if (row != null) text.setAttribute("row", row);
	if (col != null) text.setAttribute("col", col);
	if (className != null) text.setAttribute("class", className);
	
	return text;
}

function createDiv(id, text, value, className) {
	let div = document.createElement("div");
	if(id != null) div.setAttribute("id", id);
	if(className != null) div.setAttribute("class", className);
	if(value != null) div.setAttribute("value", value);
	if(text != null) div.innerHTML = text;
	
	return div;
}

function createHidden(objName, value) {
	let input = document.createElement("input");
	input.setAttribute("type", "hidden");
	if (objName != null)input.setAttribute("name", objName);
	if (value != null)input.setAttribute("value", value);
	
	return input;
}

/* 패스워드 유효성 검사*/
/* 문자의 종류가 3개 이상 
*/
function isCharCheck(text, type) {
	const largeChar = /[A-Z]/;
	const smallChar = /[a-z]/;
	const num = /[0-9]/;
	const specialChar = /[!,@,#,$,%,^,&,*]/;
	
	let typeCnt = 0;
	
	if(largeChar.test(text)) typeCnt++;
	if(smallChar.test(text)) typeCnt++;
	if(num.test(text)) typeCnt++;
	if(specialChar.test(text)) typeCnt++;
	
	if(type){
		result = typeCnt >= 3? true:false;}
	else {
		result = typeCnt == 0? true:false;}
	
	return result;
}

function isCharLengthCheck(text, min, max) {
	let result = false;
	if(max != null) {
		if(text.length >= min && text.length <= max) result = true;
	} else {
		if(text.length >= min) result = true;
	}
	
	return result;
}

/* Ajax :: GET */
function getAjaxJson(jobCode, clientData, fn) {
	const ajax = new XMLHttpRequest();
	const action = (clientData!="")?(jobCode + "?" + clientData):jobCode;
	
	ajax.onreadystatechange = function() {
		if(ajax.readyState == 4 && ajax.status == 200) { //4:데이터가 넘어옴
			window[fn](ajax.responseText); //응답 데이터
		}
	};

	ajax.open("get", action);
	ajax.send();
}
/* Ajax :: POST */
function postAjaxJson(jobCode, clientData, fn) {
	const ajax = new XMLHttpRequest();

	ajax.onreadystatechange = function() {
		if(ajax.readyState == 4 && ajax.status == 200) { //4:데이터가 넘어옴
			window[fn](ajax.responseText); //응답 데이터
		}
	};

	//post방식은 form이 필요, 해당 방식은 form이 없으므로 urlencoded방식으로 데이터 전송, 
	ajax.open("post", jobCode);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); //post이지만  url방식으로 데이터가 왔음을 알려줌, 텍스트만 가능
																				//form데이터로(json)전환 하는 방식도 있음 
	ajax.send(clientData);
}

/* Public IP Saving */
function setPublicIp(ajaxData) {
	jsonData = JSON.parse(ajaxData);
}

function showInfo(obj) {
	let infoBox = document.getElementById("infoBox");
	
	if(obj.value==0) {
		infoBox.style.display = "none";
		obj.value = "1";
	} else {
		infoBox.style.display = "block";
		obj.value = "0";
	}
}