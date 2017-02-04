<%@ page import="java.util.Random" %>
<%@ page import="Example.ExampleClass" %>

<html>
    <head>
        <link rel="stylesheet" href="skin.css">
    </head>

    <body>
        <p>This is a test paragraph.</p>
        <p><a id="trigger" onClick="doClick()">Click me to call a JAVASCRIPT method that will call a JAVA method!</a></p>
        <br>
        We can use JSP directly inside our .jsp file to get a random number like so:
	<br>
	        
        <%
        Random r = new Random();
        int i = r.nextInt();
        out.println(i);
        %>
        
        <br><br>
        
        Or we can create our class, build it and then import it in the .jsp file and execte JAVA code like so:
	<br>
        
        <%
        out.println(ExampleClass.testMethod());
        %>

    </body>
    
<script src="script.js"></script>
</html>