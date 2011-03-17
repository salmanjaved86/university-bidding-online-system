<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="DataManager.*"%>

<s:if test="#session.get('admin')==null">
	<s:action name="loginView" executeResult="true" />
</s:if>
<s:else>
	<s:form action="bidURL">
		<s:textfield name="userId" label="UserId" />
		<s:textfield name="amount" label="Amount" />
		<s:textfield name="courseCode" label="Course" />
		<s:textfield name="sectionCode" label="Section" />
		<s:submit id="loginbtn" value="Place Bid" />
	</s:form>

	<s:property value="#request.JSONObject" />
</s:else>
