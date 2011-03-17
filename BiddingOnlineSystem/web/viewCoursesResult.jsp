<%@taglib uri="/struts-tags" prefix="s"%>
<%--@taglib uri="/struts-jquery-tags" prefix="sj" --%>
<%@page import="java.text.*"%>
<%@page import="DataManager.*,model.*,java.util.*"%>
<s:if test="#session.get('student')==null">
	<s:action name="loginView" executeResult="true" />
</s:if>
<s:else>
	<html>
	<head>
	<title>Welcome to Merlion University : Add New Bid</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../css/css-display-viewcourseresult.css" rel="stylesheet"
		type="text/css" media="screen" />
	</head>
	<body>
	<div id="viewallpanel">
	<div id="displaytoprightpane"><span id="nametext"><s:property
		value="#session.get('student').getName()" /></span>
	<div id="links">|&nbsp;<a href="display.action">Home</a>&nbsp;
	|&nbsp;<a href="<s:url value='logout.action'/>">Logout</a></div>
	</div>
	<%
                    ArrayList<Course> allCourses = CourseDataManager.retrieveAll();

                    if (!allCourses.isEmpty()) {
                        // Print header of the table
                        out.println("<div id='tableheader'>Add New Bid</div>");
                        out.println("<table>");
                        out.println("<tr>");
                        out.println("<td class='day strong'>Course Code</td>");
                        out.println("<td class='day strong'>School</td>");
                        out.println("<td class='day strong'>Title</td>");
                        out.println("<td class='day strong'>Description</td>");
                        out.println("<td class='day strong'>Add Bid</td>");
                        out.println("</tr>");
                %> <s:iterator value="courseReturn">
		<%
                        out.println("<tr>");
                        out.println("<td>");
                    %>
		<s:property value="courseCode" />
		<%
                        out.println("</td>");
                        out.println("<td>");
                    %>
		<s:property value="school" />
		<%
                        out.println("</td>");
                        out.println("<td>");
                    %>
		<s:property value="title" />
		<%
                        out.println("</td>");
                        out.println("<td>");
                    %>
		<s:property value="description" />
		<%
                        out.println("</td>");
                        out.println("<td>");
                    %>
		<s:url id="url" action="viewSections">
			<s:param name="courseCode">
				<s:property value="courseCode" />
			</s:param>
		</s:url>
		<s:a href="%{url}" id="addbidlink">Add Bid</s:a>
		<%
                        out.println("</td>");
                        out.println("</tr>");
                    %>
	</s:iterator> <%
                        out.println("</table>");
                    }
                %>
	</div>
	</body>
	</html>
</s:else>


