<%@taglib prefix="s" uri="/struts-tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<s:if test="#session.get('admin')==null">
	<s:action name="loginView" executeResult="true" />
</s:if>
<s:else>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>bootstrap</title>
	</head>
	<body>
	<s:form name="bootstrap" action="/admin/bootstrap.action"
		enctype="multipart/form-data" method="post">
		<s:file name="zippedFile" label="File" value="Browse zip..." />
		<s:submit />
	</s:form>

	</body>
	</html>
</s:else>