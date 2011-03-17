<%-- 
    Document   : nameinput
    Created on : Sep 25, 2010, 11:53:45 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags"%>

<html>
<style>
.errorMessage {
	color: red;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Please tell me your name</title>
</head>
<body>
<s:form action="sayhello" method="POST">
	<s:textfield name="name" label="Your name" />
	<s:submit />
</s:form>
<h1>Hello World!</h1>
</body>
</html>
