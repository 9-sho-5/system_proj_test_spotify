var xmlHttpRequest;
let res_uri;

function sendWithGetMethod() {	
	var url = "login";
	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.withCredentials = true;
	xmlHttpRequest.onreadystatechange = receive;
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

function receive() {
	if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
		var response = JSON.parse(xmlHttpRequest.responseText);
		res_uri = response.uri;
		console.log(res_uri)
		redirect_to_api_auth();
	}
}

window.addEventListener("load", function() {
    var getButtonElement = document.querySelector("button");
    getButtonElement.addEventListener("click", sendWithGetMethod, false);   
});