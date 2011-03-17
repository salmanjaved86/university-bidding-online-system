<%@taglib uri="/struts-tags" prefix="s"%>
<%--@taglib uri="/struts-jquery-tags" prefix="sj" --%>
<%@page import="java.text.*"%>
<%@page import="JDODataManager.*,JDOModel.*,java.util.*"%>

<html>
<head>
<title>Welcome to Merlion University : Administrator Panel :
Bootstrap Successful</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../css/css-admin.css" rel="stylesheet" type="text/css"
	media="screen" />
</head>
<body>
<div id="viewallpanel">
<div id="displaytoprightpane"><span id="nametext">Administrator</span>
<div id="links">|&nbsp;<a href="adminView.action">Home</a>&nbsp;
|&nbsp;<a href="<s:url value='logout.action'/>">Logout</a></div>
</div>
<%
                    out.println("<div id='tableheader'>Admin Panel</div>");
                    out.println("<table id='adminframe'>");
                    out.println("<tr><td>");

                    String msg = (String) request.getAttribute("test");
                    if (msg != null) {
                        out.print(msg + "<br/>");
                    }
                %> <br />
<br />
<s:url id="url" action="adminView" /> <s:a href="%{url}">Back to Admin Panel</s:a>
<%
                    out.println("<br /><br /></td></tr></table>");
                %>
</div>
</body>
</html>


