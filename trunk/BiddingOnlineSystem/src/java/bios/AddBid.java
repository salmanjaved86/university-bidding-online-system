package bios;

import java.util.*;
import model.*;
import DataManager.*;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import org.apache.struts2.interceptor.SessionAware;

public class AddBid extends ActionSupport implements SessionAware {

    /**
     * Initializing private attributes
     */
    private Student student;
    private String userId;
    private String courseCode;
    private String sectionCode;
    private String amount;
    private Map session;
    private double amt;
    private double bal;
    private String message;

    /**
     * Returns the current session
     * @return session
     */
    public Map getSession() {
        return session;
    }

    /**
     * Set the current session
     * @param session the session to set
     */
    public void setSession(Map session) {
        this.session = session;
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
     * @return courseCode
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Set the course code
     * @param courseCode the courseCode to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Return the section code
     * @return sectionCode
     */
    public String getSectionCode() {
        return sectionCode;
    }

    /**
     * Set the section code
     * @param sectionCode the sectionCode to set
     */
    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    /**
     * Return the message
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Validates the add bid function
     */
    public void validate() {
        
        if(courseCode!=null && sectionCode!=null) {
            //retrieves student object
            student = (Student) session.get("student");
            //retrieves userId if student object is not null
            if (student != null) {
                userId = student.getUserId();
            }
            //checks if amount entered in the field is empty
            if (amount.isEmpty()) {
                addActionError("Please enter a valid amount");
            } else {
                try {
                    amt = Double.parseDouble(amount);
                    if (amt <= 0) {
                        //throws an error message if amount entered by the user is negative
                        throw new Exception("Please enter a valid amount.");
                        //throw an error message if amount entered by user contains more than 2 decimal places
                    } else if (String.valueOf(amt).indexOf(".") < String.valueOf(amt).length() - 3) {
                        throw new Exception("Only 2 decimal places allow.");

                    } else {
                    }
                    //shows error if bid is below 10 eDollars during round 1
                    if (Round.getRound() == 1) {
                        if (amt < 10.0) {
                            throw new Exception("Your bid is too low. Minimum bid is $10.00");
                        }
                    } else if (Round.getRound() == 2) {
                        //retrieve min bid of the particular course section
                        //double minBid = SectionDataManager.getSectionMinBid(courseCode, sectionCode);
                        double minBid = SectionDataManager.getSection(courseCode, sectionCode).getMinBid();
                        //bid must be 1 eDollar higher than the minBid
                        if (minBid == 10.0 && amt < minBid) {
                            throw new Exception("Your bid is too low. Bid placed must be $1 more than the min bid.");
                        } else if (minBid != 10.0 && amt < minBid) {
                            throw new Exception("Bid placed must be $1 more than the min bid. Current min bid is: $" + minBid);
                        }
                    }

                } catch (NumberFormatException e) {
                    addActionError("Please enter a valid amount.");
                } catch (Exception e) {
                    addActionError(e.getMessage());
                }

            }

            //shows error message when user has more than 5 bids
            if (BidDataManager.getStudentBids(userId).size() + SuccessfulBidDataManager.getStudentSuccessfulBids(userId).size() == 5) {
                addActionError("You may only bid for 5 courses at any one time.");
            }
            //shows error message if the course's exam data clashes with existing course bids
            if (checkExamClash()) {
                addActionError("There is a clash in the exam schedule. Please select another course.");
            }
            //shows error message if there is a clash in timing of the bids
            if (checkLessonClash()) {
                addActionError("There is a clash in the timetable schedule. Please select another section.");
            }

            //checks if user has sufficient balance
            if (!checkBidAmountValid()) {
                addActionError("Insufficient eDollar.");
            }
        }else{
            addActionError("No Course or Section selected!");
        }
    }

    /**
     * Method calls for addBid function
     * @return success
     */
    public String execute() {

        //add bid
        BidDataManager.addBid(student, courseCode, sectionCode, amt);

        if (Round.getRound() == 2) {
            RoundControl.checkRealTimeBid(courseCode, sectionCode);
        }
        //display success message
        addActionMessage("You have placed a bid for " + courseCode + " " + sectionCode);
        //message = "You have placed a bid for " + courseCode + " " + sectionCode;
        return "SUCCESS";
    }

    /**
     * Method checks if course's exams clash
     * @return true if exam date and time clash
     */
    private boolean checkExamClash() {
        ArrayList<Bid> studentBidList = BidDataManager.getStudentBids(userId);
        ArrayList<Bid> studentSuccessfulBidList = SuccessfulBidDataManager.getStudentSuccessfulBids(userId);
        for (Bid bid : studentBidList) {
            Course biddedCourse = CourseDataManager.getCourse(bid.getCourseCode());
            //check if biddedCourse's exam date and time is the same as the exam date of the course to be bidded
            if (biddedCourse.getExamDate().equals(CourseDataManager.getCourse(courseCode).getExamDate())
                    && biddedCourse.getExamStart().equals(CourseDataManager.getCourse(courseCode).getExamStart())) {
                return true;
            }
        }
        for (Bid bid : studentSuccessfulBidList) {
            Course biddedCourse = CourseDataManager.getCourse(bid.getCourseCode());
            //check if biddedCourse's exam date and time is the same as the exam date of the course to be bidded
            if (biddedCourse.getExamDate().equals(CourseDataManager.getCourse(courseCode).getExamDate())
                    && biddedCourse.getExamStart().equals(CourseDataManager.getCourse(courseCode).getExamStart())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method checks if student has sufficient amount for the bid
     * @return true if bid amount is valid
     */
    private boolean checkBidAmountValid() {
        //retrieves student's balance
        double studentCredit = student.getEdollar();
        //if the bid amount is lesser or equal to the student's balance
        if (amt < studentCredit || amt == studentCredit) {
            //deduct bid amount from the student balance
            //bal = studentCredit - amt;
            return true;
        }
        return false;
    }

    /**
     * Method checks if there is lesson clash(time and day)
     * @return true if there is lesson clash
     */
    private boolean checkLessonClash() {
        ArrayList<Bid> studentBidList = BidDataManager.getStudentBids(userId);
        ArrayList<Bid> studentSuccessfulBidList = SuccessfulBidDataManager.getStudentSuccessfulBids(userId);
        Section bidSection = SectionDataManager.getSection(courseCode, sectionCode);

        for (Bid bid : studentBidList) {
            Section biddedSection = SectionDataManager.getSection(bid.getCourseCode(), bid.getSectionCode());
            //check if day and time clash
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
}
