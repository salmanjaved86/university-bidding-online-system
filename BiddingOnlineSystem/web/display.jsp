<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@page import="java.text.*"%>
<%@page import="model.*,java.util.*,DataManager.*;"%>
<s:if test="#session.get('student')==null">
	<s:action name="loginView" executeResult="true" />
</s:if>
<s:else>
	<%
          int roundnumber = Round.getRound();      
    %>
	<html>
	<head>
	<title>Welcome to Merlion University : Student Panel</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../css/css-display.css" rel="stylesheet" type="text/css"
		media="screen" />
	<link href="../css/css-display-timetable.css" rel="stylesheet"
		type="text/css" media="screen" />
	<link href="../css/jquery.fastconfirm.css" rel="stylesheet"
		type="text/css" media="screen" />
	<link href="../shadowbox/shadowbox.css" rel="stylesheet"
		type="text/css" media="screen" />

	<sj:head jqueryui="true" />
	<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="../js/jquery.fastconfirm.js"></script>
	<script type="text/javascript" src="../js/jquery.jeditable.js"></script>
	<script type="text/javascript" src="../shadowbox/shadowbox.js"></script>
	<script type="text/javascript">
                $(function() {
                    var $bidToEdit = "";

                    $('#check').live('click', function() {
                        var courseCode = $("#mything").parent().parent().attr('courseCode');
                        var sectionCode = $("#mything").parent().parent().attr('sectionCode');
                        var amount = $("#mything").val();

                        $.get("edit?courseCode=" + courseCode + "&sectionCode=" + sectionCode + "&amount="  + amount, function(data){
                            $('#actionMessages').html(data);
                            message = $('#actionMessages').html().substr(42, 43);

                            $("#mything").next().fadeOut('slow').remove();

                            if (message.charAt(0)=="E"){
                                $("#mything").replaceWith($bidToEdit); //amount
                                $('#actionMessages').removeClass('success')
                                $('#actionMessages').addClass('failure');
                            }
                            else {
                                $.get("updateEDollar", function(data){
                                    $('#ebalance').html(data);
                                });
                                $("#mything").replaceWith(amount); //amount
                                $('#actionMessages').removeClass('failure');
                                $('#actionMessages').addClass('success');
                            }
                        });

                        $('.pencil').fadeIn('slow');
                    });


                    $('.pencil').each(function(){
                        $(this).click(function() {
                            $('.pencil').fadeOut('slow');
                            var amount = $(this).prev().html();
                            $bidToEdit = amount;
                            $(this).prev().replaceWith("<span class='edit'><input type='text' maxlength='6' size='4' id='mything' value='" + amount + "'>" + "<img src='../images/check_mark.gif' id='check'></span>");
                        });
                    });


                    $('.try').each(function() {
                        $(this).click(function() {
                            var courseCode = $(this).attr('courseCode');
                            var sectionCode = $(this).attr('sectionCode');
                            var objectToClose = $(this).parent().parent();
                            $(this).fastConfirm({
                                position: "top",
                                questionText: "Are you sure you want to drop this class?",
                                onProceed: function(trigger) {

                                    $.get("dropBid?courseCode=" + courseCode + "&sectionCode=" + sectionCode, function(data){
                                        $('#actionMessages').html(data);
                                        //$('#ebalance').fadeOut('slow');
                                        $.get("updateEDollar", function(data){
                                            $('#ebalance').html(data);
                                        });
                                        objectToClose.fadeOut('slow');
                                    });
                                },
                                onCancel: function(trigger) {
                                    //do nothing
                                }
                            });
                        });
                    });
                });

                Shadowbox.init();

                <%

                    Student student = (Student) session.getAttribute("student");

                    ArrayList<String> message = SystemMetaDataManager.readFromDataStore(student.getUserId());
                    if (!message.isEmpty()) {
                        out.print("window.onload = function() {");
                        out.print("Shadowbox.open({");
                        out.print("content: '<div id=\"badnews\">");

                        for (String string : message) {
                            out.print(string);
                            out.print("<br>");
                        }

                        out.print("',");
                        out.print("player: \"html\",");
                        out.print("title: \"Oops.. Here's Some Bad News\",");
                        out.print("height:     150,");
                        out.print("width:      450");
                        out.print("});");
                        out.print("};");

                        SystemMetaDataManager.deleteFromDataStore(student.getUserId());
                    }

                %>
            </script>
	</head>
	<body>
	<div id="centerpanel">
	<div id="displayleftpane"><img
		src="../images/display-bioslogo.png" alt="" />
	<div id="edolllars"><span id="edolllarscurrency">e$</span><span
		id="ebalance"><s:property
		value="#session.get('student').getEdollarTwoDecimal()" /></span></div>
	<div id="accounttotaltext">My Account Total</div>
	<div id="roundnumber">round <%=roundnumber%></div>
	<div id="currentroundtext">Current Bid Round</div>
	<div id="bidroundstatus">
	<%  if (Round.getRoundStart() == true) {
                                        out.println("<span class='success'>in progress</span>");
                                    } else {
                                        out.println("<span class='failure'>has ended</span>");
                                    }
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
	<div id="displaytoprightpane"><span id="nametext"><s:property
		value="#session.get('student').getName()" /></span>
	<div id="links">|&nbsp;<a href="display.action">Home</a>&nbsp;
	|&nbsp;<a href="<s:url value='logout.action'/>">Logout</a></div>
	</div>
	<div id="displaybottomrightpane">
	<div id="biddedtimetable">
	<div id="biddedtimetableheader"><img
		src="../images/display-biddedtimetable.png" alt="" /></div>
	<% if (Round.getRoundStart() == true) {%>
	<div id="addbidbtn"><a
		href="<s:url value='autocompleter.action'/>"><input type="submit"
		value="Add New Bid" id="addbidbtncss" onclick="checkRoundStart()"></a></div>
	<%}%> <s:action name="viewBids" executeResult="true" />
	<div id="bidlegend"><span id="bidsuccess"><img
		src="../images/hand-success-12x12.png" alt=""> - Bid Successful</span>&nbsp;&nbsp;
	<span id="bidprogress"><img
		src="../images/hand-bidding-12x12.png" alt=""> - Bidding in
	Progress</span></div>
	</div>
	</div>
	<div id="displayfloatlogo"><img
		src="../images/display-twothreeonelogo.png" /></div>
	<div id="displayfloatbottom"></div>
	</div>
	</body>
	</html>
</s:else>