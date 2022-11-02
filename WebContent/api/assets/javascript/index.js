var xmlHttpRequest;

function sendWithPostMethod() {	
	var url = "login";
	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = receive;
	xmlHttpRequest.open("post", url, true);
	xmlHttpRequest.send(null);
}

function receive() {
	if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
		var response = JSON.parse(xmlHttpRequest.responseText);
		console.log(response);
	}
}

window.addEventListener("load", function() {
    var getButtonElement = document.querySelector("button");
    getButtonElement.addEventListener("click", sendWithPostMethod, false);   
});