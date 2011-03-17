<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@page import="java.text.*"%>
<%@page import="DataManager.*,model.*,java.util.*"%>
<s:if test="#session.get('student')==null">
	<s:action name="loginView" executeResult="true" />
</s:if>
<s:else>

	<div id="viewallpanel">
	<%
                    // Print header of the table
                    out.println("<div id='tableheader'>Sections under ");
        %> <s:property value="courseCode" /> <%
                    out.println("</div>");
        %> <s:form name="myForm" action="addBid" method="post"
		onsubmit="validateInput()">
		<%
                        out.println("<table>");
                        out.println("<tr>");
                        out.println("<td class='day strong'>Choice</td>");
                        out.println("<td class='day strong'>Section</td>");
                        out.println("<td class='day strong'>Day</td>");
                        out.println("<td class='day strong'>Start Time</td>");
                        out.println("<td class='day strong'>End Time</td>");
                        out.println("<td class='day strong'>Instructor</td>");
                        out.println("<td class='day strong'>Venue</td>");
                        out.println("<td class='day strong'>Vacancy</td>");
                        out.println("<td class='day strong'>Min Bid</td>");
                        out.println("</tr>");
            %>
		<s:iterator value="sectionReturn">
			<%
                            out.println("<tr>");
                            out.println("<td>");
                %>
			<input type="radio" name="sectionCode"
				value="<s:property value="sectionCode"/>" checked>
			<%
                            out.println("</td>");
                            out.println("<td>");
                %>
			<s:property value="sectionCode" />
			<%
                            out.println("</td>");
                            out.println("<td>");
                %>
			<s:property value="day" />
			<%
                            out.println("</td>");
                            out.println("<td>");
                %>
			<s:property value="start" />
			<%
                            out.println("</td>");
                            out.println("<td>");
                %>
			<s:property value="end" />
			<%
                            out.println("</td>");
                            out.println("<td>");
                %>
			<s:property value="instructor" />
			<%
                            out.println("</td>");
                            out.println("<td>");
                %>
			<s:property value="venue" />
			<%
                            out.println("</td>");
                            out.println("<td>");
                %>
			<s:property value="vacancy" />
			<%
                            out.println("</td>");
                            out.println("<td>e$");
                %>
			<s:property value="minBid" />
			<%
                            out.println("</td>");
                            out.println("</tr>");
                %>

		</s:iterator>

		<%
                        out.println("</table>");
                        out.println("<br/>");

                        out.println("Amount : e$ <input type='text' name='amount' value='' />");
                        out.println("");
            %>
		<input type="hidden" name="courseCode"
			value="<s:property value="courseCode" />" />
		<%
                        out.println("");
            %>
		<input value="Add Bid" type="submit">

	</s:form></div>
</s:else>


