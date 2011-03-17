<%@page import="model.*,org.json.*,bios.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<s:if test="#session.get('admin')==null">
	<s:action name="loginView" executeResult="true" />
</s:if>
<s:else>
	<% String roundAction = request.getParameter("action"); %>
	<s:action name="urlAction">
		<s:param name="action"><%=roundAction%></s:param>
	</s:action>
	<s:property value="#request.JSONObject" />
</s:else>