<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="org.json.*"%>
<s:if test="#request.JSONObject == null">
	<s:action name="loginView" executeResult="true" />
</s:if>

<s:else>

	<%
        JSONObject object = (JSONObject) request.getAttribute("JSONObject");
        out.println(object);
    %>
</s:else>
