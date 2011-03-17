<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.text.*"%>
<%@page import="java.util.Date"%>
<%
    String errorMessage = request.getParameter("errorMessage");
    Date currentDate = new Date();
    DateFormat formatDate = new SimpleDateFormat("EEEE, dd MMM    yyyy");
%>
<html>
<head>
<title>Welcome to Merlion University : Login Panel</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/css-main.css" rel="stylesheet" type="text/css"
	media="screen" />
<script language="JavaScript" type="text/javascript">
            //Referenced from http://www.jsmadeeasy.com/
            var timerID = null;
            var timerRunning = false;
            var id, pause=0, position=0;

            function stopclock (){
                if(timerRunning)
                    clearTimeout(timerID);
                timerRunning = false;
            }

            function showtime () {
                var now = new Date();
                var hours = now.getHours();
                var minutes = now.getMinutes();
                var seconds = now.getSeconds()
                var timeValue = "" + ((hours >12) ? hours -12 :hours)
                timeValue += ((minutes < 10) ? ":0" : ":") + minutes
                timeValue += ((seconds < 10) ? ":0" : ":") + seconds
                timeValue += (hours >= 12) ? " PM" : " AM"
                document.clock.face.value = timeValue;
                timerID = setTimeout("showtime()",1000);
                timerRunning = true;
            }
            function startclock () {
                stopclock();
                showtime();
            }
        </script>
</head>
<body onLoad="startclock()">
<div id="centerpanel">
<div id="loginleftpane"><img src="images/login-leftpane.png" /></div>
<div id="logincenterlogo"><img src="images/login-centerlogo.png" />
<div id="countdownpane">
<% out.print(""+formatDate.format(currentDate)); %>
<form name="clock"><input type="text" id="clocktime" name="face"
	size=10 value=""></form>
</div>
<s:form id="loginform" action="login" method="post">
	<s:textfield name="userId" label="Username" />
	<s:password name="password" label="Password" />
	<s:submit id="loginbtn" value="Login" />
</s:form></div>
<%
                //prints error message if input has error
                if (errorMessage != null){
                    if (errorMessage.equals("noround")){
                        out.println("<div id='errorpane'>");
                        out.println("<div id='errorheader'>*ROUND ERROR:</div>");
                        out.println("<div id='errortext'>>.  Bid round has not started.");
                        out.println("<br>>.  Please try again later.</div>");
                        out.println("</div>");
                    }
                    else {
                        out.println("<div id='errorpane'>");
                        out.println("<div id='errorheader'>*INPUT ERROR:</div>");
                        out.println("<div id='errortext'>>.  "+errorMessage);
                        out.println("<br>>.  Please try again.</div>");
                        out.println("</div>");
                    }
                }
            %>
<div id="logincenterlowercurve"><img
	src="images/login-lowercurve.png" /></div>
<div id="copyrightpane">&copy; Copyright 2010 G3T2 | All Rights
Reserved</div>
</div>
</body>
</html>
