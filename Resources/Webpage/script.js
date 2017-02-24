var handle;
var auto = false;

function doClick() {
	$.ajax({
		type: "POST",
		url: "ExamplePage.jsp",
		data: {},
		error: function(){
			alert("ERROR: Did not make the call!");
	    	},
		success: function(response){
			$('#number').html(response);
	    	}
	});
	
	return false;
}


function submitForm() {
	$.ajax({
		type: "POST",
		url: "ExamplePage.jsp",
		data: {uinput : document.getElementById('txtinput').value},
		error: function(){
			alert("ERROR: Did not make the call!");
	    	},
		success: function(response){
			$('#number').html(response);
	    	}
	});
	
	return false;	
}

function switchFunc() {
	if (!auto) {
		doClick();
		handle = window.setInterval(function(){doClick()}, 1000)
		
		var toggle = document.getElementById('toggle');
		toggle.innerHTML = "OFF";
		toggle.style.color = 'red';

	} else {
		if (handle != null) {
			window.clearInterval(handle);
			handle = null;
		}
		
		var toggle = document.getElementById('toggle');
		toggle.innerHTML = "ON";
		toggle.style.color = '#00FF00';
	}

	auto = !auto;
}

function manualClick() {
	if (!auto) {
		doClick();
	}
}