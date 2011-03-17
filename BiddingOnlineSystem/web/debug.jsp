<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@page import="java.text.*"%>
<%@page import="DataManager.*,model.*,java.util.*"%>
<s:if test="#session.get('student')==null">
	<s:action name="loginView" executeResult="true" />
</s:if>
<s:else>
	<html>
	<head>
	<title>Welcome to Merlion University : Debug 404 Page</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../css/css-display-debug.css" rel="stylesheet"
		type="text/css" media="screen" />
	</head>
	<body>
	<div id="viewallpanel">
	<div id="displaytoprightpane"><span id="nametext"><s:property
		value="#session.get('student').getName()" /></span>
	<div id="links">|&nbsp;<a href="display.action">Home</a>&nbsp;
	|&nbsp;<a href="<s:url value='logout.action'/>">Logout</a></div>
	</div>
	<div id='tableheader'>paiseh! We've got a problem..</div>
	<table>
		<tr>
			<td>
			<div id="errorwords">Hiccups:</div>
			<div id="actionMessages"><s:actionerror /><input id="backbtn"
				type="button" value="Back" onClick="history.go(-1)" /></div>
			<br>
			<img src="../images/sorry_dog.jpg" alt="" /></td>
		</tr>
	</table>
	</div>
	</body>
	</html>
</s:else>


