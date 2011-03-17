/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bios;

import java.util.*;
import JDOModel.*;
import JDODataManager.*;
import model.Round;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author yewwei.tay.2009
 */
public class AddBid extends ActionSupport implements SessionAware {

    private Student student;
    private String userId;
    private String courseCode;
    private String sectionCode;
    private String amount;
    private Map session;
    private double amt;
    private double bal;

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public void validate() {

        //student = (Student) session.get("student");
        student = StudentDataManager.getStudent("fred.ng.2009");

        if (student != null) {
            userId = student.getUserId();
        }

        if (amount.isEmpty()) {
            addActionError("Please enter a valid amount");
        } else {

            try {
                amt = Double.parseDouble(amount);
                if (amt <= 0) {
                    throw new Exception("Please enter a valid amount.");
                }
            } catch (NumberFormatException e) {

                addActionError("Please enter a valid amount.");

            } catch (Exception e) {

                addActionError(e.getMessage());
            }
        }

        if (BidDataManager.getStudentBids(userId).size() == 5) {

            addActionError("You may only bid for 5 courses at any one time.");

        }

        if (checkExamClash()) {

            addActionError("There is a clash in the exam schedule. Please select another course.");

        }

        if (checkLessonClash()) {

            addActionError("There is a clash in the timetable schedule. Please select another section.");

        }

        if (Round.getRound() == 2) {
            double minBid = SectionDataManager.getSectionMinBid(courseCode, sectionCode);

            if (minBid == 10.0 && amt < minBid) {
                addActionError("Your bid is too low. Bid placed must be $1 more than the min bid.");

            } else if (minBid != 10.0 && amt <= minBid) {
                addActionError("Your bid is too low. Bid placed must be $1 more than the min bid.");
            }
        }

        if (!checkBidAmountValid()) {
            addActionError("Insufficient eDollar.");
        }

    }

    public String execute() {

        StudentController.editStudenteDollar(student, bal);
        BidDataManager.addBid(userId, courseCode, sectionCode, amt);
        addActionMessage("You have placed a bid for " + courseCode + " " + sectionCode);

        return "SUCCESS";

    }

    private boolean checkExamClash() {

        List<Bid> studentBidList = BidDataManager.getStudentBids(userId);

        for (Bid bid : studentBidList) {

            Course biddedCourse = CourseDataManager.getCourse(bid.getCourseCode());

            if (biddedCourse.getExamDate().equals(CourseDataManager.getCourse(courseCode).getExamDate())
                    && biddedCourse.getExamStart().equals(CourseDataManager.getCourse(courseCode).getExamStart())) {

                return true;
            }
        }
        return false;
    }

    private boolean checkBidAmountValid() {

        double studentCredit = student.getEdollar();

        if (amt < studentCredit || amt == studentCredit) {

            bal = studentCredit - amt;

            return true;
        }
        return false;
    }

    private boolean checkLessonClash() {

        List<Bid> studentBidList = BidDataManager.getStudentBids(userId);

        Section bidSection = SectionDataManager.getSection(courseCode, sectionCode);

        if (!studentBidList.isEmpty()) {

            for (Bid bid : studentBidList) {

                Section biddedSection = SectionDataManager.getSection(bid.getCourseCode(), bid.getSectionCode());

                if ((biddedSection.getDay().equals(bidSection.getDay())
                        && biddedSection.getStart().equals(bidSection.getStart()))) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }
}
