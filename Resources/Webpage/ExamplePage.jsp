<%@ page import="Example.Main" %>

<%
String userinput = request.getParameter("uinput");

String strResponse;
strResponse = Main.NLPMethod(userinput);
%>

<%=strResponse %>