<%@ page import="compass.Main" %>

<%
String userinput = request.getParameter("search");
String result = "Error parsing query. Input cannot be empty!";
if (userinput != null && !userinput.isEmpty()) {
	System.err.println("User Query: " + userinput);
	result = Main.parseUserQuery(userinput);
	
}
%>

<!DOCTYPE HTML>
<html>
<head>
<title>Compass</title>
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<link rel="icon" type="image/ico" href="favicon_compass.ico">
<!-- Custom Theme files -->
<link href="css/styleResults.css" rel="stylesheet" type="text/css" media="all"/>
<link href="https://fonts.googleapis.com/css?family=Abel" rel="stylesheet">
<!--<link href="css/bootstrap.min.css" rel="stylesheet">-->
<script>
function feedbackSwitch() {

	if (document.getElementById('feedback')) {
		if (document.getElementById('feedback').style.display == 'none') {
			document.getElementById('feedback').style.display = 'block';
			document.getElementById('feedbackReturn').style.display = 'none';
		}
		else {
			document.getElementById('feedback').style.display = 'none';
			document.getElementById('feedbackReturn').style.display = 'block';
		}
	}
}
</script>
<script>
function showTrain(){
	document.getElementById('train_data').style.display = 'block';
	document.getElementById('bus_data').style.display = 'none';
}
function showBus(){
	document.getElementById('bus_data').style.display = 'block';
	document.getElementById('train_data').style.display = 'none';
}
function dataRender() {
	
	/*
		        
		        if(category == "none"){
				var div1 = document.getElementById('wrapper');
				div1.innerHTML += '<p><h1>No Results Found</h1></p>';
				}	
				*/
	//             document.getElementById('transcript').value = decodeURIComponent(document.cookie.split(";").replace("-", ""));
	
	jso = <%=result%>;
	category = jso["Category"];
	var w = document.getElementsByClassName("resultDesc")[0];
	var t = document.getElementsByClassName("transport")[0];
	var trainArray = {};				
	var busArray = {};	
	w.style.display = 'none';
	var ptext = jso["Response"]["Text"];
	if (ptext != null){
		var shortDesc = document.getElementById('shortDesc');
		shortDesc.innerHTML += jso["Response"]["Text"];			
	}else if(ptext == null){
		var shortDesc = document.getElementById('shortDesc');
		shortDesc.innerHTML = "";

	}

	
	if(category == 'weather'){
		document.getElementById("error").style.display = "none";

		weatherData = JSON.parse(jso["Response"]); 
		w.style.display = 'block';
		t.style.display = 'none';
		var element = document.getElementById("temperature");
		var element = document.getElementById("summmary");
		var element = document.getElementById("visibility");
		var element = document.getElementById("humidity");
		var div1 = document.getElementById('wrapper');
		
		div1.innerHTML += '<p><h1>Cardiff Weather</h1></p>';
		temperature.innerHTML += '<p class="weathersmll">Temperature</p>';
		temperature.innerHTML += '<div>' + weatherData['currently'].temperature + ' &deg;C</div>';
		summary.innerHTML += '<p class="weathersmll">Summary</p>';
		summary.innerHTML += '<div>' + weatherData['currently'].summary + '</div>';
		visibility.innerHTML += '<p class="weathersmll">Visibility</p>';
		visibility.innerHTML += '<div>' + weatherData['currently'].visibility + ' miles</div>';
		humidity.innerHTML += '<p class="weathersmll">Humidity</p>';
		humidity.innerHTML += '<div>' + weatherData['currently'].humidity + ' %</div>';
		icon.innerHTML = '<div><img class="weathericon" src="images/weathericons/'+ weatherData['currently'].icon+'.png"</div>';
	
	
	}else if(category == 'transport'){
		document.getElementById("error").style.display = "none";
        //get data & show only transport div
        transportData = JSON.parse(jso["Response"]);
        w.style.display = 'none';
        t.style.display = 'block';
        
        for(x in transportData.member){
        	if(transportData.member[x]["type"] == "train_station"){
        		trainArray[transportData.member[x]["name"]] = ["Details: " + transportData.member[x]["station_code"],transportData.member[x]["distance"]];
        	}else{
        		busArray[transportData.member[x]["name"]] = ["Details: " + transportData.member[x]["distance"]+"m",transportData.member[x]["description"]];	
        	}	
        }
		            
		// 			TRAIN ARRAY PRINT VALUES			
		var div1 = document.getElementById('wrapper');
		div1.innerHTML += '<p><h1>Cardiff Transport</h1></p><br>';
		
		div1.innerHTML += '<button onclick="showTrain();">Trains Information</button><button onclick="showBus();">Bus Information</button>';
		
		
		// Render the train data 
		var train_div = document.getElementById('train_data');
		
		train_div.innerHTML += '<p>Train Stations in Cardiff</p><br>';
		for(var i in trainArray){
			train_div.innerHTML += '<div>' + JSON.stringify(i).replace(/\"/g, "") + '</div><p class="header">'+ trainArray[i][0] + ' ' + trainArray[i][1] + 'm</p>';
		}
		
		//Render in Bus data
		var bus_div = document.getElementById('bus_data');
		// 			BUS ARRAY PRINT VALUES
		bus_div.innerHTML += '<p>Bus Stations in Cardiff</p><br>';
		for(var i in busArray){
			bus_div.innerHTML += '<div>' + JSON.stringify(i).replace(/\"/g, "") + '</div><p class="header">'+ busArray[i][0] + ' ' + busArray[i][1] + '</p>';
		}
		bus_div.style.display = 'none';
	
	}
	else if (category =="general"){
		document.getElementById("error").style.display = "none";

		generalData = JSON.parse(jso["Response"]);
	
		w.style.display = 'none';
		t.style.display = 'none';
		var div1 = document.getElementById('wrapper');
		var div2 = document.getElementById('subwrapper');
	
		div1.innerHTML += '<h1 style="text-transform: capitalize;">' + generalData.results[0].label + '</h1><br>'; 
		div2.innerHTML += generalData.results[0].description;

		jsonString = JSON.stringify(generalData);

		if(!jsonString.includes("Cardiff")) {
			div2.innerHTML = "Sorry this is not in the scope of Cardiff";
		}
		
	}else{
		document.getElementById("error").style.display = "block";

	}
	eval(spellCheck());
}
	function storeSearch(query) {
		document.cookie = query;
	}


</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
function submitFeedback(val)
{
	$.ajax({
		type: "POST",
		url: "http://131.251.172.60/feedback.php",
		data: {
			query: decodeURIComponent(document.cookie.split(";")[0]),
			feedback: val
		}
	}).done(function (msg) {
		alert("Your feedback has been recorded");
	});
}

function spellCheck() {
	$.ajax({
		url: "http://131.251.172.60/SpellingCorrector.php",
		dataType: "script",
		type: "GET",
		data: {
			query: decodeURIComponent(document.cookie.split(";")[0]),
			success: function(data){
				eval(data);
				document.cookie.split(";").forEach(function(c) { document.cookie = c.replace(/^ +/, "").replace(/=.*/, "=;expires=" + new Date().toUTCString() + ";path=/"); });
;
			}
		}
	});
}
</script>
<script>
  function startDictation() {

    if (window.hasOwnProperty('webkitSpeechRecognition')) {

      var recognition = new webkitSpeechRecognition();
      recognition.continuous = false;
      recognition.interimResults = false;

      recognition.lang = "en-US";
      recognition.start();
      
      recognition.onresult = function(e) {
        document.getElementById('transcript').value
                                 = e.results[0][0].transcript;
        recognition.stop();

        storeSearch(e.results[0][0].transcript);
        document.getElementById('labnol').submit();
        
      };

      recognition.onerror = function(e) {
        recognition.stop();
      }

    }
  }
</script>
</head>
<body onload="dataRender()">
<div class="container">
<div class="topbar"></div>
<div class="bottombar"></div>
<div class="item-1">
<a href="index.html">
<img src="images/compass.png" alt="compass" >
</a>
</div>
<div class="item-2">
<form id="labnol" method="POST" action="results.jsp">
<input type="search" name="search" id="transcript" class="resize" onsearch="storeSearch(this.value)" style="font-family: 'Abel', sans-serif;position: absolute; width:250px; margin-left: -125px;z-index: 0;" placeholder="Search Cardiff...">
	<img src="images/microphone.png" class="micIcon" onclick="startDictation()" style="position: relative; width: 20px; height: 20px; margin-top: 11px; margin-right: 10px; float:right;">
	<img src="images/search.png" class="searchIcon" onclick="document.getElementById('labnol').submit();" style="position: relative; width: 20px; height: 20px; margin-top: 11px; margin-right: 10px; float:right;">
</form>
</div>
</div>
<!-- WEATHER DIV -->
<main class="mainstyling">
<div id="error"><h2 class="errormsg">Sorry, no information related to the query was found.</h2></div>
<div id="wrapper">
</div>
<p id="subwrapper" class="header">
</p>
<div id ="shortDesc"></div>
<div class="resultDesc">
<div id="icon" class="weathericon">
</div>  
<br>  
<div id="temperature"  class="header">
</div>
<div id="summary"  class="header">
</div>
<div id="visibility"  class="header">
</div>
<div id="humidity"  class="header">
</div>

</div>
<!-- TRANSPORT DIV -->

<div class="transport">
<br>
<div id="train_data" class="info"></div>
<div id="bus_data" class="info"></div>

</div>
</div>

<!-- Spell Check -->
<div id="spellCheck"> </div>


</main>
<br><br><br>
</div>
<div class="bottombar">
<div id="feedback">
<h1>Was this answer helpful?</h1>
<button type="submit" id="btn" value="No" name="theresult" onclick="feedbackSwitch(); submitFeedback(0);"/>No</button>
<button type="submit" id="btn" value="Yes" name="theresult" onclick="feedbackSwitch(); submitFeedback(1);"/>Yes</button>
</div>
<div id="feedbackReturn">
<h1>Thank you for the feedback!</h1>
</div>
</div>

</body>
</html>