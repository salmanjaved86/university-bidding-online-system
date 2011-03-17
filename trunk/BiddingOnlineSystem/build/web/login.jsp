<%@taglib uri="/struts-tags" prefix="s"%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<s:form action="login">
	<s:textfield name="userId" label="User ID" value="" />
	<s:password name="password" label="Password" />
	<s:submit value="Login" />
</s:form>

</body>
</html>
