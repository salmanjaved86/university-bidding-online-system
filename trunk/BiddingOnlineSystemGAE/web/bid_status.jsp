<%@taglib uri="/struts-tags" prefix="s"%>

<s:if test="#session.get('admin')==null">
	<s:action name="loginView" executeResult="true" />
</s:if>
<s:else>
	<%
  String courseCode = request.getParameter("course");
  String sectionCode = request.getParameter("section");
%>
	<s:action name="bidStatus">
		<s:param name="courseCode"><%=courseCode%></s:param>
		<s:param name="sectionCode"><%=sectionCode%></s:param>
	</s:action>
	<s:property value="#request.JSONObjectList" />
</s:else>
