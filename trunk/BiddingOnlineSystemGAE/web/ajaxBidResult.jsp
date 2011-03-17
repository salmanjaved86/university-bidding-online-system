<%@taglib prefix="s" uri="/struts-tags"%>
<s:actionmessage />
<% 
    String message = (String) request.getAttribute("message");


    if(message!=null){
        out.println(message);
      }

%>