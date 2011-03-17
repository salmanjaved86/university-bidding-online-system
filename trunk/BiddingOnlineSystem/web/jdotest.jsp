<%-- 
    Document   : jdotest
    Created on : Oct 12, 2010, 4:07:13 PM
    Author     : Administrator
--%>
<%@page
	import="javax.jdo.PersistenceManager, bios.*, java.util.*, javax.jdo.PersistenceManagerFactory, javax.jdo.JDOHelper, javax.jdo.Transaction, javax.jdo.Extent, javax.jdo.Query"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
</head>
<body>
<h1>JDO Test</h1>

<%
        String printer = "Hello";
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        PersistenceManager pm = pmf.getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Extent e = pm.getExtent(bios.Student2.class,true);
            Query q = pm.newQuery(e);
            //q.setOrdering("edollar ascending");

            Collection c = (Collection)q.execute();
            Iterator iter = c.iterator();
            while (iter.hasNext()){
                    Student2 p = (Student2)iter.next();
                    printer += p.getName();
             }
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

        %>
<%=printer%>
</body>
</html>
