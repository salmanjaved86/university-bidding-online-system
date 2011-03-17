<%-- 
    Document   : bidBootstrap
    Created on : Oct 22, 2010, 12:43:25 AM
    Author     : Administrator
--%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<s:if test="#session.get('admin')==null">
	<s:action name="loginView" executeResult="true" />
</s:if>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bid Bootstrap</title>
</head>
<body>
<s:form name="bidBootstrap" action="/admin/bidBootstrap.action"
	enctype="multipart/form-data" method="post">
	<s:file name="bidFile" label="Bid.csv" value="Browse csv..." />
	<s:submit />
</s:form>
</body>
</html>
