<%@page import="java.sql.*, java.util.*, DataManager.*, model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome to Merlion University : DBWatch Page</title>
</head>
<body>
<h1>DB Watch</h1>
<%
        String printer = "Hello<p/>";

        //Student Info

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("select * from STUDENT");
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                String studentId = rs.getString(1);
                String eDollar = rs.getString(2);
                String name = rs.getString(3);
                String password = rs.getString(4);
                String school = rs.getString(5);
                printer += studentId + "\t" + eDollar + "\t" + name + "\t" + password + "\t" + eDollar + "<br/>";//;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }


        // Bid Info


        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("select * from SUCCESSFULBID");
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                String userid = rs.getString(1);
                String amount = rs.getString(2);
                String code = rs.getString(3);
                String section = rs.getString(4);
                printer += userid + "\t" + amount + "\t" + code + "\t" + section + "<br/>";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }


        // Courses info

        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("select * from COURSE");
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                String coursecode = rs.getString(1);
                String school = rs.getString(2);
                String title = rs.getString(3);
                String description = rs.getString(4);
                String examDate = rs.getString(5);
                String examStart = rs.getString(6);
                String examEnd = rs.getString(7);
                printer += coursecode + "\t" + school + "\t" + title + "\t" + description + "\t" + examDate + "\t" + examStart + "\t" + examEnd + "<br/>";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }


        // Section info

        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("select * from SECTION");
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                String course = rs.getString(1);
                String section = rs.getString(2);
                String day = rs.getString(3);
                String start = rs.getString(4);
                String end = rs.getString(5);
                String instructor = rs.getString(6);
                String venue = rs.getString(7);
                String size = rs.getString(8);
                printer += course + "\t" + section + "\t" + day + "\t" + start + "\t" + end + "\t" + instructor + "\t" + venue + "\t" + size + "<br/>";
            }

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
            pstmt = (PreparedStatement) conn.prepareStatement("select * from BID");
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                String userid = rs.getString(1);
                String amount = rs.getString(2);
                String code = rs.getString(3);
                String section = rs.getString(4);
                printer += userid + "\t" + amount + "\t" + code + "\t" + section + "<br/>";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }


        %>
<%=printer%>
</body>
</html>
