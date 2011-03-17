<%-- 
    Document   : jdotest
    Created on : Oct 12, 2010, 4:07:13 PM
    Author     : Administrator
--%>
<%@page import="JDODataManager.SectionDataManager"%>
<%@page import="JDODataManager.CourseDataManager"%>
<%@page import="JDODataManager.BidDataManager"%>
<%@page import="JDODataManager.StudentDataManager"%>
<%@page import="JDODataManager.PMF"%>
<%@page
	import="javax.jdo.PersistenceManager, JDOModel.*, java.util.*, javax.jdo.PersistenceManagerFactory, javax.jdo.JDOHelper, javax.jdo.Transaction, javax.jdo.Extent, javax.jdo.Query"%>
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

        String printer ="";
        PersistenceManager pm = PMF.get().getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Extent e = pm.getExtent(JDOModel.Student.class,true);
            Query q = pm.newQuery(e);
            //q.setOrdering("edollar ascending");

            Collection c = (Collection)q.execute();
            Iterator iter = c.iterator();
            while (iter.hasNext()){
                    Student p = (Student)iter.next();
                    printer += p.getName();
                    printer += p.getPassword();
                    printer += p.getUserId();
             }
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

        Student student = StudentDataManager.getStudent("fred.ng.2009");
        String outer = student.getName();
        outer += student.getUserId();

        //ArrayList<Bid> temp = BidDataManager.getStudentBids("fred.ng.2009");
        //outer+= temp.get(0).getCourseCode() + temp.get(0).getSectionCode();
            
        //List<Course> temp = CourseDataManager.retrieveAll();
        //outer+= temp.get(0).getCourseCode() + temp.get(0).getTitle();


        List<Course> temporary = CourseDataManager.retrieveAll();
        for (Course course : temporary){
        outer+= course.getCourseCode() + course.getTitle();
        outer+= "came here<br/>";
        }

        //String courseCode = "IS105";
        //sectionReturn = SectionDataManager.getSectionWithCourseCode(courseCode);


        //List<Section> tempor = SectionDataManager.retrieveAll();
        //for (Section section : tempor){
        //outer+= section.getCourseCode() + section.getSectionCode();
        //outer+= "came here";
        //}

        //List<Bid> creator = BidDataManager.retrieveAll();
        //for (Bid bider : creator){
        //outer+= bider.getCourseCode() + bider.getSectionCode() + bider.getUserId();
        //outer+= "came here";
        //}
        %>
<%=printer%><p />
<%=outer%>
</body>
</html>
