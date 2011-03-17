<%-- 
    Document   : ajaxTestView
    Created on : Oct 22, 2010, 1:44:00 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
<sj:head />

</head>
<div id="div1">Div 1</div>
<s:url id="ajaxTest" value="/admin/ajaxdemo.action" />

<sj:a id="link1" href="%{ajaxTest}" targets="div1">

      Update Content
    </sj:a>

</body>
</html>
