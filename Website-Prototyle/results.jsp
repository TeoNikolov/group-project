<%@ page import="compass.Main" %>

<%
String userinput = request.getParameter("search");
String result = "Error parsing query. Input cannot be empty!";
if (userinput != null && !userinput.isEmpty()) {
	result = Main.parseUserQuery(userinput);
}
%>

<!DOCTYPE HTML>
<html>
<head>
<title>Compass</title>
<meta name="viewport" content="width=device-width,initial-scale=1.0">

<!-- Custom Theme files -->
<link href="css/styleResults.css" rel="stylesheet" type="text/css" media="all"/>
<link href="https://fonts.googleapis.com/css?family=Abel" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<!-- 	<!-- information -->
<!--   <header>
	<div class="topbar"></div>
	<div class="bottombar"></div>

    <div class="topdiv">
      <ul>
        <li></li>
        <li>

		</li>
      </ul>
    </div>
  </header> -->
<div class="container">
	<div class="topbar"></div>
	<div class="bottombar"></div>
  <div class="item-1">
  <a href="index.html">
  	<img src="images/compass.png" alt="compass" >
  </a>
  </div>
  <div class="item-2">
  	    <form action="results.jsp">
			<input type="search" name="search" placeholder="Search Cardiff...">
		</form>
  </div>
  <div class="content>">
	  <br><br><br><br><br><br><br><br>
	  <%=result%>
  </div>
</div>
</body>
</html>