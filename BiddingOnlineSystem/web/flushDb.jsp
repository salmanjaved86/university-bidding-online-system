<%@page import="java.sql.*, model.ConnectionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome to Merlion University : FlushDB Page</title>
</head>
<body>
<h1>Flush DB</h1>
<%

        String test = "";
        //Truncate student

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE STUDENT");
            pstmt.executeUpdate();
            test += "Student table flushed. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }



        // Truncate bid

        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE BID");
            pstmt.executeUpdate();
            test += "Bid table flushed. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }




        // Truncate course

        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE COURSE");
            pstmt.executeUpdate();
            test += "Course table flushed. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }




        // Truncate course_completed

        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE COURSE_COMPLETED");
            pstmt.executeUpdate();
            test += "Course completed table flushed. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }


        // Truncate prerequisite

        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE PREREQUISITE");
            pstmt.executeUpdate();
            test += "Prerequisite table flushed. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }



        // Truncate section

        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE SECTION");
            pstmt.executeUpdate();
            test += "Section table flushed. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }


    


        %>

<%=test%>

</body>
</html>
