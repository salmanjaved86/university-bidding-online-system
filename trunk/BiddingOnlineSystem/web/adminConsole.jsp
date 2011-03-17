<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@page import="java.text.*"%>
<%@page import="DataManager.*,model.*,java.util.*"%>
<s:if test="#session.get('admin')==null">
	<s:action name="loginView" executeResult="true" />
</s:if>
<s:else>
	<html>
	<head>
	<title>Welcome to Merlion University : Administrator Panel</title>
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
                %> <s:form action="setRound">
                    Round Change: 
                    <select name="roundAction">
			<option value="2">Start Round 2</option>
			<option value="3">Stop Round</option>
		</select>
		<input value="Execute Action" type="submit">
	</s:form> <s:form name="bootstrap" action="/admin/bootstrap.action"
		enctype="multipart/form-data" method="post">
		<s:file name="zippedFile" label="Bootstrap" value="Browse zip..." />
		<s:submit value="Bootstrap File"></s:submit>
	</s:form> <%
                            out.println("<br /><br /></td></tr></table>");
                %>
	<div id="adminerror">
	<%
                                String message = request.getParameter("message");
                                if (message != null) {
                                    out.print(message);
                                }
                                else {
                                    out.print("Welcome!");
                                }
                    %>
	</div>
	</div>
	</body>
	</html>
</s:else>

