<%@taglib prefix="s" uri="/struts-tags"%>
<s:if test="#session.get('student')==null">
	<s:action name="loginView" executeResult="true" />
</s:if>
<s:else>
	<!-- Display action results here -->
	<div id="actionMessages"><s:actionmessage /></div>
	<%@page import="java.util.*,DataManager.*,model.*"%>
	<!-- Display user Bid data -->
	<table>
		<tr>
			<td class="day strong">Monday</td>
			<td class="day light">Section</td>
			<td class="day light">Time</td>
			<td class="day light">Venue</td>
			<td class="day light">Bid Amt</td>
			<td class="day light">Drop</td>
		</tr>
		<%
            ArrayList<Bid> monBids = (ArrayList<Bid>) request.getAttribute("monBids");
            for (Bid bid : monBids) {
                Section section = SectionDataManager.getSection(bid.getCourseCode(), bid.getSectionCode());
                Course course = CourseDataManager.getCourse(section.getCourseCode());
                if(bid.getBidStatus() == true){
                    out.print("<tr id='bidconfirmed'>");
                    out.print("<td id='bidconfirmed'><img src='../images/hand-success-12x12.png' alt=''> " + section.getCourseCode() + ": " + course.getTitle() + "</td>");
                    out.print("<td id='bidconfirmed'>" + section.getSectionCode() + "</td>");
                    out.print("<td id='bidconfirmed'>" + section.getStart() + "-" + section.getEnd() + "</td>");
                    out.print("<td id='bidconfirmed'>" + section.getVenue() + "</td>");

                    // Edit line below to insert "EDIT" ajax function
                    out.print("<td id='bidinprogress' courseCode='" + section.getCourseCode() +  "' sectionCode='" + section.getSectionCode() + "'>e$<span class='edit'>" + bid.getAmountTwoDecimal() + "</span>");
                    out.print("</td>");
                    out.print("<td id='bidconfirmed'>");
                    out.print("<img src='../images/drop.jpg' class='try drop' courseCode='" + section.getCourseCode() + "' "+"sectionCode='"+section.getSectionCode()+"' />");

                    //out.print("<a href='/BiddingOnlineSystem/secure/dropBid.action?courseCode=" + section.getCourseCode() + "'><img src='../images/drop.jpg' alt='Click to Drop Bid' /></a>");
                    out.print("</td>");
                    out.print("</tr>");
                }
                else{
                    out.print("<tr id='bidinprogress'>");
                    out.print("<td id='bidinprogress'><img src='../images/hand-bidding-12x12.png' alt=''> " + section.getCourseCode() + ": " + course.getTitle() + "</td>");
                    out.print("<td id='bidinprogress'>" + section.getSectionCode() + "</td>");
                    out.print("<td id='bidinprogress'>" + section.getStart() + "-" + section.getEnd() + "</td>");
                    out.print("<td id='bidinprogress'>" + section.getVenue() + "</td>");

                    // Edit line below to insert "EDIT" ajax function
                    out.print("<td id='bidinprogress' courseCode='" + section.getCourseCode() +  "' sectionCode='" + section.getSectionCode() + "'>e$<span class='edit'>" + bid.getAmountTwoDecimal() + "</span>");
                    out.print(" <img src='../images/pencil.png' class='pencil' alt='Click to Edit Bid' /></a>");
                    out.print("</td>");
                    out.print("<td id='bidinprogress'>");
                    out.print("<img src='../images/drop.jpg' class='try drop' courseCode='" + section.getCourseCode() + "' "+"sectionCode='"+section.getSectionCode()+"' />");

                    //out.print("<a href='/BiddingOnlineSystem/secure/dropBid.action?courseCode=" + section.getCourseCode() + "'><img src='../images/drop.jpg' alt='Click to Drop Bid' /></a>");
                    out.print("</td>");
                    out.print("</tr>");
                }
            }
        %>

		<tr>
			<td class="day strong">Tuesday</td>
			<td class="day light">Section</td>
			<td class="day light">Time</td>
			<td class="day light">Venue</td>
			<td class="day light">Bid Amt</td>
			<td class="day light">Drop</td>
		</tr>
		<%
            ArrayList<Bid> tueBids = (ArrayList<Bid>) request.getAttribute("tueBids");
            for (Bid bid : tueBids) {
                Section section = SectionDataManager.getSection(bid.getCourseCode(), bid.getSectionCode());
                Course course = CourseDataManager.getCourse(section.getCourseCode());
                if(bid.getBidStatus() == true){
                    out.print("<tr id='bidconfirmed'>");
                    out.print("<td id='bidconfirmed'><img src='../images/hand-success-12x12.png' alt=''> " + section.getCourseCode() + ": " + course.getTitle() + "</td>");
                    out.print("<td id='bidconfirmed'>" + section.getSectionCode() + "</td>");
                    out.print("<td id='bidconfirmed'>" + section.getStart() + "-" + section.getEnd() + "</td>");
                    out.print("<td id='bidconfirmed'>" + section.getVenue() + "</td>");

                    // Edit line below to insert "EDIT" ajax function
                    out.print("<td id='bidinprogress' courseCode='" + section.getCourseCode() +  "' sectionCode='" + section.getSectionCode() + "'>e$<span class='edit'>" + bid.getAmountTwoDecimal() + "</span>");
                    out.print("</td>");
                    out.print("<td id='bidconfirmed'>");
                    out.print("<img src='../images/drop.jpg' class='try drop' courseCode='" + section.getCourseCode() + "' "+"sectionCode='"+section.getSectionCode()+"' />");

                    //out.print("<a href='/BiddingOnlineSystem/secure/dropBid.action?courseCode=" + section.getCourseCode() + "'><img src='../images/drop.jpg' alt='Click to Drop Bid' /></a>");
                    out.print("</td>");
                    out.print("</tr>");
                }
                else{
                    out.print("<tr id='bidinprogress'>");
                    out.print("<td id='bidinprogress'><img src='../images/hand-bidding-12x12.png' alt=''> " + section.getCourseCode() + ": " + course.getTitle() + "</td>");
                    out.print("<td id='bidinprogress'>" + section.getSectionCode() + "</td>");
                    out.print("<td id='bidinprogress'>" + section.getStart() + "-" + section.getEnd() + "</td>");
                    out.print("<td id='bidinprogress'>" + section.getVenue() + "</td>");

                    // Edit line below to insert "EDIT" ajax function
                    out.print("<td id='bidinprogress' courseCode='" + section.getCourseCode() +  "' sectionCode='" + section.getSectionCode() + "'>e$<span class='edit'>" + bid.getAmountTwoDecimal() + "</span>");
                    out.print(" <img src='../images/pencil.png' class='pencil' alt='Click to Edit Bid' /></a>");
                    out.print("</td>");
                    out.print("<td id='bidinprogress'>");
                    out.print("<img src='../images/drop.jpg' class='try drop' courseCode='" + section.getCourseCode() + "' "+"sectionCode='"+section.getSectionCode()+"' />");

                    //out.print("<a href='/BiddingOnlineSystem/secure/dropBid.action?courseCode=" + section.getCourseCode() + "'><img src='../images/drop.jpg' alt='Click to Drop Bid' /></a>");
                    out.print("</td>");
                    out.print("</tr>");
                }
            }
        %>

		<tr>
			<td class="day strong">Wednesday</td>
			<td class="day light">Section</td>
			<td class="day light">Time</td>
			<td class="day light">Venue</td>
			<td class="day light">Bid Amt</td>
			<td class="day light">Drop</td>
		</tr>
		<%
            ArrayList<Bid> wedBids = (ArrayList<Bid>) request.getAttribute("wedBids");
            for (Bid bid : wedBids) {
                Section section = SectionDataManager.getSection(bid.getCourseCode(), bid.getSectionCode());
                Course course = CourseDataManager.getCourse(section.getCourseCode());
                if(bid.getBidStatus() == true){
                    out.print("<tr id='bidconfirmed'>");
                    out.print("<td id='bidconfirmed'><img src='../images/hand-success-12x12.png' alt=''> " + section.getCourseCode() + ": " + course.getTitle() + "</td>");
                    out.print("<td id='bidconfirmed'>" + section.getSectionCode() + "</td>");
                    out.print("<td id='bidconfirmed'>" + section.getStart() + "-" + section.getEnd() + "</td>");
                    out.print("<td id='bidconfirmed'>" + section.getVenue() + "</td>");

                    // Edit line below to insert "EDIT" ajax function
                    out.print("<td id='bidinprogress' courseCode='" + section.getCourseCode() +  "' sectionCode='" + section.getSectionCode() + "'>e$<span class='edit'>" + bid.getAmountTwoDecimal() + "</span>");
                    out.print("</td>");
                    out.print("<td id='bidconfirmed'>");
                    out.print("<img src='../images/drop.jpg' class='try drop' courseCode='" + section.getCourseCode() + "' "+"sectionCode='"+section.getSectionCode()+"' />");

                    //out.print("<a href='/BiddingOnlineSystem/secure/dropBid.action?courseCode=" + section.getCourseCode() + "'><img src='../images/drop.jpg' alt='Click to Drop Bid' /></a>");
                    out.print("</td>");
                    out.print("</tr>");
                }
                else{
                    out.print("<tr id='bidinprogress'>");
                    out.print("<td id='bidinprogress'><img src='../images/hand-bidding-12x12.png' alt=''> " + section.getCourseCode() + ": " + course.getTitle() + "</td>");
                    out.print("<td id='bidinprogress'>" + section.getSectionCode() + "</td>");
                    out.print("<td id='bidinprogress'>" + section.getStart() + "-" + section.getEnd() + "</td>");
                    out.print("<td id='bidinprogress'>" + section.getVenue() + "</td>");

                    // Edit line below to insert "EDIT" ajax function
                    out.print("<td id='bidinprogress' courseCode='" + section.getCourseCode() +  "' sectionCode='" + section.getSectionCode() + "'>e$<span class='edit'>" + bid.getAmountTwoDecimal() + "</span>");
                    out.print(" <img src='../images/pencil.png' class='pencil' alt='Click to Edit Bid' /></a>");
                    out.print("</td>");
                    out.print("<td id='bidinprogress'>");
                    out.print("<img src='../images/drop.jpg' class='try drop' courseCode='" + section.getCourseCode() + "' "+"sectionCode='"+section.getSectionCode()+"' />");

                    //out.print("<a href='/BiddingOnlineSystem/secure/dropBid.action?courseCode=" + section.getCourseCode() + "'><img src='../images/drop.jpg' alt='Click to Drop Bid' /></a>");
                    out.print("</td>");
                    out.print("</tr>");
                }
            }
        %>

		<tr>
			<td class="day strong">Thursday</td>
			<td class="day light">Section</td>
			<td class="day light">Time</td>
			<td class="day light">Venue</td>
			<td class="day light">Bid Amt</td>
			<td class="day light">Drop</td>
		</tr>
		<%
            ArrayList<Bid> thuBids = (ArrayList<Bid>) request.getAttribute("thuBids");
            for (Bid bid : thuBids) {
                Section section = SectionDataManager.getSection(bid.getCourseCode(), bid.getSectionCode());
                Course course = CourseDataManager.getCourse(section.getCourseCode());
                if(bid.getBidStatus() == true){
                    out.print("<tr id='bidconfirmed'>");
                    out.print("<td id='bidconfirmed'><img src='../images/hand-success-12x12.png' alt=''> " + section.getCourseCode() + ": " + course.getTitle() + "</td>");
                    out.print("<td id='bidconfirmed'>" + section.getSectionCode() + "</td>");
                    out.print("<td id='bidconfirmed'>" + section.getStart() + "-" + section.getEnd() + "</td>");
                    out.print("<td id='bidconfirmed'>" + section.getVenue() + "</td>");

                    // Edit line below to insert "EDIT" ajax function
                    out.print("<td id='bidinprogress' courseCode='" + section.getCourseCode() +  "' sectionCode='" + section.getSectionCode() + "'>e$<span class='edit'>" + bid.getAmountTwoDecimal() + "</span>");
                    out.print("</td>");
                    out.print("<td id='bidconfirmed'>");
                    out.print("<img src='../images/drop.jpg' class='try drop' courseCode='" + section.getCourseCode() + "' "+"sectionCode='"+section.getSectionCode()+"' />");

                    //out.print("<a href='/BiddingOnlineSystem/secure/dropBid.action?courseCode=" + section.getCourseCode() + "'><img src='../images/drop.jpg' alt='Click to Drop Bid' /></a>");
                    out.print("</td>");
                    out.print("</tr>");
                }
                else{
                    out.print("<tr id='bidinprogress'>");
                    out.print("<td id='bidinprogress'><img src='../images/hand-bidding-12x12.png' alt=''> " + section.getCourseCode() + ": " + course.getTitle() + "</td>");
                    out.print("<td id='bidinprogress'>" + section.getSectionCode() + "</td>");
                    out.print("<td id='bidinprogress'>" + section.getStart() + "-" + section.getEnd() + "</td>");
                    out.print("<td id='bidinprogress'>" + section.getVenue() + "</td>");

                    // Edit line below to insert "EDIT" ajax function
                    out.print("<td id='bidinprogress' courseCode='" + section.getCourseCode() +  "' sectionCode='" + section.getSectionCode() + "'>e$<span class='edit'>" + bid.getAmountTwoDecimal() + "</span>");
                    out.print(" <img src='../images/pencil.png' class='pencil' alt='Click to Edit Bid' /></a>");
                    out.print("</td>");
                    out.print("<td id='bidinprogress'>");
                    out.print("<img src='../images/drop.jpg' class='try drop' courseCode='" + section.getCourseCode() + "' "+"sectionCode='"+section.getSectionCode()+"' />");

                    //out.print("<a href='/BiddingOnlineSystem/secure/dropBid.action?courseCode=" + section.getCourseCode() + "'><img src='../images/drop.jpg' alt='Click to Drop Bid' /></a>");
                    out.print("</td>");
                    out.print("</tr>");
                }
            }
        %>

		<tr>
			<td class="day strong">Friday</td>
			<td class="day light">Section</td>
			<td class="day light">Time</td>
			<td class="day light">Venue</td>
			<td class="day light">Bid Amt</td>
			<td class="day light">Drop</td>
		</tr>
		<%
            ArrayList<Bid> friBids = (ArrayList<Bid>) request.getAttribute("friBids");
            for (Bid bid : friBids) {
                Section section = SectionDataManager.getSection(bid.getCourseCode(), bid.getSectionCode());
                Course course = CourseDataManager.getCourse(section.getCourseCode());
                if(bid.getBidStatus() == true){
                    out.print("<tr id='bidconfirmed'>");
                    out.print("<td id='bidconfirmed'><img src='../images/hand-success-12x12.png' alt=''> " + section.getCourseCode() + ": " + course.getTitle() + "</td>");
                    out.print("<td id='bidconfirmed'>" + section.getSectionCode() + "</td>");
                    out.print("<td id='bidconfirmed'>" + section.getStart() + "-" + section.getEnd() + "</td>");
                    out.print("<td id='bidconfirmed'>" + section.getVenue() + "</td>");

                    // Edit line below to insert "EDIT" ajax function
                    out.print("<td id='bidinprogress' courseCode='" + section.getCourseCode() +  "' sectionCode='" + section.getSectionCode() + "'>e$<span class='edit'>" + bid.getAmountTwoDecimal() + "</span>");
                    out.print("</td>");
                    out.print("<td id='bidconfirmed'>");
                    out.print("<img src='../images/drop.jpg' class='try drop' courseCode='" + section.getCourseCode() + "' "+"sectionCode='"+section.getSectionCode()+"' />");

                    //out.print("<a href='/BiddingOnlineSystem/secure/dropBid.action?courseCode=" + section.getCourseCode() + "'><img src='../images/drop.jpg' alt='Click to Drop Bid' /></a>");
                    out.print("</td>");
                    out.print("</tr>");
                }
                else{
                    out.print("<tr id='bidinprogress'>");
                    out.print("<td id='bidinprogress'><img src='../images/hand-bidding-12x12.png' alt=''> " + section.getCourseCode() + ": " + course.getTitle() + "</td>");
                    out.print("<td id='bidinprogress'>" + section.getSectionCode() + "</td>");
                    out.print("<td id='bidinprogress'>" + section.getStart() + "-" + section.getEnd() + "</td>");
                    out.print("<td id='bidinprogress'>" + section.getVenue() + "</td>");

                    // Edit line below to insert "EDIT" ajax function
                    out.print("<td id='bidinprogress' courseCode='" + section.getCourseCode() +  "' sectionCode='" + section.getSectionCode() + "'>e$<span class='edit'>" + bid.getAmountTwoDecimal() + "</span>");
                    out.print(" <img src='../images/pencil.png' class='pencil' alt='Click to Edit Bid' /></a>");
                    out.print("</td>");
                    out.print("<td id='bidinprogress'>");
                    out.print("<img src='../images/drop.jpg' class='try drop' courseCode='" + section.getCourseCode() + "' "+"sectionCode='"+section.getSectionCode()+"' />");

                    //out.print("<a href='/BiddingOnlineSystem/secure/dropBid.action?courseCode=" + section.getCourseCode() + "'><img src='../images/drop.jpg' alt='Click to Drop Bid' /></a>");
                    out.print("</td>");
                    out.print("</tr>");
                }
            }
        %>
	</table>
</s:else>






