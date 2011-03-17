<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>

<head>
<title>Error</title>
</head>

<body>

<p>
<h4>Exception Name:</h4>
<s:property value="exception" />
</p>
<p>
<h4>What you did wrong:</h4>
<s:property value="exceptionStack" />
</p>


</body>

</html>
