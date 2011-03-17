package bios;
import java.util.*;
import model.*;
import DataManager.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

public class DropBid extends ActionSupport implements SessionAware {
    /**
     * Initializing private attributes
     */
    private Student student;
    private Map session;
    private String courseCode;
    private String sectionCode;

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
     * Return the student object
     * @return student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Set the student
     * @param student the student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Set the session
     * @param session the session to set
     */
    public void setSession(Map session) {
        this.session = session;
    }

    /**
     * Return the current session
     * @return the session
     */
    public Map getSession() {
        return session;
    }

    /**
     * Execute the Drop Bid method by removing the selected bid from studentBids
     * and refund the cost of the dropped bid to the user eDollar balance
     * @return SUCCESS
     */
    public String execute() {

        System.out.println("CourseCode:"+courseCode);
        System.out.println("SectionCode:"+sectionCode);
        //retrieve the student object
        student = (Student) session.get("student");
        //retrieve student existing bids
        ArrayList<Bid> unconfirmedBids = BidDataManager.getStudentBids(student.getUserId());
        ArrayList<Bid> confirmedBids = SuccessfulBidDataManager.getStudentSuccessfulBids(student.getUserId());
        //iterate through the bid list
        for (int i = 0; i < unconfirmedBids.size(); i++) {
            Bid bid = unconfirmedBids.get(i);
            if (getCourseCode().equals(bid.getCourseCode())) {
                //retrieve bid amount
                double bidAmt = bid.getAmount();

                BidDataManager.dropBid(student, getCourseCode(),getSectionCode(), bidAmt);
            }
        }

        for (int i = 0; i < confirmedBids.size(); i++) {
            Bid bid = confirmedBids.get(i);
            if (getCourseCode().equals(bid.getCourseCode())) {
                //retrieve bid amount
                double bidAmt = bid.getAmount();

                SuccessfulBidDataManager.dropSuccessfulBid(student, getCourseCode(),getSectionCode(), bidAmt);
            }
        }
        //display success message
        addActionMessage("You have dropped " + getCourseCode());
        return "SUCCESS";
    }
}
