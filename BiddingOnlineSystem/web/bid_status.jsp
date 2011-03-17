<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="org.json.*"%>
<%@page import="java.util.*"%>

<s:if test="#request.JSONObjectList == null">
	<s:action name="loginView" executeResult="true" />
</s:if>

<s:else>

	<%
    ArrayList<JSONObject> object = (ArrayList<JSONObject>) request.getAttribute("JSONObjectList");
    for(int i =0;i<object.size();i++){
        out.println(object.get(i));
        }
    %>
</s:else>
