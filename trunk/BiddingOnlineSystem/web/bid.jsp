<%@ taglib prefix="s" uri="/struts-tags"%>

<%
            String password = request.getParameter("password");
%>
<s:form action="bidURL">
	<s:textfield name="userId" label="UserId" />
	<s:textfield name="amount" label="Amount" />
	<s:textfield name="courseCode" label="Course" />
	<s:textfield name="sectionCode" label="Section" />
	<input type="hidden" name="password" value="<%=password%>" />
	<s:submit id="loginbtn" value="Place Bid" />
</s:form>



