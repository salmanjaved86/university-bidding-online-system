<%@page import="java.text.*"%>
<%@page import="JDOModel.*"%>
<%@page import="JDODataManager.*"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>

<%
               // int roundnumber = Round.getRound();
    %>
<html>
<head>
<title>Welcome to Merlion University : Course Search</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../css/css-display-addnewbid.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="../css/css-display-viewsections.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="../shadowbox/shadowbox.css" rel="stylesheet" type="text/css"
	media="screen" />

<sj:head jqueryui="true" />
<script type="text/javascript" src="../shadowbox/shadowbox.js"></script>
<script type="text/javascript">
                $(function(){
                    $.subscribe('colorchange', function(event,data) {
                        //$("body").css("background-color", "yellow");
                        //$("h1").append($("#courses").val());
                        var colon = $("#courses").val().indexOf(":");
                        var courseCode = $("#courses").val().substring(0,colon);
                        $("#sections").load('sections?courseCode=' + courseCode, function(){
                            //alert('Load was performed.'+ courseCode);
                        });

                    });

                    $("form#sections").submit(function() {
                        alert('submit was clicked');
                        var sectionCode = $("form input:radio").val();
                        $.get("'bids?courseCode=" + courseCode + "&sectionCode=" + sectionCode, function(data){
                            $('#resulter').html(data);
                        });
                    });
                });

                Shadowbox.init();
            </script>
</head>
<body>
<div id="centerpanel">
<div id="displayleftpane"><img
	src="../images/display-bioslogo.png" alt="" class="leftlogo" />
<div id="edolllars"><span id="edolllarscurrency">e$</span><%=StudentDataManager.getStudent("fred.ng.2009").getEdollar()/*getEdollarTwoDecimal()*/%></div>
<div id="accounttotaltext">My Account Total</div>
<div id="roundnumber">round</div>
<div id="currentroundtext">Current Bid Round</div>
<div id="bidroundstatus">
<% /* if (Round.getRoundStart() == true) {
                                        out.println("<span class='success'>in progress</span>");
                                    } else {
                                        out.println("<span class='failure'>has ended</span>");
                                    }*/
                        %>
</div>
<div id="bidroundstatustext">Bid Round Status</div>
</div>
<div id="displayfloattop"><img
	src="../images/display-biddingpanel.png" alt="" /></div>
<div id="displayallmods"><img src="../images/display-viewall.png"
	alt="" />
<div id="displayallmodstext"><a href="../viewAllCourses.jsp"
	rel="shadowbox" id="displayallmodsclick">&nabla; View All Modules</a></div>
</div>
<div id="displaytoprightpane"><span id="nametext"><%=StudentDataManager.getStudent("fred.ng.2009").getName()/*getEdollarTwoDecimal()*/%></span>
<div id="links">|&nbsp;<a href="display.action">Home</a>&nbsp;
|&nbsp;<a href="<s:url value='logout.action'/>">Logout</a></div>
</div>
<div id="displaybottomrightpane">
<div id="biddedtimetable">
<div id="biddedtimetableheader"><img
	src="../images/display-addnewbid.png" alt="" /></div>
<div id="biddedtimetablemsg"></div>
<div id="searchbar"><span id="searchheader">Search course:
</span><sj:autocompleter id="courses" list="%{courses}"
	onCompleteTopics="colorchange" /></div>

<div id="sections"></div>

</div>
</div>
<div id="displayfloatlogo"><img
	src="../images/display-twothreeonelogo.png" /></div>
<div id="displayfloatbottom"></div>
</div>
</body>
</html>