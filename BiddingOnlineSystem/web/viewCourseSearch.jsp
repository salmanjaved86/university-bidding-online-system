<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="#session.get('student')==null">
	<s:action name="loginView" executeResult="true" />
</s:if>
<s:else>
	<s:form id="courseSearchForm" action="courseSearch">
		<s:textfield name="keyword" label="Search For" />
		<s:submit id="searchBtn" value="Search" />
	</s:form>


	<table align="center">
		<th>
		<h2>Course Search Listing</h2>
		</th>
	</table>
	<table border="1" align="center" cellpadding="5" width="85%"
		height="10">
		<th>Course Code</th>
		<th>School</th>
		<th>Course Title</th>
		<th>Description</th>
		<th>Exam Date</th>
		<th>Exam Start</th>
		<th>Exam End</th>
		<s:iterator value="courseSearchList">
			<tr>
				<td width="10%"><s:property value="courseCode" /></td>
				<td><s:property value="school" /></td>
				<td><s:property value="title" /></td>
				<td><s:property value="description" /></td>
				<td><s:property value="examDate" /></td>
				<td><s:property value="examStart" /></td>
				<td><s:property value="examEnd" /></td>
		</s:iterator>

	</table>
</s:else>