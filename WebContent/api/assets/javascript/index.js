var xmlHttpRequest;
let res_uri;

// function login() {	
// 	var url = "login";
// 	xmlHttpRequest = new XMLHttpRequest();
// 	xmlHttpRequest.withCredentials = true;
// 	xmlHttpRequest.onreadystatechange = receiveFromLogin;
// 	xmlHttpRequest.open("get", url, true);
// 	xmlHttpRequest.send(null);

	// window.location.href = "/isp2/api/";
	// xmlHttpRequest = new XMLHttpRequest();
	// xmlHttpRequest.withCredentials = true;
	// xmlHttpRequest.onreadystatechange = receive;
	// xmlHttpRequest.open("get", url, true);
	// xmlHttpRequest.send(null);
// }

function serch() {
	var url = "search"
	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.withCredentials = true;
	xmlHttpRequest.onreadystatechange = receiveFromSearch;
	xmlHttpRequest.open("get", url, true);
	xmlHttpRequest.send(null);
}

function redirect_to_api_auth() {

	window.location.href = res_uri;

	// const xhr = new XMLHttpRequest();
	// xhr.open("get", res_uri);
	// xhr.setRequestHeader('Access-Control-Allow-Methods', 'GET, PUT, POST, DELETE, OPTIONS');
	// xhr.setRequestHeader('Access-Control-Allow-Origin', '*');
	// xhr.setRequestHeader('Access-Control-Allow-Headers', 'Authorization, Content-Type, Accept, X-User-Email, X-Auth-Token, X-HTTP-Method-Override, X-Requested-With, api-token');
	// xhr.setRequestHeader('Access-Control-Allow-Credentials', 'true');
	// xhr.withCredentials = true;
	// xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencode');
	// xhr.send();

	// console.log(xhr.getResponseHeader("Access-Control-Allow-Methods"))
}

function receiveFromLogin() {
	if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
		var response = JSON.parse(xmlHttpRequest.responseText);
		res_uri = response.uri;
		redirect_to_api_auth();
	}
}

function receiveFromSearch() {
	if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
		var response = JSON.parse(xmlHttpRequest.responseText);
		if(document.querySelector(".result")){
			let resultElement = document.querySelector(".result");
			resultElement.innerHTML = response.data;
			console.log("OK");
		}
	}
}

function receive() {
	if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
		var response = JSON.parse(xmlHttpRequest.responseText);
	}
}

window.addEventListener("load", function() {
	if(document.querySelector("#login")){
		console.log("OK")
		var getButtonElement = document.querySelector("#login");
		getButtonElement.addEventListener("click", login, false);   
	}
});

