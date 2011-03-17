<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="#session.get('student')==null">
	<s:action name="loginView" executeResult="true" />
</s:if>
<s:else>
	<s:form action="editBid" method="post">
		<s:textfield name="amount" label="Amount:" />
		<input type="hidden" name="courseCode"
			value="<s:property value="courseCode"/>" />
		<input type="hidden" name="sectionCode"
			value="<s:property value="sectionCode"/>" />
		<s:submit value="Edit" />
	</s:form>
	<input type="button" value="Back" onClick="history.go(-1)" />
</s:else>