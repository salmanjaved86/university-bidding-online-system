<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>Welcome to Merlion University : Login Panel</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/css.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<div id="centerpanel">
<div id="loginleftpane"><img src="images/login-leftpane.png" /></div>
<div id="logincenterlogo"><img src="images/login-centerlogo.png" />
<div id="countdownpane">!--Insert Here--!</div>
<s:form id="loginform" action="login" method="post">
	<s:textfield name="userId" label="Username" />
	<s:password name="password" label="Password" />
	<s:submit id="loginbtn" value="Login" />
</s:form></div>
<div id="copyrightpane">&copy; Copyright 2010 G3T2 | All Rights
Reserved</div>
<div id="logincenterlowercurve"><img
	src="images/login-lowercurve.png" /></div>
</div>
</body>
</html>
