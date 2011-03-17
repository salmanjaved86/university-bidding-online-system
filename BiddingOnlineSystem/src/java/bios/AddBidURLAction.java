package bios;
import model.*;
import DataManager.*;
import org.json.*;
import java.util.*;
import org.apache.struts2.interceptor.RequestAware;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author yewwei.tay.2009
 */
public class AddBidURLAction extends ActionSupport implements RequestAware, SessionAware {
    /**
     * initializing private attributes
     */
    private Student student;
    private String userid;
    private String amount;
    private String course;
    private String section;
    private Map request;
    private JSONObject obj;
    private double amt;
    private double bal;
    private String password;
    private Map session;

    /**
     * Return the password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return the session
     * @return session
     */
    public Map getSession() {
        return session;
    }

    /**
     * Set the session
     * @param session the session to set
     */
    public void setSession(Map session) {
        this.session = session;
    }

    /**
     * Return the JSON object
     * @return obj
     */
    public JSONObject getObj() {
        return obj;
    }

    /**
     * Set the JSON object
     * @param obj the obj to set
     */
    public void setObj(JSONObject obj) {
        this.obj = obj;
    }

    /**
     * Return the amount
     * @return amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Set the amount
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Return the course code
     * @return the courseCode
     */
    public String getCourse() {
        return course;
    }

    /**
     * Set the course
     * @param course the course to set
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     * Return the section
     * @return the sectionCode
     */
    public String getSection() {
        return section;
    }

    /**
     * Set the section code of the course
     * @param sectionCode the sectionCode to set
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * Return the userid of the student
     * @return userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Set the userid of the student
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * Set the request map
     * @param arg0 the request to set
     */
    public void setRequest(Map arg0) {
        request = arg0;
    }

    /**
     * Return the request map
     * @return request
     */
    public Map getRequest() {
        return request;
    }

