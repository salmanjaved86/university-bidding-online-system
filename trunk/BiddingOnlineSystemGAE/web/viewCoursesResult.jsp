<%@ taglib prefix="s" uri="/struts-tags"%>

<table align="center">
	<th>
	<h2>Course Listing</h2>
	</th>
</table>
<table border="1" align="center" cellpadding="5" width="85%" height="10">
	<th>Course Code</th>
	<th>School</th>
	<th>Course Title</th>
	<th>Description</th>
	<th>Add Bid</th>
	<s:iterator value="courseReturn">
		<tr>
			<td width="10%"><s:property value="courseCode" /></td>
			<td><s:property value="school" /></td>
			<td><s:property value="title" /></td>
			<td><s:property value="description" /></td>
			<td width="7%"><s:url id="url" action="viewSections">
				<s:param name="courseCode">
					<s:property value="courseCode" />
				</s:param>
			</s:url> <s:a href="%{url}">Add Bid</s:a></td>
		</tr>
	</s:iterator>

</table>
