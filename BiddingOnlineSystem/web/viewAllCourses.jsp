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
	<title>Welcome to Merlion University : View All Courses</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="css/css-display-viewallcourses.css" rel="stylesheet"
		type="text/css" media="screen" />
	<sj:head jqueryui="true" />
	</head>
	<body>
	<div id="viewallpanel">
	<div id="displaytoprightpane"><span id="nametext">Hi <s:property
		value="#session.get('student').getName()" />!</span></div>
	<%
                            ArrayList<Course> allCourses = CourseDataManager.retrieveAll();

                            if (!allCourses.isEmpty()) {
                                // Print header of the table
                                out.println("<div id='tableheader'>View All Courses</div>");
                                out.println("<table>");
                                out.println("<tr>");
                                out.println("<td class='day strong'>Course Code</td>");
                                out.println("<td class='day strong'>School</td>");
                                out.println("<td class='day strong'>Title</td>");
                                out.println("<td class='day strong'>Description</td>");
                                out.println("<td class='day strong'>Exam Date</td>");
                                out.println("<td class='day strong'>Exam Start</td>");
                                out.println("</tr>");

                                // Print data of the table
                                for (Course course : allCourses) {
                                    out.println("<tr>");
                                    out.println("<td>" + course.getCourseCode() + "</td>");
                                    out.println("<td>" + course.getSchool() + "</td>");
                                    out.println("<td>" + course.getTitle() + "</td>");
                                    out.println("<td>" + course.getDescription() + "</td>");
                                    out.println("<td>" + course.getExamDate() + "</td>");
                                    out.println("<td>" + course.getExamStart() + "</td>");
                                    out.println("</tr>");
                                }

                                out.println("</table>");
                            }
                %>
	</div>
	</body>
	</html>
</s:else>

