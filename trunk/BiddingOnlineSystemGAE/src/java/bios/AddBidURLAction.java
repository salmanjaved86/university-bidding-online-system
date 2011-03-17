/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bios;

import model.*;
import DataManager.*;
import org.json.*;
import java.util.*;
import org.apache.struts2.interceptor.RequestAware;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author yewwei.tay.2009
 */
public class AddBidURLAction extends ActionSupport implements RequestAware {

    private Student student;
    private String userId;
    private String amount;
    private String courseCode;
    private String sectionCode;
    private Map request;
    private JSONObject obj;
    private double amt;
    private double bal;

    public JSONObject getObj() {
        return obj;
    }

    public void setObj(JSONObject obj) {
        this.obj = obj;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRequest(Map arg0) {
        request = arg0;
    }

    public Map getRequest() {
        return request;
    }

    public String execute() {
        boolean valid = true;
        try {
            student = StudentDataManager.getStudent(userId);

            if (student == null) {
                valid = false;
            }
            if (amount.isEmpty()) {
                valid = false;
            } else {
                amt = Double.parseDouble(amount);
                if (amt <= 0) {
                    valid = false;
                }
            }

            if (!checkPrerequisiteDone()) {
                valid =  false;
            }
            if (BidDataManager.getStudentBids(userId).size() == 5) {
                valid = false;
            }

            if (checkExamClash()) {
                valid = false;
            }

            if (checkLessonClash()) {
                valid = false;
            }

            if (Round.getRound() == 2) {
                double minBid = SectionDataManager.getSectionMinBid(courseCode, sectionCode);

                if (minBid == 10.0 && amt < minBid) {
                    valid = false;
                } else if (minBid != 10.0 && amt <= minBid) {
                    valid = false;
                }
            }

            if (!checkBidAmountValid()) {
                valid = false;
            }
        } catch (Exception e) {
            valid = false;
        }


        if (valid) {

            obj = new JSONObject();
            StudentController.editStudenteDollar(student, bal);
            BidDataManager.addBid(userId, courseCode, sectionCode, amt);

            try {
                if (Round.getRound() == 1) {
                    obj.put("status", "OK");
                }

                if (Round.getRound() == 2) {
                    obj.put("seat", SectionDataManager.getSectionVacancy(courseCode, sectionCode));
                    obj.put("price", SectionDataManager.getSectionMinBid(courseCode, sectionCode));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (!valid) {
            try {
                obj = new JSONObject();
                obj.put("status", "INVALID");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.put("JSONObject", obj);
        return "SUCCESS";
    }

    private boolean checkExamClash() {

        ArrayList<Bid> studentBidList = BidDataManager.getStudentBids(userId);

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

        ArrayList<Bid> studentBidList = BidDataManager.getStudentBids(userId);

        Section bidSection = SectionDataManager.getSection(courseCode, sectionCode);

        if (studentBidList.size() != 0) {

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

    private boolean checkPrerequisiteDone() {

        boolean valid = false;
        ArrayList<PrerequisiteCourse> prereqCourses = PrerequisiteCourseDataManager.getPrerequisiteCourse(courseCode);
        ArrayList<CourseCompleted> userCompletedCourseList = CourseCompletedManager.getCourseCompleted(userId);

        //check if the course has prerequisite
        if (prereqCourses.size() != 0) {

            int count = 0;

            // check if student has completed the prerequisite courses
            for (PrerequisiteCourse prereq : prereqCourses) {
                for (CourseCompleted courseCompleted : userCompletedCourseList) {
                    if (courseCompleted.getCourseCode().equals(prereq.getPrereqCourseCode())) {
                        count++;
                    }
                }
            }

            //if student has completed all the prerequisite courses
            if (count == prereqCourses.size()) {
                valid = true;
            }
        } else {
            valid = true;
        }
        return valid;
    }
}

