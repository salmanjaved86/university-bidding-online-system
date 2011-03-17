<%--
    Document   : index
    Created on : Oct 12, 2010, 3:34:51 PM
    Author     : Administrator
--%>
<%@page import="javax.jdo.Query"%>
<%@page import="java.util.List"%>
<%@page import="JDOModel.Student"%>
<%@page import="javax.jdo.PersistenceManager"%>
<%@page import="JDODataManager.PMF"%>
<%@page import="java.sql.*, model.ConnectionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
</head>
<body>

<%

        String test = "";
        //Truncate student

        PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery("select from " + Student.class.getName());
        List<Student> objs = (List<Student>) query.execute();
        pm.deletePersistentAll(objs);
        test += "Student table flushed. ";

        // Truncate bid


        // Truncate course



        // Truncate course_completed


        // Truncate prerequisite


        // Truncate section


    


        %>

<%=test%>

</body>
</html>
