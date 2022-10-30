var xmlHttpRequest;

function sendWithPostMethod() {
	var date = document.getElementById("date");
	
	var url = "execute?date=" + date.value +"&method=post";
	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = receive;
	xmlHttpRequest.open("post", url, true);
	xmlHttpRequest.send(null);
}

function receive() {
	if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
		var response = JSON.parse(xmlHttpRequest.responseText);

		var yearElement = document.getElementById("year");
		var monthMessageElement = document.getElementById("month");
		var dayMessageElement = document.getElementById("day");
		yearElement.innerHTML = response.year;
		monthMessageElement.innerHTML = response.month;
		dayMessageElement.innerHTML = response.day;
	}
}

window.addEventListener("load", function() {
    var getButtonElement = document.getElementById("execute_button");
    getButtonElement.addEventListener("click", sendWithPostMethod, false);   
});