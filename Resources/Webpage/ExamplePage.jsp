<%@ page import="Example.ExampleClass" %>

<%
String strResponse;
strResponse = ExampleClass.testMethod();
%>

<%=strResponse %>