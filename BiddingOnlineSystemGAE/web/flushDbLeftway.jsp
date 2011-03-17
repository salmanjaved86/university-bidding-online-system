<%-- 
    Document   : flushDbLeftway
    Created on : Oct 14, 2010, 11:42:13 AM
    Author     : Administrator
--%>
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

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("DROP TABLE IF EXISTS BID,COURSE,COURSE_COMPLETED,PREREQUISITE,SECTION,STUDENT;");
            pstmt.executeUpdate();
            test += "All tables dropped. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }




        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("create table STUDENT(USERID varchar(20) not null,NAME varchar(50),PASSWORD varchar(50),SCHOOL varchar (10),eDOLLAR double,constraint STUDENT_PK primary key (USERID))");
            pstmt.executeUpdate();
            test += "Student table created. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }




        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("create table COURSE(COURSECODE varchar(10) NOT NULL, SCHOOL varchar(5),TITLE varchar(50),DESCRIPTION varchar(500),EXAMDATE varchar(50),EXAMSTART varchar(50),EXAMEND varchar(50),constraint COURSE_PK primary key (COURSECODE))");
            pstmt.executeUpdate();
            test += "Course table created. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }



        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("create table SECTION(COURSECODE varchar(10) not null,SECTIONCODE varchar(10) not null,DAY varchar(10),START varchar(50),END varchar(50),INSTRUCTOR varchar(50),VENUE varchar(50),SIZE int,CONSTRAINT SECTION_PK PRIMARY KEY (COURSECODE,SECTIONCODE))");
            pstmt.executeUpdate();
            test += "Section table created. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }



                conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("create table BID(USERID varchar(20) NOT NULL,COURSECODE varchar(10) NOT NULL,SECTIONCODE varchar(10) NOT NULL,AMOUNT double,CONSTRAINT BID_PK PRIMARY KEY (USERID,COURSECODE,SECTIONCODE))");
            pstmt.executeUpdate();
            test += "Bid table created. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }



        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("CREATE TABLE COURSE_COMPLETED(USERID varchar(20) NOT NULL,COURSECODE varchar(10) NOT NULL,CONSTRAINT COURSE_COMPLETED_PK PRIMARY KEY (USERID,COURSECODE))");
            pstmt.executeUpdate();
            test += "Course completed table created. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }





        %>
</body>
</html>
