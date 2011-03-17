package bios;

import java.util.*;
import model.*;
import DataManager.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

public class EditBid extends ActionSupport implements SessionAware {
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
     * Return the amount of the bid
     * @return amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Set the amount of new bid
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }


    /**
     * Return the course code to be edited
     * @return courseCode
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Set the courseCode to be edited
     * @param courseCode the courseCode to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Return the bid's section code of the course to be edited
     * @return sectionCode
     */
    public String getSectionCode() {
        return sectionCode;
    }

    /**
     * Set the sectionCode
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
     * @return session
     */
    public Map getSession() {
        return session;
    }

    /**
     * Method validates the edit bid function
     */
    public void validate() {
        student = (Student) session.get("student");
        //retrieves userId
        userId = student.getUserId();
        //check if the amount to update bid is empty
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
                    } else if (minBid != 10.0 && amt <= minBid) {
                        throw new Exception("Bid placed must be $1 more than the min bid. Current min bid is: $" + minBid);
                    }
                }
            } catch (NumberFormatException e) {
                addActionError("Please enter a valid amount.");
            } catch (Exception e) {
                addActionError(e.getMessage());
            }
        }
        //check if user has enough amount of eDollar
        if (!checkEditValid()) {
            addActionError("Insufficient eDollar.");
        }
    }

    /**
     * Method executes the editBid function
     * @return success
     */
    public String execute() {
        //deducts amount from student
        
        //edit the bid accordingly
        BidDataManager.editBid(student, courseCode, sectionCode, amt);
        return "SUCCESS";
    }

    /**
     * Method checks if the edit bid function is valid by checking the balance of the student
     * and deduct the new bid amount from the student's account
     * @return true if edit bid is valid
     */
    private boolean checkEditValid() {
        ArrayList<Bid> studentBids = BidDataManager.getStudentBids(userId);
        for (int i = 0; i < studentBids.size(); i++) {
            Bid bid = studentBids.get(i);
            if (bid.getCourseCode().equals(courseCode)) {
                double biddedAmt = bid.getAmount();
                //refund the existing bid amount to the student's balance
                double cumulativeAmt = biddedAmt + student.getEdollar();
                if (amt < cumulativeAmt || amt == cumulativeAmt) {
                    //deducts the new bid amount from the student's balance
                    bal = cumulativeAmt - amt;
                    return true;
                }
            }
        }
        return false;
    }
}
