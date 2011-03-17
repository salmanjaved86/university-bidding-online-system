package bios;

import java.util.*;
import JDOModel.*;
import JDODataManager.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

public class AjaxEdit extends ActionSupport implements SessionAware {
    /**
     * Initializing private attributes
     */
    private String userId;
    private Student student;
    private Map session;
    private String courseCode;
    private String sectionCode;
    private String amount;
    private double amt;
    private double bal;

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
     * Return the student
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
     * @return session
     */
    public Map getSession() {
        return session;
    }

    /**
     * Method validates the edit bid function
     * @return true if all details for edit bid is valid
     * @return false if any details for the edit bid is invalid
     */ 
    public boolean validater() {
        //student = (Student) session.get("student");
        Student student = StudentDataManager.getStudent("fred.ng.2009");
        //retrieves userId
        userId = student.getUserId();
        //check if the amount to update bid is empty
         if (amount.isEmpty()) {
            addActionMessage("Error: Please enter a valid amount"); return false;
        } else {
            try {
                amt = Double.parseDouble(amount);
                if (amt <= 0) {

                    //throws an error message if amount entered by the user is negative
                    addActionMessage("Error: Please enter a valid amount."); return false;
                    //throw an error message if amount entered by user contains more than 2 decimal places
                } else if (String.valueOf(amt).indexOf(".") < String.valueOf(amt).length() - 3) {
                    addActionMessage("Error: Only 2 decimal places allow."); return false;

                } else {
                }
                //shows error if bid is below 10 eDollars during round 1
                if (Round.getRound() == 1) {
                    if (amt < 10.0) {
                       addActionMessage("Error: Your bid is too low. Minimum bid is $10.00"); return false;
                    }
                } else if (Round.getRound() == 2) {
                    //retrieve min bid of the particular course section
                    //double minBid = SectionDataManager.getSectionMinBid(courseCode, sectionCode);
                    double minBid = SectionDataManager.getSection(courseCode, sectionCode).getMinBid();
                    //bid must be 1 eDollar higher than the minBid
                    if (minBid == 10.0 && amt < minBid) {
                        addActionMessage("Error: Your bid is too low. Bid placed must be $1 more than the min bid."); return false;
                    } else if (minBid != 10.0 && amt <= minBid) {
                        addActionMessage("Error: Bid placed must be $1 more than the min bid. Current min bid is: $" + minBid); return false;
                    }
                }
            } catch (NumberFormatException e) {
                addActionMessage("Error: Please enter a valid amount."); return false;
            } catch (Exception e) {
                addActionMessage("Error: " + e.getMessage());return false;
            }
        }
        //check if user has enough amount of eDollar
        if (!checkEditValid()) {
            addActionMessage("Error: Insufficient eDollar."); return false;
        }
        return true;
    }

    /**
     * Method executes the editBid function
     * @return "success"
     */
    public String execute() {
        if (validater()){
        //edit the bid accordingly
        Student student = StudentDataManager.getStudent("fred.ng.2009");
        BidDataManager.editBid(student, courseCode, sectionCode, amt);
        addActionMessage("You have successfully edited the bid value for the course " + courseCode);
        }
        return "SUCCESS";
    }

    /**
     * Method checks if the edit bid function is valid by checking the balance of the student
     * and deduct the new bid amount from the student's account
     * @return true if edit bid is valid
     */
    private boolean checkEditValid() {
        List<Bid> studentBids = BidDataManager.getStudentBids(userId);
        for (int i = 0; i < studentBids.size(); i++) {
            Bid bid = studentBids.get(i);
            if (bid.getCourseCode().equals(courseCode)) {
                double biddedAmt = bid.getAmount();
                //refund the existing bid amount to the student's balance
                double cumulativeAmt = biddedAmt + student.getEdollar();
                
                if (amt < cumulativeAmt || amt == cumulativeAmt) {
                    //deducts the new bid amount from the student's balance
                    bal = cumulativeAmt - amt;
                    student.setEdollar(cumulativeAmt);
                    return true;
                }
            }
        }
        return false;
    }
}