    /**
     * Method generates the JSON responses when a bid is added
     * @return success
     */
    public String execute() {

        String ADMIN_PASSWORD = getText("admin.password");
        if (!ADMIN_PASSWORD.equals(password)) {
            try {
                obj = new JSONObject();
                //display invalid
                obj.put("status", "INVALID");
                request.put("JSONObject", obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            boolean valid = true;
            try {
                student = StudentDataManager.getStudent(userid);
                // check if student is valid
                if (student == null) {
                    valid = false;
                }
                //check if amount is entered
                if (amount.isEmpty()) {
                    valid = false;
                } else {
                    amt = Double.parseDouble(amount);
                    //check for negative and O amount
                    if (amt <= 0) {
                        valid = false;
                    }
                }
                //check whether the student has completed the particular course
                if (checkCourseCompleted()) {
                    valid = false;
                    //check if the prerequisite of the course is completed
                } else if (!checkPrerequisiteDone()) {
                    valid = false;
                } //check for sufficient balance
                else if (!checkBidAmountValid()) {
                    valid = false;
                }//check if the student exceeded 5 bids
                else if (BidDataManager.getStudentBids(userid).size() + SuccessfulBidDataManager.getStudentSuccessfulBids(userid).size() == 5) {
                    valid = false;
                    //check if module has already bidded before and is a successful bid
                } else if (checkBidded()) {
                    valid = false;
                    //check if there is exam clash
                } else if (checkExamClash()) {
                    valid = false;
                    //check is there is lesson clash
                } else if (checkLessonClash()) {
                    valid = false;
                    //check if round 1, if yes, amount must be higher than 10eDollars
                } else if (Round.getRound() == 1) {
                    if (amt < 10.0) {
                        valid = false;
                    }
                } //check if round 2, the amount must be higher than the minimum bid of round 1
                else if (Round.getRound() == 2) {
                    //double minBid = SectionDataManager.getSectionMinBid(courseCode, sectionCode);
                    double minBid = SectionDataManager.getSection(course, section).getMinBid();
                    if (minBid == 10.0 && amt < minBid) {
                        valid = false;
                    } else if (minBid != 10.0 && amt <= minBid) {
                        valid = false;
                    }
                }
            } catch (Exception e) {
                valid = false;
            }
            //if everything user had entered is valid, add the bid and deduct the bid amount from student balance
            if (valid) {
                double studentCredit = student.getEdollar();
                bal = studentCredit - amt;
                //create a new JSON object
                obj = new JSONObject();
                BidDataManager.addBid(student, course, section, amt);
                try {
                    if (Round.getRound() == 1) {
                        obj.put("status", "OK");
                    }
                    if (Round.getRound() == 2) {
                        //display number of vacancy left after round is cleared
                        obj.put("seat", SectionDataManager.getSectionVacancy(course, section));
                        //display price of the minimum bid when the round is cleared
                        // obj.put("price", SectionDataManager.getSectionMinBid(courseCode, sectionCode));
                        obj.put("price", SectionDataManager.getSection(course, section).getMinBid());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //if one of the checks failed
            } else if (!valid) {
                try {
                    obj = new JSONObject();
                    //display invalid
                    obj.put("status", "INVALID");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            request.put("JSONObject", obj);
        }
        return "SUCCESS";
    }

    /**
     * This method checks if there is exam clash by comparing the exams date/time of the student's bids
     * and the exam data/time of the bid to be added
     * @return true if exam clash, else return false
     */
    private boolean checkExamClash() {
        ArrayList<Bid> studentBidList = BidDataManager.getStudentBids(userid);
        ArrayList<Bid> studentSuccessfulBidList = SuccessfulBidDataManager.getStudentSuccessfulBids(userid);
        //iterate through student's current bids
        for (Bid bid : studentBidList) {
            Course biddedCourse = CourseDataManager.getCourse(bid.getCourseCode());
            if (biddedCourse.getExamDate().equals(CourseDataManager.getCourse(course).getExamDate())
                    && biddedCourse.getExamStart().equals(CourseDataManager.getCourse(course).getExamStart())) {
                return true;
            }
        }
        //iterate through student's successful bids
        for (Bid bid : studentSuccessfulBidList) {
            Course biddedCourse = CourseDataManager.getCourse(bid.getCourseCode());
            if (biddedCourse.getExamDate().equals(CourseDataManager.getCourse(course).getExamDate())
                    && biddedCourse.getExamStart().equals(CourseDataManager.getCourse(course).getExamStart())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method check if there is sufficient credit in the student's balance
     * @return true if there is sufficient credit, false if insufficient
     */
    private boolean checkBidAmountValid() {
        double studentCredit = student.getEdollar();
        if (amt < studentCredit || amt == studentCredit) {
            return true;
        }
        return false;
    }

    /**
     * This method checks if there is lessons clash by comparing the time of the student's bids
     * and the time of the bid to be added
     * @return true if lesson clash, else return false
     */
    private boolean checkLessonClash() {
        ArrayList<Bid> studentBidList = BidDataManager.getStudentBids(userid);
        ArrayList<Bid> studentSuccessfulBidList = SuccessfulBidDataManager.getStudentSuccessfulBids(userid);
        Section bidSection = SectionDataManager.getSection(course, section);
        for (Bid bid : studentBidList) {
            Section biddedSection = SectionDataManager.getSection(bid.getCourseCode(), bid.getSectionCode());
            if ((biddedSection.getDay().equals(bidSection.getDay())
                    && biddedSection.getStart().equals(bidSection.getStart()))) {
                return true;
            }
        }
        for (Bid bid : studentSuccessfulBidList) {
            Section biddedSection = SectionDataManager.getSection(bid.getCourseCode(), bid.getSectionCode());
            //check if day and time clash
            if ((biddedSection.getDay().equals(bidSection.getDay())
                    && biddedSection.getStart().equals(bidSection.getStart()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if there is prerequisite modules required for the bid to be added.
     * If there is prerequisite modules, check if the modules are completed
     * @return true if prerequisites are completed, else turn false
     */
    private boolean checkPrerequisiteDone() {
        boolean valid = false;
        ArrayList<PrerequisiteCourse> prereqCourses = PrerequisiteCourseDataManager.getPrerequisiteCourse(course);
        ArrayList<CourseCompleted> userCompletedCourseList = CourseCompletedManager.getCourseCompleted(userid);
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
        //if there is no prerequisites required for the course
        return valid;
    }

    /**
     * This method checks if the course is already completed by the user
     * @return true if course is completed, else return false
     */
    private boolean checkCourseCompleted() {
        ArrayList<CourseCompleted> userCompletedCourseList = CourseCompletedManager.getCourseCompleted(userid);
        for (CourseCompleted completedCourse : userCompletedCourseList) {
            if (completedCourse.getCourseCode().equals(course)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if the course is already bidded
     * @return true if course is already bidded, else return false
     */
    private boolean checkBidded() {
        ArrayList<Bid> studentBidList = BidDataManager.getStudentBids(userid);
        ArrayList<Bid> studentSuccessfulBidList = SuccessfulBidDataManager.getStudentSuccessfulBids(userid);
        for (Bid bid : studentBidList) {
            if (bid.getCourseCode().equals(course)) {
                return true;
            }
        }
        for (Bid bid : studentSuccessfulBidList) {
            if (bid.getCourseCode().equals(course)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method attempts to add bid
     * @param userIdenti the userId
     * @param courseCode the course code
     * @param sectionCode the section code of the course
     * @param amt the bid amount
     * @return failBid the reason why the bid had failed(returns an empty String if bid is successful)
     */
    public String addBid(String userIdenti, String courseCode, String sectionCode, String amount) {
        this.userid = userIdenti;
        setCourse(courseCode);
        setSection(sectionCode);
        setAmount(amount);
        BidDataManager.refresh();
        String failedBid;
        try {
            //retrieves student object
            student = StudentDataManager.getStudent(userIdenti);
            //parse amt into double type
            amt = Double.parseDouble(amount);

            //prints failedBid error message due to any one of the reasons below
            failedBid = "<p>User ID " + userIdenti + "'s bid for course " + courseCode + "'s section " + sectionCode + " worth $" + amt + " failed because ";
            int valid = 1;
            //user did not enter amount
            if (amount.isEmpty()) {
                failedBid += "the amount field for the bid is empty";
                valid = 0;
                //user enter a negative amount
            } else if (amt <= 0) {
                failedBid += "the bid amount was a negative amount";
                valid = 0;
            } //user tries to place the 6th bid
            else if (BidDataManager.getStudentBids(userid).size() + SuccessfulBidDataManager.getStudentSuccessfulBids(userid).size() == 5) {
                failedBid += "the student has already placed 5 bids previously";
                valid = 0;
            } //exam clash
            else if (checkExamClash()) {
                failedBid += "there is a clash in the student's courses exam schedule";
                valid = 0;
            } //lesson timing clash
            else if (checkLessonClash()) {
                failedBid += "there is a clash in the student's timetable schedule";
                valid = 0;
            } else if (checkCourseCompleted()) {
                failedBid += "student has already completed that course";
                valid = 0;
            } else if (!checkPrerequisiteDone()) {
                failedBid += "student has not done the prerequisite course.";
                valid = 0;
            } //user has insufficient balance
            else if (!checkBidAmountValid()) {
                failedBid += "the student has insufficient eDollar balance";
                valid = 0;
            } else if (checkBidded()) {
                failedBid += "student has already bidded for that course.";
                valid = 0;
            } //bid is lower than minBid
            else if (Round.getRound() == 2) {
                // double minBid = SectionDataManager.getSectionMinBid(courseCode, sectionCode);
                double minBid = SectionDataManager.getSection(courseCode, sectionCode).getMinBid();
                if ((minBid == 10.0 && amt < minBid) || (minBid != 10.0 && amt <= minBid)) {
                    failedBid += "the bid value is too low, and must be $1 more than the min bid.";
                    valid = 0;
                }
            }
            if (valid == 0) {
                return failedBid + "</p>";
            } //bid is successful
            else {
                //deducts bid amount from user's balance
                //StudentDataManager.editStudent(student, student.getEdollar() - amt);
                //adds bid
                BidDataManager.addBid(userIdenti, courseCode, sectionCode, amt);
            }
        } catch (Exception e) {
            failedBid = "Please upload a valid bid.csv file.";
        }
        return "";
    }
}
