<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<script type="text/javascript">
         </script>
</head>
<h1>Sections under <s:property value="courseCode" /></h1>
<s:form name="myForm" action="addBid" method="post">
	<table border="1" cellpadding="5">
		<th>Choice</th>
		<th>Section</th>
		<th>Day</th>
		<th>Start Time</th>
		<th>End Time</th>
		<th>Instructor</th>
		<th>Venue</th>
		<th>Size</th>
		<s:iterator value="sectionReturn">
			<tr>
				<td><input type="radio" name="sectionCode"
					value="<s:property value='sectionCode'/>" checked></td>
				<td><s:property value="sectionCode" /></td>
				<td><s:property value="day" /></td>
				<td><s:property value="start" /></td>
				<td><s:property value="end" /></td>
				<td><s:property value="instructor" /></td>
				<td><s:property value="venue" /></td>
				<td><s:property value="size" /></td>
			</tr>
		</s:iterator>
	</table>
	<br />
        Amount : <input type="text" name="amount" value=""
		onChange="validateAmt(this.value)" />
	<input type="hidden" name="courseCode"
		value="<s:property value="courseCode"/>" />
	<s:submit align="left" value="Add Bid" />
	<br />
	<input type="button" value="Back" onClick="history.go(-1)" />
</s:form>

</html>
